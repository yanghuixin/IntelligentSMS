package com.yhx.intelligentsms.ui.activity;

import android.content.Intent;
import android.database.Cursor;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.yhx.intelligentsms.R;
import com.yhx.intelligentsms.adapter.ConversationListAdapter;
import com.yhx.intelligentsms.base.BaseActivity;
import com.yhx.intelligentsms.bean.Conversation;
import com.yhx.intelligentsms.dao.SimpleQueryHandler;
import com.yhx.intelligentsms.globle.Constant;

/**
 * Created by Administrator on 2017/12/12.
 */

public class GroupDetailActivity extends BaseActivity {

    private String groupName;
    private int groupId;
    private ListView lv_group_detail;
    private SimpleQueryHandler queryHandler;
    private ConversationListAdapter adapter;

    @Override
    public void initView() {
        setContentView(R.layout.activity_group_detail);
        lv_group_detail = findViewById(R.id.lv_group_detail);
    }

    @Override
    public void initListener() {
        lv_group_detail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //直接跳转至ConversationDetailActivity
                //进入会话
                Intent intent = new Intent(GroupDetailActivity.this, ConversationDetailActivity.class);
                //携带数据：address和thread_id
                Cursor cursor = (Cursor) adapter.getItem(position);
                Conversation conversation = Conversation.createFromCursor(cursor);
                intent.putExtra("address", conversation.getAddress());
                intent.putExtra("thread_id", conversation.getThreadId());
                startActivity(intent);
            }
        });
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        groupName = intent.getStringExtra("name");
        groupId = intent.getIntExtra("group_id", -1);
        initTitleBar();

        //复用会话列表的所有组件、参数、条目布局、查询字段、表
        //只有查询的条件不一样
        adapter = new ConversationListAdapter(this, null);
        lv_group_detail.setAdapter(adapter);

        String[] projection = {
                "body As snippet",
                "thread_id As _id",
                //"groups.msg_count As msg_count",
                "address As address",
                "date As date"
        };

        queryHandler = new SimpleQueryHandler(getContentResolver());
        queryHandler.startQuery(0, adapter, Constant.URI.URI_SMS_CONVERSATION, projection, buildQuery(), null, null);
    }

    @Override
    public void processClick(View v) {
        switch (v.getId()){
            case R.id.iv_titlebar_back_btn:
                finish();
                break;
        }
    }

    private void initTitleBar(){
        ((TextView)findViewById(R.id.tv_titlebar_title)).setText(groupName);
        findViewById(R.id.iv_titlebar_back_btn).setOnClickListener(this);
    }

    private String buildQuery(){
        //查询当前群组包含的所有会话的thread_id,这些会话都会被显示在GroupDetailActivity界面中
        Cursor cursor = getContentResolver().query(Constant.URI.URI_THREAD_GROUP_QUERY, new String[]{"thread_id"}, "group_id = " + groupId, null, null);
        String selection = "thread_id in (";
        while (cursor.moveToNext()){
            if (cursor.isLast()){
                //最后一个会话id后面不要逗号
                selection += cursor.getString(cursor.getColumnIndex("thread_id"));
            }else {
                selection += cursor.getString(cursor.getColumnIndex("thread_id")) + ",";
            }
        }
        selection += ")";
        return selection;
    }
}
