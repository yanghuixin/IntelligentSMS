package com.yhx.intelligentsms.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.yhx.intelligentsms.R;
import com.yhx.intelligentsms.adapter.ConversationDetailAdapter;
import com.yhx.intelligentsms.base.BaseActivity;
import com.yhx.intelligentsms.dao.ContactDao;
import com.yhx.intelligentsms.dao.SimpleQueryHandler;
import com.yhx.intelligentsms.dao.SmsDao;
import com.yhx.intelligentsms.globle.Constant;

/**
 * Created by Administrator on 2017/12/9.
 */

public class ConversationDetailActivity extends BaseActivity {

    private String address;
    private int thread_id;
    private SimpleQueryHandler queryHandler;
    private ConversationDetailAdapter conversationDetailAdapter;
    private ListView lv_conversation_detail;
    private EditText et_conversation_detail;
    private Button bt_conversation_detail_send;

    @Override
    public void initView() {
        setContentView(R.layout.activity_conversation_detail);

        lv_conversation_detail = findViewById(R.id.lv_conversation_detail);
        et_conversation_detail = findViewById(R.id.et_conversation_detail);
        bt_conversation_detail_send = findViewById(R.id.bt_conversation_detail_send);
        //只要ListView刷新就会滑动
        lv_conversation_detail.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
    }

    @Override
    public void initListener() {
        bt_conversation_detail_send.setOnClickListener(this);
    }

    @Override
    public void initData() {
        //拿到传递过来的数据
        Intent intent = getIntent();
        if (intent != null){
            address = intent.getStringExtra("address");
            thread_id = intent.getIntExtra("thread_id", -1);
            //初始化标题栏
            initTitleBar();
        }
        //为会话详细界面的listview设置adapter，显示会话的所有短信
        conversationDetailAdapter = new ConversationDetailAdapter(this, null, lv_conversation_detail);
        lv_conversation_detail.setAdapter(conversationDetailAdapter);
        //根据会话id查询该会话所有的短信
        String[] projection = {
                "_id",
                "body",
                "type",
                "date"
        };
        String selection = "thread_id = " + thread_id;
        //异步查询短信
        queryHandler = new SimpleQueryHandler(getContentResolver());
        queryHandler.startQuery(0, conversationDetailAdapter, Constant.URI.URI_SMS, projection, selection, null, "date");
        //Cursor cursor = getContentResolver().query(Constant.URI.URI_SMS, projection, selection, null, "date");
    }

    @Override
    public void processClick(View v) {
        switch (v.getId()){
            case R.id.iv_titlebar_back_btn:
                finish();
                break;
            case R.id.bt_conversation_detail_send:
                String body = et_conversation_detail.getText().toString();
                //判断用户是否输入短信内容
                if (!TextUtils.isEmpty(body)){
                    SmsDao.sendSms(this, address, body);
                }
                et_conversation_detail.setText("");
                break;
        }
    }

    /**
     * 初始化标题栏
     */
    public void initTitleBar(){
        findViewById(R.id.iv_titlebar_back_btn).setOnClickListener(this);
        String name = ContactDao.getNameByAddress(getContentResolver(), address);
        ((TextView)findViewById(R.id.tv_titlebar_title)).setText(TextUtils.isEmpty(name)? address : name);
    }
}