package com.yhx.intelligentsms.dao;

import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;

import com.yhx.intelligentsms.globle.Constant;
import com.yhx.intelligentsms.receiver.SendSmsReceiver;

import java.util.List;

/**
 * Created by yhx on 2017/12/10.
 */

public class SmsDao {

    public static void sendSms(Context context,String address, String body){
        SmsManager manager = SmsManager.getDefault();
        List<String> smss = manager.divideMessage(body);
        Intent intent = new Intent(SendSmsReceiver.ACTION_SEND_SMS);
        //短信发出去后，系统会发送一条广播，告知我们短信发送后是否成功
        PendingIntent sendIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        for (String text : smss) {
            //这个api只负责发短信，不会把短信写入到数据库中
            manager.sendTextMessage(address, null, text, sendIntent, null);
            //把短信插入短信数据库
            insertSms(context, address, body);
        }
    }
    public static void insertSms(Context context,String address, String body){
        ContentValues values = new ContentValues();
        values.put("address", address);
        values.put("body", body);
        values.put("type", Constant.SMS.TYPE_SEND);
        context.getContentResolver().insert(Constant.URI.URI_SMS, values);
    }
}