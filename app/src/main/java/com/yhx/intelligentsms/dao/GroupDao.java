package com.yhx.intelligentsms.dao;

import android.content.ContentResolver;
import android.content.ContentValues;

import com.yhx.intelligentsms.globle.Constant;

/**
 * Created by Administrator on 2017/12/11.
 */

public class GroupDao {

    /**
     * 插入新的群组
     * @param resolver
     * @param groupName
     */
    public static void insertGroup(ContentResolver resolver, String groupName){
        ContentValues values = new ContentValues();
        values.put("name", groupName);
        values.put("thread_count", 0);
        values.put("create_date", System.currentTimeMillis());
        resolver.insert(Constant.URI.URI_GROUP_INSERT, values);
    }
}
