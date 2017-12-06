package com.yhx.intelligentsms.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.yhx.intelligentsms.R;

/**
 * Created by Administrator on 2017/12/6.
 */

public abstract class BaseDialog extends AlertDialog implements View.OnClickListener {

    protected BaseDialog(Context context){
        //通过构造指定主题，主题中就已经设置了弧形边角的背景
        super(context, R.style.BaseDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initListener();
        initData();
    }

    public abstract void initView();
    public abstract void initListener();
    public abstract void initData();
    public abstract void processClick(View v);

    @Override
    public void onClick(View v) {
        processClick(v);
    }
}
