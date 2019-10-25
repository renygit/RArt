package com.reny.git.mvvmlib.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.reny.git.mvvmlib.utils.AppUtils;
import com.reny.git.mvvmlib.utils.TUtil;
import com.reny.git.mvvmlib.view.MultiStateView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by reny on 2019/10/24.
 *  懒加载见：https://blog.csdn.net/qq_36486247/article/details/102531304
 * //构造 FragmentStatePagerAdapter 时传入Behavior：BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT  即可实现懒加载
 */
public abstract class RBaseFragment<T extends RBaseViewModel> extends Fragment implements IRBaseCallBack{

    private View mRoot;
    protected T vm;
    private Unbinder mUnbinder;

    private MultiStateView mMultiStateView;
    private RefreshLayout mRefreshLayout;
    private boolean isFirstLoadData = true;// 是否第一次加载数据
    private boolean isFirstLoadPage = true; // 是否第一次加载页面

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        try {
            int layoutResID = getLayoutId();
            if (layoutResID != 0) {//如果initView返回0,框架则不会调用setContentView(),当然也不会 Bind ButterKnife
                mRoot = inflater.inflate(layoutResID, container, false);
                //绑定到butterknife
                mUnbinder = ButterKnife.bind(this, mRoot);
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
        return mRoot;

    }

    @Override
    public void onResume() {
        super.onResume();
        if (isFirstLoadPage) {
            // 将数据加载逻辑放到onResume()方法中
            lazyLoad();
            isFirstLoadPage = false;
        }
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
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder != null && mUnbinder != Unbinder.EMPTY) mUnbinder.unbind();
        this.mUnbinder = null;
        this.vm = null;

        AppUtils.self().release();
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
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

    protected abstract int getLayoutId();
    protected abstract void init(Bundle savedInstanceState);
    protected abstract void lazyLoad();

    protected abstract MultiStateView getMultiStateView();
    protected abstract RefreshLayout getRefreshLayout();

}
