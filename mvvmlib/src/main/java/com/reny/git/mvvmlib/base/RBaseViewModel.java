package com.reny.git.mvvmlib.base;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.reny.git.mvvmlib.utils.AppUtils;
import com.reny.git.mvvmlib.utils.TUtil;

/**
 * Created by reny on 2019/10/24.
 */
public abstract class RBaseViewModel<T extends RBaseRepository> extends AndroidViewModel implements IRViewModel,IRBaseCallBack{

    public final String TAG = this.getClass().getSimpleName();

    public T repo;
    private IRBaseCallBack callBack;

    public RBaseViewModel(@NonNull Application application) {
        super(application);
        repo = TUtil.getNewInstance(this, 0);
        AppUtils.self().setApplication(application);
    }

    public void setCallBack(IRBaseCallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if(null != repo){
            repo.onDestroy();
        }
    }

    @Override
    public void loadData(boolean isRefresh) {

    }

    @Override
    public void onRefreshCall(boolean isRefresh, boolean isEmpty, boolean isError) {
        if(null != callBack){
            callBack.onRefreshCall(isRefresh, isEmpty, isError);
        }
    }
}
