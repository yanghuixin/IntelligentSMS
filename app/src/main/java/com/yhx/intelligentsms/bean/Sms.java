package com.yhx.intelligentsms.bean;

import android.database.Cursor;

/**
 * Created by yhx on 2017/12/10.
 */

public class Sms {

    private String body;
    private int type;
    private long date;
    private int _id;
    /**
     * 创建会话bean对象
     * @param cursor
     * @return
     */
    public static Sms createFromCursor(Cursor cursor){
        Sms sms = new Sms();
        sms.setBody(cursor.getString(cursor.getColumnIndex("body")));
        sms.setType(cursor.getInt(cursor.getColumnIndex("type")));
        sms.setDate(cursor.getLong(cursor.getColumnIndex("date")));
        sms.set_id(cursor.getInt(cursor.getColumnIndex("_id")));
        return sms;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }
}
