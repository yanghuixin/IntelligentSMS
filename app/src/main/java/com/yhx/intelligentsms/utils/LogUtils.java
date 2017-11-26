package com.yhx.intelligentsms.utils;

import android.util.Log;

/**
 * Created by Administrator on 2017/11/27.
 */

public class LogUtils {

    public static boolean isDebug = true;
    public static void i(String tag , String msg){
        if (isDebug){
            Log.i(tag,msg);
        }
    }

    public static void i(Object tag , String msg){
        if (isDebug){
            Log.i(tag.getClass().getSimpleName(),msg);
        }
    }

    public static void d(String tag , String msg){
        if (isDebug){
            Log.d(tag,msg);
        }
    }

    public static void d(Object tag , String msg){
        if (isDebug){
            Log.d(tag.getClass().getSimpleName(),msg);
        }
    }

    public static void e(String tag , String msg){
        if (isDebug){
            Log.e(tag,msg);
        }
    }

    public static void e(Object tag , String msg){
        if (isDebug){
            Log.e(tag.getClass().getSimpleName(),msg);
        }
    }
}
