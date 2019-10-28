package com.reny.git.rart.core;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.alibaba.android.arouter.launcher.ARouter;
import com.jaeger.library.StatusBarUtil;
import com.jude.swipbackhelper.SwipeBackHelper;
import com.reny.git.mvvmlib.base.RBaseActivity;
import com.reny.git.mvvmlib.base.RBaseViewModel;
import com.reny.git.mvvmlib.view.MultiStateView;
import com.reny.git.rart.R;
import com.reny.git.rart.utils.SwipeBackUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

/**
 * Created by reny on 2019/10/24.
 */
public abstract class BaseActivity<T extends RBaseViewModel> extends RBaseActivity<T> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        if (isEnableSwipeBack()) {
            SwipeBackHelper.onCreate(this);
            SwipeBackUtils.EnableSwipeActivity(this, 0.1f);
        }

        if (isTranslucentStatusBar()) {
            StatusBarUtil.setTranslucentForImageViewInFragment(this, null);
        }

        super.onCreate(savedInstanceState);

        if (null != getToolbar()) {
            getToolbar().setTitle("");
            setSupportActionBar(getToolbar());
            //给左上角图标的左边加上一个返回的图标
            if(null != getSupportActionBar()) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
            getToolbar().setNavigationOnClickListener(v -> onBackPressed());
        }
    }

    @Override
    protected MultiStateView getMultiStateView() {
        View msv = getWindow().getDecorView().findViewById(R.id.msv);
        if(msv instanceof MultiStateView){
            return (MultiStateView)msv;
        }
        return null;
    }

    @Override
    protected RefreshLayout getRefreshLayout() {
        View srl = getWindow().getDecorView().findViewById(R.id.srl);
        if(srl instanceof RefreshLayout){
            return (RefreshLayout)srl;
        }
        return null;
    }

    protected Toolbar getToolbar(){
        View toolbar = getWindow().getDecorView().findViewById(R.id.toolbar);
        if(toolbar instanceof Toolbar){
            return (Toolbar)toolbar;
        }
        return null;
    }

    //是否启动滑动退出
    protected boolean isEnableSwipeBack() {
        return true;
    }

    //是否设置状态栏为透明
    protected boolean isTranslucentStatusBar() {
        return false;
    }

}
