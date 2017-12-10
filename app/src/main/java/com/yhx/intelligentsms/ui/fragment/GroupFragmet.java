package com.yhx.intelligentsms.ui.fragment;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.yhx.intelligentsms.R;
import com.yhx.intelligentsms.base.BaseFragment;
import com.yhx.intelligentsms.globle.Constant;

/**
 * Created by Administrator on 2017/11/27.
 */

public class GroupFragmet extends BaseFragment {

    private Button bt_group_newgroup;

    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //填充布局对象，返回view对象
        View view = inflater.inflate(R.layout.fragment_group,null);
        bt_group_newgroup = view.findViewById(R.id.bt_group_newgroup);
        return view;
    }

    @Override
    public void initListener() {
        bt_group_newgroup.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void processClick(View v) {
        switch (v.getId()){
            case R.id.bt_group_newgroup:
                ContentValues values = new ContentValues();
                values.put("name", "天下英雄基本也就这几个了");
                values.put("create_date", System.currentTimeMillis());
                values.put("thread_count", 0);
                getActivity().getContentResolver().insert(Constant.URI.URI_GROUP_INSERT, values);
        }
    }
}
