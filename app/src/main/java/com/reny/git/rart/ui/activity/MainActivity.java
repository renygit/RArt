package com.reny.git.rart.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.reny.git.mvvmlib.utils.LogUtils;
import com.reny.git.rart.R;
import com.reny.git.rart.R2;
import com.reny.git.rart.core.BaseActivity;
import com.reny.git.rart.ui.router.RConfig;
import com.reny.git.rart.ui.vm.MainVM;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = RConfig.main)
public class MainActivity extends BaseActivity<MainVM> {

    @BindView(R2.id.tv)
    TextView tv;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        vm.liveData.observe(this, splashPushData -> {
            if(vm.isRefresh) {
                LogUtils.e("splashPushData...init");
            }else {
                LogUtils.e("splashPushData...loadmore");
            }

            tv.setText(splashPushData.getResults().get(0).getTitle());
        });
        vm.loadData(true);
    }

    @OnClick({R2.id.tv})
    public void onViewClick(View view) {
        ARouter.getInstance().build(RConfig.second).withInt("age", 12).navigation();
    }

}
