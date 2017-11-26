package com.yhx.intelligentsms.ui.activity;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yhx.intelligentsms.R;
import com.yhx.intelligentsms.adapter.MainViewPagerAdapter;
import com.yhx.intelligentsms.base.BaseActivity;
import com.yhx.intelligentsms.ui.fragment.ConversationFragment;
import com.yhx.intelligentsms.ui.fragment.GroupFragmet;
import com.yhx.intelligentsms.ui.fragment.SearchFragment;
import com.yhx.intelligentsms.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private ViewPager viewPager;
    private List<Fragment> fragments;
    private TextView tv_tab_conversation;
    private TextView tv_tab_group;
    private TextView tv_tab_search;
    private MainViewPagerAdapter adapter;
    @Override
    public void initView() {
        setContentView(R.layout.activity_main);

        //拿到布局文件中的组件
        viewPager = findViewById(R.id.viewPager);
        tv_tab_conversation = findViewById(R.id.tv_tab_conversation);
        tv_tab_group = findViewById(R.id.tv_tab_group);
        tv_tab_search = findViewById(R.id.tv_tab_search);
    }

    @Override
    public void initListener() {
        //viewPager界面切换时会触发
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            //切换完成后，传入参数是切换后的界面的索引
            @Override
            public void onPageSelected(int position) {
                textLightAndScale();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void initData() {
        fragments = new ArrayList<Fragment>();
        //创建Fragment对象，存入集合
        ConversationFragment conversationFragment = new ConversationFragment();
        GroupFragmet groupFragmet = new GroupFragmet();
        SearchFragment searchFragment = new SearchFragment();
        fragments.add(conversationFragment);
        fragments.add(groupFragmet);
        fragments.add(searchFragment);
        MainViewPagerAdapter adapter = new MainViewPagerAdapter(getSupportFragmentManager(),fragments);
        viewPager.setAdapter(adapter);
        textLightAndScale();
    }

    @Override
    public void processClick(View v) {

    }

    /**
     * 改变选项卡中的文本的颜色和大小
     */
    private void textLightAndScale(){
        //获取viewPager当前显示界面的索引
        int item = viewPager.getCurrentItem();
        //根据viewPager的界面索引决定选项卡颜色
        tv_tab_conversation.setTextColor(item == 0 ? Color.WHITE : 0xaa666666);
        tv_tab_group.setTextColor(item == 1 ? Color.WHITE : 0xaa666666);
        tv_tab_search.setTextColor(item == 2 ? Color.WHITE : 0xaa666666);
    }
}
