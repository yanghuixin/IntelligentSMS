package com.yhx.intelligentsms.adapter;

import android.content.Context;
import android.database.Cursor;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.yhx.intelligentsms.R;
import com.yhx.intelligentsms.bean.Group;

/**
 * Created by Administrator on 2017/12/12.
 */

public class GroupListAdapter extends CursorAdapter {

    public GroupListAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return View.inflate(context, R.layout.item_group_list, null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = getHolder(view);

        Group group = Group.createFromCursor(cursor);

        viewHolder.tv_grouplist_name.setText(group.getName() + " (" + group.getThread_count() + ")");
        if (DateUtils.isToday(group.getCreate_date())){
            viewHolder.tv_grouplist_date.setText(DateFormat.getTimeFormat(context).format(group.getCreate_date()));
        }else {
            viewHolder.tv_grouplist_date.setText(DateFormat.getDateFormat(context).format(group.getCreate_date()));
        }
    }

    public ViewHolder getHolder(View view){
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        if (viewHolder == null){
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }
        return viewHolder;
    }

    class ViewHolder{
        private TextView tv_grouplist_name;
        private TextView tv_grouplist_date;

        public ViewHolder(View view){
            tv_grouplist_name = view.findViewById(R.id.tv_grouplist_name);
            tv_grouplist_date = view.findViewById(R.id.tv_grouplist_date);
        }
    }
}
