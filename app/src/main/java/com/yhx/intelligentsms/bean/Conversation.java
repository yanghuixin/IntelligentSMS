package com.yhx.intelligentsms.bean;

import android.database.Cursor;

/**
 * Created by Administrator on 2017/11/30.
 */

public class Conversation {
    private String snippet;
    private int threadId;
    private String msgCount;
    private long date;
    private String address;

    /**
     * 创建会话bean对象
     * @param cursor
     * @return
     */
    public static Conversation createFromCursor(Cursor cursor){
        Conversation conversation = new Conversation();
        conversation.setSnippet(cursor.getString(cursor.getColumnIndex("snippet")));
        conversation.setThreadId(cursor.getInt(cursor.getColumnIndex("_id")));
        conversation.setMsgCount(cursor.getString(cursor.getColumnIndex("msg_count")));
        conversation.setAddress(cursor.getString(cursor.getColumnIndex("address")));
        conversation.setDate(cursor.getLong(cursor.getColumnIndex("date")));
        return conversation;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public int getThreadId() {
        return threadId;
    }

    public void setThreadId(int threadId) {
        this.threadId = threadId;
    }

    public String getMsgCount() {
        return msgCount;
    }

    public void setMsgCount(String msgCount) {
        this.msgCount = msgCount;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
