package com.yhx.intelligentsms.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

/**
 * Created by Administrator on 2017/11/30.
 */

public class ConversationListAdapter extends CursorAdapter {

    public ConversationListAdapter(Context context, Cursor c) {
        super(context, c);
    }

    //返回的view对象就是ListView的条目
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return null;
    }

    //设置ListView每个条目显示的内容
    @Override
    public void bindView(View view, Context context, Cursor cursor) {

    }
}
