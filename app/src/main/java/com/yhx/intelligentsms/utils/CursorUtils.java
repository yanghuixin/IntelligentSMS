package com.yhx.intelligentsms.utils;

import android.database.Cursor;

/**
 * Created by Administrator on 2017/11/28.
 */

public class CursorUtils {

    public static void printCursor(Cursor cursor){
        LogUtils.i(cursor, "一共有" + cursor.getCount() + "条数据");
        while (cursor.moveToNext()){
            for (int i = 0 ; i < cursor.getColumnCount() ; i ++){
                String name = cursor.getColumnName(i);
                String content = cursor.getString(i);
                LogUtils.i(cursor, name + ":" + content);
            }
            LogUtils.i(cursor, "=========================");
        }
    }
}
