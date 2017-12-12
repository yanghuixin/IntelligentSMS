package com.yhx.intelligentsms.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.yhx.intelligentsms.R;
import com.yhx.intelligentsms.adapter.ConversationListAdapter;
import com.yhx.intelligentsms.base.BaseFragment;
import com.yhx.intelligentsms.bean.Conversation;
import com.yhx.intelligentsms.dao.SimpleQueryHandler;
import com.yhx.intelligentsms.globle.Constant;
import com.yhx.intelligentsms.ui.activity.ConversationDetailActivity;
import com.yhx.intelligentsms.ui.activity.GroupDetailActivity;

/**
 * Created by Administrator on 2017/11/27.
 */

public class SearchFragment extends BaseFragment {

    private EditText et_search_list;
    private ListView lv_search_list;
    private ConversationListAdapter adapter;
    private SimpleQueryHandler queryHandler;
    private String[] projection = {
            "body As snippet",
            "thread_id As _id",
            //"groups.msg_count As msg_count",
            "address As address",
            "date As date"
    };

    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //填充布局对象，返回view对象
        View view = inflater.inflate(R.layout.fragment_search,null);
        et_search_list = view.findViewById(R.id.et_search_list);
        lv_search_list = view.findViewById(R.id.lv_search_list);
        return view;
    }

    @Override
    public void initListener() {
        //添加文本改变的监听
        et_search_list.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            //文本改变，此方法调用
            //s：当前文本框里的文本
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                queryHandler.startQuery(0, adapter, Constant.URI.URI_SMS_CONVERSATION,
                        projection, "body like '%" + s + "%'", null, "date desc");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        lv_search_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //直接跳转至ConversationDetailActivity
                //进入会话
                Intent intent = new Intent(getActivity(), ConversationDetailActivity.class);
                //携带数据：address和thread_id
                Cursor cursor = (Cursor) adapter.getItem(position);
                Conversation conversation = Conversation.createFromCursor(cursor);
                intent.putExtra("address", conversation.getAddress());
                intent.putExtra("thread_id", conversation.getThreadId());
                startActivity(intent);
            }
        });
        lv_search_list.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                //输入法管理器
                //隐藏输入法软键盘
                InputMethodManager methodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                methodManager.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    @Override
    public void initData() {
        adapter = new ConversationListAdapter(getActivity(), null);
        lv_search_list.setAdapter(adapter);

        queryHandler = new SimpleQueryHandler(getActivity().getContentResolver());
    }

    @Override
    public void processClick(View v) {
        
    }

}
