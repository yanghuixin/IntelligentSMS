package com.yhx.intelligentsms.dao;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

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

    /**
     * 把会话添加到群组中
     * @param resolver
     * @param thread_id
     * @param group_id
     * @return
     */
    public static boolean insertThreadGroup(ContentResolver resolver, int thread_id, int group_id){
        ContentValues values = new ContentValues();
        values.put("thread_id", thread_id);
        values.put("group_id", group_id);
        Uri uri = resolver.insert(Constant.URI.URI_THREAD_GROUP_INSERT, values);

        if (uri != null){
            //插入会话后，改变群组的会话数量
            int thread_count = GroupDao.getThreadCount(resolver, group_id);
            GroupDao.updateThreadCount(resolver, group_id, thread_count + 1);
        }

        return uri != null;
    }

    /**
     * 从群组中删除会话
     * @param resolver
     * @param thread_id
     * @param group_id
     * @return
     */
    public static boolean deleteThreadGroupByThreadId(ContentResolver resolver, int thread_id,int group_id){
        int number = resolver.delete(Constant.URI.URI_THREAD_GROUP_DELETE, "thread_id = " + thread_id, null);

        if (number > 0){
            //删除会话后，改变群组的会话数量
            int thread_count = GroupDao.getThreadCount(resolver, group_id);
            GroupDao.updateThreadCount(resolver, group_id, thread_count - 1);
        }

        return number > 0;
    }
}
