package com.yhx.intelligentsms.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yhx.intelligentsms.R;
import com.yhx.intelligentsms.bean.Conversation;
import com.yhx.intelligentsms.dao.ContactDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/30.
 */

public class ConversationListAdapter extends CursorAdapter {

    private boolean isSelectMode = false;
    private List<Integer> selectedConversationIds = new ArrayList<Integer>();


    //记录选择模式下选中哪些条目
    List<Integer> selectedConversationId = new ArrayList<>();

    public boolean isSelectMode() {
        return isSelectMode;
    }

    public void setSelectMode(boolean selectMode) {
        isSelectMode = selectMode;
    }

    public ConversationListAdapter(Context context, Cursor c) {
        super(context, c);
    }

    //返回的view对象就是ListView的条目
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return View.inflate(context, R.layout.item_conversation_list,null);
    }

    //设置ListView每个条目显示的内容
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = getHolder(view);
        //根据cursor内容创建会话对象，此时cursor的指针已经移动至正确的位置
        Conversation conversation = Conversation.createFromCursor(cursor);

        //判断当前是否进入选择模式
        if (isSelectMode){
            viewHolder.tv_check.setVisibility(View.VISIBLE);
            //判断集合中是否包含会话id,从而确定该条目是否被选中
            if (selectedConversationIds.contains(conversation.getThreadId())){
                viewHolder.tv_check.setBackgroundResource(R.drawable.common_checkbox_checked);
            }else {
                viewHolder.tv_check.setBackgroundResource(R.drawable.common_checkbox_normal);
            }
        }else {
            viewHolder.tv_check.setVisibility(View.GONE);
        }
        //判断当前是否进入选择模式
        if (isSelectMode){
            viewHolder.iv_check.setVisibility(View.VISIBLE);
            if (selectedConversationId.contains(conversation.getThreadId())){
                viewHolder.iv_check.setBackgroundResource(R.drawable.common_checkbox_checked);
            }else {
                viewHolder.iv_check.setBackgroundResource(R.drawable.common_checkbox_normal);
            }
        }else {
            viewHolder.iv_check.setVisibility(View.GONE);
        }
        //设置号码
        //按号码查询是否存有联系人
        String name = ContactDao.getNameByAddress(context.getContentResolver(),conversation.getAddress());
        if (TextUtils.isEmpty(name)){
            viewHolder.tv_conversation_address.setText(conversation.getAddress() + "(" + conversation.getMsgCount() + ")");
        }else {
            viewHolder.tv_conversation_address.setText(name + "(" + conversation.getMsgCount() + ")");
        }
        //设置短信内容
        viewHolder.tv_conversation_body.setText(conversation.getSnippet());
        //设置时间
        //判断是否今天
        if (DateUtils.isToday(conversation.getDate())){
            //如果是显示时分
            viewHolder.tv_conversation_date.setText(DateFormat.getTimeFormat(context).format(conversation.getDate()));
        }else {
            //如果不是显示年月日
            viewHolder.tv_conversation_date.setText(DateFormat.getDateFormat(context).format(conversation.getDate()));
        }

        //获取联系人头像
        Bitmap bitmap = ContactDao.getAvatarByAddress(context.getContentResolver(), conversation.getAddress());
        //判断是否成功拿到图片
        if (bitmap == null){
            viewHolder.iv_conversation_avatar.setBackgroundResource(R.drawable.img_default_avatar);
        }else {
            viewHolder.iv_conversation_avatar.setBackground(new BitmapDrawable(bitmap));
        }
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
        private ImageView iv_conversation_avatar;
        private TextView tv_conversation_address;
        private TextView tv_conversation_body;
        private TextView tv_conversation_date;
        private ImageView tv_check;
        private ImageView iv_check;
        //参数就是条目的view对象
        public ViewHolder(View view){
            iv_conversation_avatar = view.findViewById(R.id.iv_conversation_avatar);
            tv_conversation_address = view.findViewById(R.id.tv_conversation_address);
            tv_conversation_body = view.findViewById(R.id.tv_conversation_body);
            tv_conversation_date = view.findViewById(R.id.tv_conversation_date);
            tv_check = view.findViewById(R.id.tv_check);
        }
    }

    /**
     * 把选中的条目存入集合
     * @param position
     */
    public void selectSingle(int position){
        //从cursor中取出position对应的会话
        Cursor cursor = (Cursor) getItem(position);
        Conversation conversation = Conversation.createFromCursor(cursor);
        if (selectedConversationIds.contains(conversation.getThreadId())){
            //强转为Integer，否则是把参数作为索引而不是要删除的yuan
            selectedConversationIds.remove((Integer) conversation.getThreadId());
        }else {
            selectedConversationIds.add(conversation.getThreadId());
        }
        notifyDataSetChanged();
    }

    public List<Integer> getSelectedConversationIds() {
        return selectedConversationIds;
    }

}
