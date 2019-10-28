package com.reny.git.rart.ui.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.flyco.tablayout.SlidingTabLayout;
import com.reny.git.rart.R;
import com.reny.git.rart.R2;
import com.reny.git.rart.core.BaseActivity;
import com.reny.git.rart.ui.adapter.TabPagerAdapter;
import com.reny.git.rart.ui.fragment.TestFragment;
import com.reny.git.rart.ui.router.RConfig;
import com.reny.git.rart.ui.vm.SecondVM;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindArray;
import butterknife.BindView;

@Route(path = RConfig.second)
public class SecondActivity extends BaseActivity<SecondVM> {

    @Autowired
    int age;
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.vp)
    ViewPager vp;
    @BindView(R2.id.stl)
    SlidingTabLayout stl;

    @BindArray(R2.array.tabNames)
    String[] tabNames;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_second;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        tvTitle.setText("标题" + age);

        List<Fragment> fragmentList = new ArrayList<>(tabNames.length);
        fragmentList.add(new TestFragment(0));
        fragmentList.add(new TestFragment(1));
        fragmentList.add(new TestFragment(2));

        TabPagerAdapter adapter = new TabPagerAdapter(getSupportFragmentManager(), fragmentList, tabNames);
        vp.setAdapter(adapter);
        vp.setOffscreenPageLimit(adapter.getCount());
        stl.setViewPager(vp);
        stl.setCurrentTab(0);
    }

}
