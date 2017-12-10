package com.yhx.intelligentsms.globle;

import android.net.Uri;

/**
 * Created by Administrator on 2017/11/28.
 */

public class Constant {

    public interface URI{
        Uri URI_SMS_CONVERSATION = Uri.parse("content://mms-sms/conversations");
        Uri URI_SMS = Uri.parse("content://sms");
        Uri URI_GROUP_INSERT = Uri.parse("content://com.yhx.intelligentsms/groups/insert");
    }

    public interface SMS{
        int TYPE_RECEIVE = 1;
        int TYPE_SEND = 2;
    }
}
