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
        Uri URI_GROUPS_INSERT = Uri.parse(GroupProvider.BASE_URI + "/groups/insert");
        Uri URI_GROUPS_QUERY = Uri.parse(GroupProvider.BASE_URI + "/groups/query");
        Uri URI_GROUPS_UPDATE = Uri.parse(GroupProvider.BASE_URI + "/groups/update");
        Uri URI_GROUPS_DELETE = Uri.parse(GroupProvider.BASE_URI + "/groups/delete");
        Uri URI_THREAD_GROUP_INSERT = Uri.parse(GroupProvider.BASE_URI + "/thread_group/insert");
        Uri URI_THREAD_GROUP_QUERY = Uri.parse(GroupProvider.BASE_URI + "/thread_group/query");
        Uri URI_THREAD_GROUP_UPDATE = Uri.parse(GroupProvider.BASE_URI + "/thread_group/update");
        Uri URI_THREAD_GROUP_DELETE = Uri.parse(GroupProvider.BASE_URI + "/thread_group/delete");
    }

    public interface SMS{
        int TYPE_RECEIVE = 1;
        int TYPE_SEND = 2;
    }
}
