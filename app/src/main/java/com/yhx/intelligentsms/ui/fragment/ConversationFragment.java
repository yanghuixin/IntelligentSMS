package com.yhx.intelligentsms.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.nineoldandroids.view.ViewPropertyAnimator;
import com.yhx.intelligentsms.R;
import com.yhx.intelligentsms.adapter.ConversationListAdapter;
import com.yhx.intelligentsms.base.BaseFragment;
import com.yhx.intelligentsms.dao.SimpleQueryHandler;
import com.yhx.intelligentsms.dialog.ConfirmDialog;
import com.yhx.intelligentsms.globle.Constant;

import java.util.List;

/**
 * Created by Administrator on 2017/11/27.
 */

public class ConversationFragment extends BaseFragment {

    private Button bt_conversation_edit;
    private Button bt_conversation_new_msg;
    private Button bt_conversation_select_all;
    private Button bt_conversation_cancel_select;
    private Button bt_conversation_delete;
    private LinearLayout ll_conversation_edit_menu;
    private LinearLayout ll_conversation_select_menu;
    private ListView lv_conversation_list;
    private ConversationListAdapter conversationListAdapter;
    private List<Integer> selectedConversationIds;
    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //填充布局对象，返回view对象
        View view = inflater.inflate(R.layout.fragment_conversation,null);

        lv_conversation_list = view.findViewById(R.id.lv_conversation_list);
        bt_conversation_edit = view.findViewById(R.id.bt_conversation_edit);
        bt_conversation_new_msg = view.findViewById(R.id.bt_conversation_new_msg);
        bt_conversation_select_all = view.findViewById(R.id.bt_conversation_select_all);
        bt_conversation_cancel_select = view.findViewById(R.id.bt_conversation_cancel_select);
        bt_conversation_delete = view.findViewById(R.id.bt_conversation_delete);
        ll_conversation_edit_menu = view.findViewById(R.id.ll_conversation_edit_menu);
        ll_conversation_select_menu = view.findViewById(R.id.ll_conversation_select_menu);
        return view;
    }

    @Override
    public void initListener() {
        bt_conversation_edit.setOnClickListener(this);
        bt_conversation_new_msg.setOnClickListener(this);
        bt_conversation_select_all.setOnClickListener(this);
        bt_conversation_cancel_select.setOnClickListener(this);
        bt_conversation_delete.setOnClickListener(this);
        lv_conversation_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (conversationListAdapter.isSelectMode()){
                    //选中选框
                    conversationListAdapter.selectSingle(position);
                }else {
                    //进入会话

                }
            }
        });
        lv_conversation_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (conversationListAdapter.isSelectMode()){
                    //选中选框
                    conversationListAdapter.selectSingle(position);
                }else {
                    //进入会话详情
                }
            }
        });
    }

    @Override
    public void initData() {
        //创建一个CursorAdapter对象
        conversationListAdapter = new ConversationListAdapter(getActivity(),null);
        lv_conversation_list.setAdapter(conversationListAdapter);
        //Cursor cursor = getActivity().getContentResolver().query(Constant.URI.URI_SMS_CONVERSATION,null,null,null,null);
        SimpleQueryHandler simpleQueryHandler = new SimpleQueryHandler(getActivity().getContentResolver());

        String[] projection = {
                "sms.body As snippet",
                "sms.thread_id As _id",
                "groups.msg_count As msg_count",
                "address As address",
                "date As date"
        };
        //开始异步查询
        //arg0、arg1：可以用来携带一个int型和一个对象
        //arg1：用来携带adapter对象，查询完毕后给adapter设置cursor
        simpleQueryHandler.startQuery(0,conversationListAdapter,Constant.URI.URI_SMS_CONVERSATION,projection,null,null,"date desc");
    }

    @Override
    public void processClick(View v) {
        switch (v.getId()){
            case R.id.bt_conversation_edit:
                showSelectMenu();
                //进入选择模式
                conversationListAdapter.setSelectMode(true);
                conversationListAdapter.notifyDataSetChanged();
                break;
            case R.id.bt_conversation_new_msg:
                break;
            case R.id.bt_conversation_select_all:
                break;
            case R.id.bt_conversation_cancel_select:
                showEditMenu();
                //退出选择模式
                conversationListAdapter.setSelectMode(false);
                conversationListAdapter.notifyDataSetChanged();
                break;
            case R.id.bt_conversation_delete:
                selectedConversationIds = conversationListAdapter.getSelectedConversationIds();
                if (selectedConversationIds.size() == 0){
                    return;
                }
                showDeleteDialog();
                break;
        }
    }

    /**
     * 选择菜单往上移动，编辑菜单往下移动
     */
    private void showSelectMenu(){
        ViewPropertyAnimator.animate(ll_conversation_edit_menu).translationY(ll_conversation_edit_menu.getHeight()).setDuration(200);
        //延时执行
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ViewPropertyAnimator.animate(ll_conversation_select_menu).translationY(0).setDuration(200);
            }
        },200);
    }

    /**
     * 选择菜单往下移动，编辑菜单往上移动
     */
    private void showEditMenu(){
        ViewPropertyAnimator.animate(ll_conversation_select_menu).translationY(ll_conversation_edit_menu.getHeight()).setDuration(200);
        //延时执行
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ViewPropertyAnimator.animate(ll_conversation_edit_menu).translationY(0).setDuration(200);
            }
        },200);
    }

    private void deleteSms(){
        for (int i = 0; i < selectedConversationIds.size(); i++){
            //取出集合中的会话id，以id作为where条件删除所有符合条件的短信
            String where = "thread_id = " + selectedConversationIds.get(i);
            getActivity().getContentResolver().delete(Constant.URI.URI_SMS, where, null);
        }
    }

    private void showDeleteDialog(){
        ConfirmDialog.showDialog(getActivity(), "提示", "真的要删除回话吗？", new ConfirmDialog.OnConfirmListener() {
            @Override
            public void onCancel() {
                deleteSms();
            }

            @Override
            public void onConfirm() {

            }
        });
    }
}
