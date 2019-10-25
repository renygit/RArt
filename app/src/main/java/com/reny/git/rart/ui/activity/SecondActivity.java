package com.reny.git.rart.ui.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.reny.git.rart.R;
import com.reny.git.rart.core.BaseActivity;
import com.reny.git.rart.ui.router.RConfig;
import com.reny.git.rart.ui.vm.SecondVM;

@Route(path = RConfig.second)
public class SecondActivity extends BaseActivity<SecondVM> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_second;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

}
