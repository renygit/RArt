package com.reny.git.rart.ui.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.reny.git.mvvmlib.utils.LogUtils;
import com.reny.git.mvvmlib.view.MultiStateView;
import com.reny.git.rart.R;
import com.reny.git.rart.core.BaseActivity;
import com.reny.git.rart.ui.router.RConfig;
import com.reny.git.rart.ui.vm.MainVM;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import butterknife.BindView;

@Route(path = RConfig.main)
public class MainActivity extends BaseActivity<MainVM> {

    @BindView(R.id.tv)
    TextView tv;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        vm.liveData.observe(this, splashPushData -> {
            LogUtils.e("splashPushData...get");

            tv.setText(splashPushData.getResults().get(0).getTitle());
        });
        vm.loadData(true);
    }

}
