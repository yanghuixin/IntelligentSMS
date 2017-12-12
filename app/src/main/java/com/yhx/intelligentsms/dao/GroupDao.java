package com.yhx.intelligentsms.dao;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;

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
        resolver.insert(Constant.URI.URI_GROUPS_INSERT, values);
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
        resolver.update(Constant.URI.URI_GROUPS_UPDATE, values, "_id = " + _id, null);
    }

    /**
     * 删除群组
     * @param resolver
     * @param _id
     */
    public static void deleteGroup(ContentResolver resolver, int _id){
        resolver.delete(Constant.URI.URI_GROUPS_DELETE, "_id = " + _id, null);
    }

    /**
     * 通过群组id查询群组名称
     * @param resolver
     * @param _id
     * @return
     */
    public static String getGroupNameByGroupId(ContentResolver resolver, int _id){
        String name = null;
        Cursor cursor = resolver.query(Constant.URI.URI_GROUPS_QUERY, new String[]{"name"}, "_id = " + _id, null, null);
        if (cursor.moveToFirst()){
            name = cursor.getString(cursor.getColumnIndex("name"));
        }
        return name;
    }

}
