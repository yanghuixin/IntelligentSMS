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

    /**
     * 修改群组名称
     * @param resolver
     * @param groupName
     * @param _id
     */
    public static void updateGroupName(ContentResolver resolver, String groupName, int _id){
        ContentValues values = new ContentValues();
        values.put("name", groupName);
        resolver.update(Constant.URI.URI_GROUP_UPDATE, values, "_id = " + _id, null);
    }
}
