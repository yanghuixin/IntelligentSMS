package com.yhx.intelligentsms.dialog;

import android.content.Context;
import android.view.View;

import com.yhx.intelligentsms.R;

/**
 * Created by Administrator on 2017/12/6.
 */

public class ConfirmDialog extends BaseDialog {

    protected ConfirmDialog(Context context) {
        super(context);
    }

    public static void showDialog(Context context){
        ConfirmDialog dialog = new ConfirmDialog(context);
        dialog.show();
    }

    @Override
    public void initView() {
        //设置对话框显示的布局文件
        setContentView(R.layout.dialog_confirm);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void processClick(View v) {

    }
}
