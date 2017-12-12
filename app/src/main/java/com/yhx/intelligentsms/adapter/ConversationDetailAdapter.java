package com.yhx.intelligentsms.adapter;

import android.content.Context;
import android.database.Cursor;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.yhx.intelligentsms.R;
import com.yhx.intelligentsms.bean.Sms;
import com.yhx.intelligentsms.globle.Constant;

/**
 * Created by yhx on 2017/12/9.
 */

public class ConversationDetailAdapter extends CursorAdapter {

    private static final int DURATION = 3 * 60 * 1000;
    private ListView listView;

    public ConversationDetailAdapter(Context context, Cursor c, ListView listView) {
        super(context, c);
        this.listView = listView;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return View.inflate(context, R.layout.item_conversation_detail, null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        //组件对象全在viewHolder里
        ViewHolder viewHolder = getHolder(view);
        //数据全在sms对象里
        Sms sms = Sms.createFromCursor(cursor);
        //设置显示内容
        //判断当前短信与上一条短信的时间间隔是否超过3分钟
        //第一条短信，不需要对比
        if (cursor.getPosition() == 0){
            viewHolder.tv_conversation_detail_date.setVisibility(View.VISIBLE);
            showDate(context,viewHolder,sms);
        }else {
            long preDate = getPreviousSmsDate(cursor.getPosition());
            if (sms.getDate() - preDate > DURATION){
                viewHolder.tv_conversation_detail_date.setVisibility(View.VISIBLE);
                showDate(context,viewHolder,sms);
            }else {
                viewHolder.tv_conversation_detail_date.setVisibility(View.GONE);
            }
        }
        viewHolder.tv_conversation_detail_receive.setVisibility(sms.getType() == Constant.SMS.TYPE_RECEIVE ? View.VISIBLE : View.GONE);
        viewHolder.tv_conversation_detail_send.setVisibility(sms.getType() == Constant.SMS.TYPE_SEND ? View.VISIBLE : View.GONE);

        if (sms.getType() == Constant.SMS.TYPE_RECEIVE){
            viewHolder.tv_conversation_detail_receive.setText(sms.getBody());
        }else {
            viewHolder.tv_conversation_detail_send.setText(sms.getBody());
        }
    }

    public void showDate(Context context, ViewHolder viewHolder, Sms sms){
        //设置时间
        if (DateUtils.isToday(sms.getDate())){
            viewHolder.tv_conversation_detail_date.setText(DateFormat.getTimeFormat(context).format(sms.getDate()));
        }else {
            viewHolder.tv_conversation_detail_date.setText(DateFormat.getDateFormat(context).format(sms.getDate()));
        }
    }

    /**
     * 获取上一条短信的时间
     * @param position
     * @return
     */
    public long getPreviousSmsDate(int position){
        Cursor cursor = (Cursor) getItem(position - 1);
        Sms sms = Sms.createFromCursor(cursor);
        return sms.getDate();
    }

    @Override
    public void changeCursor(Cursor cursor) {
        super.changeCursor(cursor);
        //让listview滑动到指定条目上
        listView.setSelection(getCount());
    }

    public ViewHolder getHolder(View view){
        //先判断条目view对象中是否有viewHolder
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        if (viewHolder == null){
            //如果没有，就创建一个，并存入view对象中
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }
        return viewHolder;
    }

    class ViewHolder{

        private TextView tv_conversation_detail_date;
        private TextView tv_conversation_detail_receive;
        private TextView tv_conversation_detail_send;

        public ViewHolder(View view){
            tv_conversation_detail_date = view.findViewById(R.id.tv_conversation_detail_date);
            tv_conversation_detail_receive = view.findViewById(R.id.tv_conversation_detail_receive);
            tv_conversation_detail_send = view.findViewById(R.id.tv_conversation_detail_send);
        }
    }
}
