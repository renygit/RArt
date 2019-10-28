package com.reny.git.rart.ui.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by reny on 2019/10/28.
 */

public class TabPagerAdapter extends FragmentStatePagerAdapter {

    private List<? extends Fragment> fragmentList;
    private String[] titles;

    public TabPagerAdapter(FragmentManager fm, List<? extends Fragment> fragmentList, String[] titles) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);//懒加载关键
        this.fragmentList = fragmentList;
        this.titles = titles;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }
}
