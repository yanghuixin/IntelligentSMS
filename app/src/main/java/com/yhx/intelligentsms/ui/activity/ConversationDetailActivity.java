package com.yhx.intelligentsms.ui.activity;

import android.content.Intent;
import android.view.View;

import com.yhx.intelligentsms.base.BaseActivity;

/**
 * Created by Administrator on 2017/12/9.
 */

public class ConversationDetailActivity extends BaseActivity {
    @Override
    public void initView() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        //拿到传递过来的数据
        Intent intent = getIntent();
        if (intent != null){
            String address = intent.getStringExtra("address");
            int thread_id = intent.getIntExtra("thread_id", -1);
        }
    }

    @Override
    public void processClick(View v) {

    }
}
