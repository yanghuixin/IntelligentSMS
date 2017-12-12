package com.yhx.intelligentsms.ui.fragment;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.yhx.intelligentsms.R;
import com.yhx.intelligentsms.adapter.GroupListAdapter;
import com.yhx.intelligentsms.base.BaseFragment;
import com.yhx.intelligentsms.bean.Group;
import com.yhx.intelligentsms.dao.GroupDao;
import com.yhx.intelligentsms.dao.SimpleQueryHandler;
import com.yhx.intelligentsms.dialog.InputDialog;
import com.yhx.intelligentsms.dialog.ListDialog;
import com.yhx.intelligentsms.globle.Constant;
import com.yhx.intelligentsms.utils.ToastUtils;

/**
 * Created by Administrator on 2017/11/27.
 */

public class GroupFragmet extends BaseFragment {

    private Button bt_group_newgroup;
    private ListView lv_group_list;
    private GroupListAdapter adapter;
    private SimpleQueryHandler queryHandler;

    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //填充布局对象，返回view对象
        View view = inflater.inflate(R.layout.fragment_group,null);
        bt_group_newgroup = view.findViewById(R.id.bt_group_newgroup);
        lv_group_list = view.findViewById(R.id.lv_group_list);
        return view;
    }

    @Override
    public void initListener() {
        bt_group_newgroup.setOnClickListener(this);
        //给条目设置长按侦听
        lv_group_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) adapter.getItem(position);
                final Group group = Group.createFromCursor(cursor);
                ListDialog.showDialog(getActivity(), "选择操作", new String[]{"修改", "删除"}, new ListDialog.OnListDialogListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        switch (position){
                            case 0://修改
                                //弹出输入对话框
                                InputDialog.showDialog(getActivity(), "修改群组", new InputDialog.OnInputDialogListener() {
                                    @Override
                                    public void onCancel() {

                                    }

                                    @Override
                                    public void onConfirm(String text) {
                                        //确认修改群组名称
                                        GroupDao.updateGroupName(getActivity().getContentResolver(), text, group.get_id());
                                    }
                                });
                                break;
                            case 1://删除
                                GroupDao.deleteGroup(getActivity().getContentResolver(), group.get_id());
                                break;
                        }
                    }
                });
                return false;
            }
        });
    }

    @Override
    public void initData() {
        adapter = new GroupListAdapter(getActivity(), null);
        lv_group_list.setAdapter(adapter);

        queryHandler = new SimpleQueryHandler(getActivity().getContentResolver());
        queryHandler.startQuery(0, adapter, Constant.URI.URI_GROUP_QUERY, null, null, null, "create_date desc");
    }

    @Override
    public void processClick(View v) {
        switch (v.getId()){
            case R.id.bt_group_newgroup:
                InputDialog.showDialog(getActivity(), "创建群组", new InputDialog.OnInputDialogListener() {
                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onConfirm(String text) {
                        if (!TextUtils.isEmpty(text)){
                            GroupDao.insertGroup(getActivity().getContentResolver(), text);
                        }else {
                            ToastUtils.showToast(getActivity(), "群组名不能为空");
                        }
                    }
                });
                break;
        }
    }
}
