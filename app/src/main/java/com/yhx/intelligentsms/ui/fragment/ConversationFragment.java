package com.yhx.intelligentsms.ui.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.yhx.intelligentsms.bean.Conversation;
import com.yhx.intelligentsms.bean.Group;
import com.yhx.intelligentsms.dao.GroupDao;
import com.yhx.intelligentsms.dao.SimpleQueryHandler;
import com.yhx.intelligentsms.dao.ThreadGroupDao;
import com.yhx.intelligentsms.dialog.ConfirmDialog;
import com.yhx.intelligentsms.dialog.DeleteMsgDialog;
import com.yhx.intelligentsms.dialog.ListDialog;
import com.yhx.intelligentsms.globle.Constant;
import com.yhx.intelligentsms.ui.activity.ConversationDetailActivity;
import com.yhx.intelligentsms.ui.activity.NewMsgActivity;
import com.yhx.intelligentsms.utils.ToastUtils;

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
    private ConversationListAdapter adapter;
    private List<Integer> selectedConversationIds;
    private DeleteMsgDialog dialog;
    private boolean isStopDelete = false;

    private static final int WHAT_DELETE_COMPLETE = 0;
    private static final int WHAT_UPDATE_DELETE_PROGRESS = 1;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case WHAT_DELETE_COMPLETE:
                    //退出选择模式，显示编辑菜单
                    adapter.setSelectMode(false);
                    adapter.notifyDataSetChanged();
                    showEditMenu();
                    dialog.dismiss();
                    break;
                case WHAT_UPDATE_DELETE_PROGRESS:
                    dialog.updateProgressAndTitle(msg.arg1 + 1);
                    break;
            }
        }
    };

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
                if (adapter.isSelectMode()){
                    //选中选框
                    adapter.selectSingle(position);
                }else {
                    //进入会话
                    Intent intent = new Intent(getActivity(), ConversationDetailActivity.class);
                    //携带数据：address和thread_id
                    Cursor cursor = (Cursor) adapter.getItem(position);
                    Conversation conversation = Conversation.createFromCursor(cursor);
                    intent.putExtra("address", conversation.getAddress());
                    intent.putExtra("thread_id", conversation.getThreadId());
                    startActivity(intent);
                }
            }
        });
        lv_conversation_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) adapter.getItem(position);
                Conversation conversation = Conversation.createFromCursor(cursor);
                //判断选中的会话是否有所属群组
                if (ThreadGroupDao.hasGroup(getActivity().getContentResolver(), conversation.getThreadId())){
                    //该会话已经被添加到群组中,弹出ConfirmDialog
                    showExitDialog(conversation.getThreadId());
                }else {
                    //该会话没有被添加到群组中,弹出ListDialog，列出所有群组
                    showSelectGroupDialog(conversation.getThreadId());
                }
                //消费掉这个事件，否则会传递给OnItemClickListener
                return true;
            }
        });
    }

    @Override
    public void initData() {
        //创建一个CursorAdapter对象
        adapter = new ConversationListAdapter(getActivity(),null);
        lv_conversation_list.setAdapter(adapter);
        //Cursor cursor = getActivity().getContentResolver().query(Constant.URI.URI_SMS_CONVERSATION,null,null,null,null);
        SimpleQueryHandler simpleQueryHandler = new SimpleQueryHandler(getActivity().getContentResolver());

        String[] projection = {
                "body As snippet",
                "thread_id As _id",
                //"groups.msg_count As msg_count",
                "address As address",
                "date As date"
        };
        //开始异步查询
        //arg0、arg1：可以用来携带一个int型和一个对象
        //arg1：用来携带adapter对象，查询完毕后给adapter设置cursor
        simpleQueryHandler.startQuery(0,adapter,Constant.URI.URI_SMS_CONVERSATION,projection,null,null,"date desc");
    }

    @Override
    public void processClick(View v) {
        switch (v.getId()){
            case R.id.bt_conversation_edit:
                showSelectMenu();
                //进入选择模式
                adapter.setSelectMode(true);
                adapter.notifyDataSetChanged();
                break;
            case R.id.bt_conversation_new_msg:
                Intent intent = new Intent(getActivity(), NewMsgActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_conversation_select_all:
                adapter.selectAll();
                break;
            case R.id.bt_conversation_cancel_select:
                showEditMenu();
                //退出选择模式
                adapter.setSelectMode(false);
                adapter.notifyDataSetChanged();
                break;
            case R.id.bt_conversation_delete:
                selectedConversationIds = adapter.getSelectedConversationIds();
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
        //弹出删除进度对话框
        dialog = DeleteMsgDialog.showDeleteDialog(getActivity(), selectedConversationIds.size(), new DeleteMsgDialog.OnDeleteCancelListener() {
            @Override
            public void onCancel() {
                isStopDelete = true;
            }
        });
        Thread t = new Thread(){
            @Override
            public void run() {
                for (int i = 0; i < selectedConversationIds.size(); i++){
                    //中断删除
                    if (isStopDelete){
                        isStopDelete = false;
                        break;
                    }
                    //取出集合中的会话id，以id作为where条件删除所有符合条件的短信
                    String where = "thread_id = " + selectedConversationIds.get(i);
                    getActivity().getContentResolver().delete(Constant.URI.URI_SMS, where, null);
                    //发送消息，让删除进度条刷新，同时把当前的删除进度传给进度条
                    Message msg = handler.obtainMessage();
                    msg.what = WHAT_UPDATE_DELETE_PROGRESS;
                    //把当前删除进度存入消息中
                    msg.arg1 = i;
                    handler.sendMessage(msg);
                }
                //删除会话后，清除集合
                selectedConversationIds.clear();
                handler.sendEmptyMessage(WHAT_DELETE_COMPLETE);
            }
        };
        t.start();
    }

    private void showDeleteDialog(){
        ConfirmDialog.showDialog(getActivity(), "提示", "真的要删除会话吗？", new ConfirmDialog.OnConfirmListener() {
            @Override
            public void onCancel() {

            }

            @Override
            public void onConfirm() {
                deleteSms();
            }
        });
    }

    private void showExitDialog(final int thread_id){
        //先通过会话id查询群组id
        final int group_id = ThreadGroupDao.getGroupIdByThreadId(getActivity().getContentResolver(), thread_id);
        //通过群组id查询群组名字
        String name = GroupDao.getGroupNameByGroupId(getActivity().getContentResolver(), group_id);

        String message = "该会话已经被添加至[" + name + "]群组,是否要退出该群组?";
        ConfirmDialog.showDialog(getActivity(), "提示", message, new ConfirmDialog.OnConfirmListener() {
            @Override
            public void onCancel() {

            }

            @Override
            public void onConfirm() {
                //把选中的会话从群组中删除
                boolean isSuccess = ThreadGroupDao.deleteThreadGroupByThreadId(getActivity().getContentResolver(), thread_id, group_id);
                ToastUtils.showToast(getActivity(), isSuccess ? "退出群组成功" : "退出群组失败");
            }
        });
    }

    private void showSelectGroupDialog(final int thread_id){
        //查询一共有多少群组，取出名字全部存入items
        final Cursor cursor = getActivity().getContentResolver().query(Constant.URI.URI_GROUPS_QUERY, null, null, null, null);
        if (cursor.getCount() == 0){
            ToastUtils.showToast(getActivity(), "当前没有群组，请先创建");
            return;
        }
        String[] items = new String[cursor.getCount()];
        //遍历cursor，取出名字
        while (cursor.moveToNext()){
            Group group = Group.createFromCursor(cursor);
            items[cursor.getPosition()] = group.getName();
        }
        ListDialog.showDialog(getActivity(), "选择群组", items, new ListDialog.OnListDialogListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //cursor就是查询groups表得到的，里面就是群组的所有消息
                cursor.moveToPosition(position);
                Group group = Group.createFromCursor(cursor);
                //把指定会话存入指定群组
                boolean isSuccess = ThreadGroupDao.insertThreadGroup(getActivity().getContentResolver(), thread_id, group.get_id());
                ToastUtils.showToast(getActivity(), isSuccess ? "添加群组成功" : "添加群组失败");
            }
        });
    }
}
