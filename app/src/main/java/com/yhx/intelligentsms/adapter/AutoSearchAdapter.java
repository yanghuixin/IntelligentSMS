package com.yhx.intelligentsms.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.yhx.intelligentsms.R;

/**
 * Created by yhx on 2017/12/10.
 */

public class AutoSearchAdapter extends CursorAdapter {

    public AutoSearchAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return View.inflate(context, R.layout.item_auto_search_tv, null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = getHolder(view);
        viewHolder.tv_auto_search_name.setText(cursor.getString(cursor.getColumnIndex("display_name")));
        viewHolder.tv_auto_search_address.setText(cursor.getString(cursor.getColumnIndex("data1")));
    }

    //点击下列列表条目时的返回值
    @Override
    public CharSequence convertToString(Cursor cursor) {
        return cursor.getString(cursor.getColumnIndex("data1"));
    }

    private ViewHolder getHolder(View view){
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        if (viewHolder == null){
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }
        return viewHolder;
    }

    class ViewHolder{
        private TextView tv_auto_search_name;
        private TextView tv_auto_search_address;

        public ViewHolder(View view){
            tv_auto_search_name = view.findViewById(R.id.tv_auto_search_name);
            tv_auto_search_address = view.findViewById(R.id.tv_auto_search_address);
        }
    }

}
