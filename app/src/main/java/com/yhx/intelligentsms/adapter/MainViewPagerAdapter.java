package com.yhx.intelligentsms.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

import java.util.List;

/**
 * Created by Administrator on 2017/11/27.
 */

public class MainViewPagerAdapter extends FragmentPagerAdapter {

    List<Fragment> fragments;
    public MainViewPagerAdapter(FragmentManager fm , List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    //返回fragment对象会作为viewpager的条目
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
