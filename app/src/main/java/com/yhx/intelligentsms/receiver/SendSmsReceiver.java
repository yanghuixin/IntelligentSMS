package com.yhx.intelligentsms.receiver;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.yhx.intelligentsms.utils.ToastUtils;

/**
 * Created by yhx on 2017/12/10.
 */

public class SendSmsReceiver extends BroadcastReceiver {

    public static final String ACTION_SEND_SMS = "com.yhx.intelligentsms.sendsms";
    @Override
    public void onReceive(Context context, Intent intent) {
        int code = getResultCode();
        if (code == Activity.RESULT_OK){
            ToastUtils.showToast(context, "发送成功");
        }else {
            ToastUtils.showToast(context, "发送失败");
        }
    }
}
