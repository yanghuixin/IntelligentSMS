package com.yhx.intelligentsms.dialog;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yhx.intelligentsms.R;

/**
 * Created by Administrator on 2017/12/6.
 */

public class ConfirmDialog extends BaseDialog {

    private String title;
    private String message;
    private TextView tv_dialog_title;
    private TextView tv_dialog_message;
    private Button bt_dialog_cancel;
    private Button bt_dialog_confirm;
    private OnConfirmListener onConfirmListener;

    protected ConfirmDialog(Context context) {
        super(context);
    }

    public static void showDialog(Context context, String title, String message,OnConfirmListener onConfirmListener){
        ConfirmDialog dialog = new ConfirmDialog(context);
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setOnConfirmListener(onConfirmListener);
        dialog.show();
    }

    @Override
    public void initView() {
        //设置对话框显示的布局文件
        setContentView(R.layout.dialog_confirm);
        tv_dialog_title = findViewById(R.id.tv_dialog_title);
        tv_dialog_message = findViewById(R.id.tv_dialog_message);
        bt_dialog_cancel = findViewById(R.id.bt_dialog_cancel);
        bt_dialog_confirm = findViewById(R.id.bt_dialog_confirm);
    }

    @Override
    public void initListener() {
        bt_dialog_cancel.setOnClickListener(this);
        bt_dialog_confirm.setOnClickListener(this);
    }

    @Override
    public void initData() {
        tv_dialog_title.setText(title);
        tv_dialog_message.setText(message);
    }

    @Override
    public void processClick(View v) {
        switch (v.getId()){
            //如果取消按钮按下时，侦听存在，那么调用侦听的onCancel方法
            case R.id.bt_dialog_cancel:
                if (onConfirmListener != null){
                    onConfirmListener.onCancel();
                }
                break;
            //如果确定按钮按下时，侦听存在，那么调用侦听的onConfirm方法
            case R.id.bt_dialog_confirm:
                if (onConfirmListener != null){
                    onConfirmListener.onConfirm();
                }
                break;
        }
        //对话框消失
        dismiss();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
       this.message = message;
    }

    public void setOnConfirmListener(OnConfirmListener onConfirmListener) {
        this.onConfirmListener = onConfirmListener;
    }

    public interface OnConfirmListener{
        void onCancel();
        void onConfirm();
    }
}
