package com.reny.git.rart.core;

import com.reny.git.mvvmlib.base.RBaseViewModel;
import com.reny.git.mvvmlib.utils.AppUtils;
import com.reny.git.mvvmlib.utils.LogUtils;

import io.reactivex.observers.DisposableObserver;

/**
 * Created by reny on 2017/11/16.
 */

public abstract class DCall<T> extends DisposableObserver<T> {

    private RBaseViewModel vm;
    private boolean isRefresh;

    protected DCall(){

    }
    protected DCall(RBaseViewModel vm, boolean isRefresh) {
        this.vm = vm;
        this.isRefresh = isRefresh;
    }

    @Override
    public void onNext(T value) {
        onSuc(value);
        AppUtils.self().hideLoading();
        if (null != vm) {
            vm.onRefreshCall(isRefresh, isEmpty(value), false);
        }
    }

    @Override
    public void onError(Throwable e) {
        AppUtils.self().hideLoading();
        if (null != vm) {
            vm.onRefreshCall(isRefresh, false, true);
        }
        LogUtils.e((null != vm ? vm.TAG : "vm == null") + "："+ e.getMessage());
        onErr(e);
    }

    @Override
    public void onComplete() {
        AppUtils.self().hideLoading();
    }

    public abstract boolean isEmpty(T value);

    public abstract void onSuc(T data);

    public abstract void onErr(Throwable e);

}
