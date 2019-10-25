package com.reny.git.mvvmlib.base;

/**
 * Created by reny on 2019/10/24.
 */
public interface IRBaseCallBack {

    /***
     * 加载数据后调用，更新界面状态
     * @param isRefresh 是否是通过刷新加载的新数据
     * @param isEmpty 加载的新数据是否为空
     * @param isError 加载新数据时是否出错
     */
    void onRefreshCall(boolean isRefresh, boolean isEmpty, boolean isError);

}
