package com.yhx.intelligentsms.ui.activity;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nineoldandroids.view.ViewPropertyAnimator;
import com.yhx.intelligentsms.R;
import com.yhx.intelligentsms.adapter.MainViewPagerAdapter;
import com.yhx.intelligentsms.base.BaseActivity;
import com.yhx.intelligentsms.ui.fragment.ConversationFragment;
import com.yhx.intelligentsms.ui.fragment.GroupFragmet;
import com.yhx.intelligentsms.ui.fragment.SearchFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private ViewPager viewPager;
    private List<Fragment> fragments;
    private TextView tv_tab_conversation;
    private TextView tv_tab_group;
    private TextView tv_tab_search;
    private MainViewPagerAdapter adapter;
    private LinearLayout ll_tab_conversation;
    private LinearLayout ll_tab_group;
    private LinearLayout ll_tab_search;
    private View v_indicate_line;
    @Override
    public void initView() {
        setContentView(R.layout.activity_main);

        //拿到布局文件中的组件
        viewPager = findViewById(R.id.viewPager);
        tv_tab_conversation = findViewById(R.id.tv_tab_conversation);
        tv_tab_group = findViewById(R.id.tv_tab_group);
        tv_tab_search = findViewById(R.id.tv_tab_search);
        ll_tab_conversation = findViewById(R.id.ll_tab_conversation);
        ll_tab_group = findViewById(R.id.ll_tab_group);
        ll_tab_search = findViewById(R.id.ll_tab_search);
        v_indicate_line = findViewById(R.id.v_indicate_line);
    }

    @Override
    public void initListener() {
        //viewPager界面切换时会触发
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            //滑动过程中不断调用
            //如果滑动过程中出现两个界面，position是前一个的索引
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //计算红线位移的距离
                int distance = positionOffsetPixels / 3;
                //持续时间为0立刻生效，因为红线的移动需要与用户滑动保持一致
                ViewPropertyAnimator.animate(v_indicate_line).translationX(distance + position * v_indicate_line.getWidth()).setDuration(0);
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

        //给三个选项卡设置点击事件
        ll_tab_conversation.setOnClickListener(this);
        ll_tab_group.setOnClickListener(this);
        ll_tab_search.setOnClickListener(this);
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
        adapter = new MainViewPagerAdapter(getSupportFragmentManager(),fragments);
        viewPager.setAdapter(adapter);
        textLightAndScale();

        //设置红线宽度
        computeIndicateLineWidth();
    }

    @Override
    public void processClick(View v) {
        switch (v.getId()){
            case R.id.ll_tab_conversation:
                //改变ViewPager界面
                viewPager.setCurrentItem(0);
                break;
            case R.id.ll_tab_group:
                viewPager.setCurrentItem(1);
                break;
            case R.id.ll_tab_search:
                viewPager.setCurrentItem(2);
                break;
        }
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
        //要操作的对象,改变宽度至指定比例
        ViewPropertyAnimator.animate(tv_tab_conversation).scaleX(item == 0 ? 1.2f : 1).setDuration(200);
        ViewPropertyAnimator.animate(tv_tab_group).scaleX(item == 1 ? 1.2f : 1).setDuration(200);
        ViewPropertyAnimator.animate(tv_tab_search).scaleX(item == 2 ? 1.2f : 1).setDuration(200);
        //要操作的对象,改变高度至指定比例
        ViewPropertyAnimator.animate(tv_tab_conversation).scaleY(item == 0 ? 1.2f : 1).setDuration(200);
        ViewPropertyAnimator.animate(tv_tab_group).scaleY(item == 1 ? 1.2f : 1).setDuration(200);
        ViewPropertyAnimator.animate(tv_tab_search).scaleY(item == 2 ? 1.2f : 1).setDuration(200);
    }

    /**
     * 设置红线宽度为屏幕的三分之一
     */
    public void computeIndicateLineWidth(){
        int width = getWindowManager().getDefaultDisplay().getWidth();
        v_indicate_line.getLayoutParams().width = width / 3;
    }
}
