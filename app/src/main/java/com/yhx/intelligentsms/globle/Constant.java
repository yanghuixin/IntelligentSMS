package com.yhx.intelligentsms.globle;

import android.net.Uri;

import com.yhx.intelligentsms.provider.GroupProvider;

/**
 * Created by Administrator on 2017/11/28.
 */

public class Constant {

    public interface URI{
        Uri URI_SMS_CONVERSATION = Uri.parse("content://mms-sms/conversations");
        Uri URI_SMS = Uri.parse("content://sms");
        Uri URI_GROUP_INSERT = Uri.parse(GroupProvider.BASE_URI + "/groups/insert");
        Uri URI_GROUP_QUERY = Uri.parse(GroupProvider.BASE_URI + "/groups/query");
        Uri URI_GROUP_UPDATE = Uri.parse(GroupProvider.BASE_URI + "/groups/update");
        Uri URI_GROUP_DELETE = Uri.parse(GroupProvider.BASE_URI + "/groups/delete");
    }

    public interface SMS{
        int TYPE_RECEIVE = 1;
        int TYPE_SEND = 2;
    }
}
