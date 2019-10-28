package com.reny.git.rart.ui.vm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.reny.git.rart.core.BaseViewModel;
import com.reny.git.rart.core.DCall;
import com.reny.git.rart.entity.response.SplashPushData;
import com.reny.git.rart.repo.NetRepository;
import com.reny.git.rart.utils.CommonUtils;
import com.reny.git.rart.utils.ToastUtils;

/**
 * Created by reny on 2019/10/24.
 */
public class MainVM extends BaseViewModel<NetRepository> {

    public MutableLiveData<SplashPushData> liveData = new MutableLiveData<>();

    public MainVM(@NonNull Application application) {
        super(application);
    }

    @Override
    public void loadData(boolean isRefresh) {
        super.loadData(isRefresh);
        repo.loadSplash(new DCall<SplashPushData>(this, isRefresh) {
            @Override
            public boolean isEmpty(SplashPushData value) {
                return value == null || CommonUtils.isEmpty(value.getResults());
            }

            @Override
            public void onSuc(SplashPushData data) {
                if(!isEmpty(data)) {
                    liveData.setValue(data);
                }
            }

            @Override
            public void onErr(Throwable e) {
                ToastUtils.show(e.getMessage());
            }
        });
    }
}
