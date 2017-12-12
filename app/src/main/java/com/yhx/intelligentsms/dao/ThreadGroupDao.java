package com.yhx.intelligentsms.dao;

import android.content.ContentResolver;
import android.database.Cursor;

import com.yhx.intelligentsms.globle.Constant;

/**
 * Created by Administrator on 2017/12/12.
 */

public class ThreadGroupDao {

    /**
     * 判断选中的会话是否有所属群组
     * @param resolver
     * @param thread_id
     * @return
     */
    public static boolean hasGroup(ContentResolver resolver, int thread_id){
        //只要能查到数据，说明这个会话已经被存入thread_group表了
        Cursor cursor = resolver.query(Constant.URI.URI_THREAD_GROUP_QUERY, null, "thread_id = " + thread_id, null, null);
        return cursor.moveToNext();
    }

    /**
     * 通过会话id查询群组id
     * @param resolver
     * @param thread_id
     * @return
     */
    public static int getGroupIdByThreadId(ContentResolver resolver, int thread_id){
        Cursor cursor = resolver.query(Constant.URI.URI_THREAD_GROUP_QUERY, new String[]{"group_id"}, "thread_id = " + thread_id, null, null);
        cursor.moveToFirst();
        return cursor.getInt(cursor.getColumnIndex("group_id"));
    }
}
