package com.reny.git.mvvmlib.base;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.reny.git.mvvmlib.utils.AppUtils;
import com.reny.git.mvvmlib.utils.PermissionHelper;
import com.reny.git.mvvmlib.utils.TUtil;
import com.reny.git.mvvmlib.view.MultiStateView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by reny on 2019/10/24.
 */
public abstract class RBaseActivity<T extends RBaseViewModel> extends AppCompatActivity implements IRBaseCallBack{

    public final String TAG = this.getClass().getSimpleName();

    protected T vm;
    private Unbinder mUnbinder;

    private MultiStateView mMultiStateView;
    private RefreshLayout mRefreshLayout;
    private boolean isFirstLoadData = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            int layoutResID = getLayoutId();
            if (layoutResID != 0) {//如果initView返回0,框架则不会调用setContentView(),当然也不会 Bind ButterKnife
                setContentView(layoutResID);
                //绑定到butterknife
                mUnbinder = ButterKnife.bind(this);
            }
            initViewModel();
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMultiStateView = getMultiStateView();
        if(null != mMultiStateView){
            mMultiStateView.setOnRetryListener(() -> {
                mMultiStateView.showLoading();
                if(null != vm) {
                    vm.loadData(true);
                }
            });
        }
        mRefreshLayout = getRefreshLayout();
        if(null != mRefreshLayout){
            mRefreshLayout.setEnableAutoLoadMore(true);//开启自动加载功能（非必须）
            mRefreshLayout.setEnableHeaderTranslationContent(true);//内容跟随下拉
            mRefreshLayout.setEnableLoadMoreWhenContentNotFull(true);//是否在列表不满一页时候开启上拉加载功能
            mRefreshLayout.setEnableFooterFollowWhenNoMoreData(true);//设置是否在没有更多数据之后 Footer 跟随内容
            mRefreshLayout.setOnRefreshListener(rl -> {
                if(null != vm) {
                    vm.loadData(true);
                }
            });
            mRefreshLayout.setOnLoadMoreListener(rl -> {
                if(null != vm) {
                    vm.loadData(false);
                }
            });
        }

        init(savedInstanceState);
    }

    @Override
    public void onRefreshCall(boolean isRefresh, boolean isEmpty, boolean isError) {
        if(isRefresh && isFirstLoadData){
            if(isError){
                if(null != mMultiStateView) {
                    if(AppUtils.self().isConnected()) {
                        mMultiStateView.showError();
                    }else {
                        mMultiStateView.showNoNetwork();
                    }
                }
            }else if(isEmpty){
                if(null != mMultiStateView) mMultiStateView.showEmpty();
            }else {
                isFirstLoadData = false;
                if(null != mMultiStateView) mMultiStateView.showContent();
            }
        }
        if(null != mRefreshLayout){
            if(isRefresh) {
                mRefreshLayout.finishRefresh();
                mRefreshLayout.resetNoMoreData();
            }else {
                if(isEmpty){
                    mRefreshLayout.finishLoadMoreWithNoMoreData();
                }else {
                    mRefreshLayout.finishLoadMore();
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mUnbinder != null && mUnbinder != Unbinder.EMPTY) mUnbinder.unbind();
        this.mUnbinder = null;
        this.vm = null;

        AppUtils.self().release();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        initViewModel();
    }

    private void initViewModel(){
        if (null == vm) {
            Class<T> tClass = TUtil.getInstance(this, 0);
            if(null != tClass) {
                vm = ViewModelProviders.of(this).get(tClass);
                vm.setCallBack(this);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    protected abstract int getLayoutId();
    protected abstract void init(Bundle savedInstanceState);

    protected abstract MultiStateView getMultiStateView();
    protected abstract RefreshLayout getRefreshLayout();

}
