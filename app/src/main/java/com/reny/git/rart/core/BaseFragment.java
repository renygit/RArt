package com.reny.git.rart.core;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.launcher.ARouter;
import com.reny.git.mvvmlib.base.RBaseFragment;
import com.reny.git.mvvmlib.base.RBaseViewModel;
import com.reny.git.mvvmlib.view.MultiStateView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

/**
 * Created by reny on 2019/10/24.
 */
public abstract class BaseFragment<T extends RBaseViewModel> extends RBaseFragment<T> {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected MultiStateView getMultiStateView() {
        return null;
    }

    @Override
    protected RefreshLayout getRefreshLayout() {
        return null;
    }
}
