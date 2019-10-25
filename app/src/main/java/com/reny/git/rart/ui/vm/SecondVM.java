package com.reny.git.rart.ui.vm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.reny.git.rart.core.BaseViewModel;
import com.reny.git.rart.entity.response.SplashPushData;
import com.reny.git.rart.repo.NetRepository;

/**
 * Created by reny on 2019/10/24.
 */
public class SecondVM extends BaseViewModel<NetRepository> {

    public MutableLiveData<SplashPushData> liveData = new MutableLiveData<>();

    public SecondVM(@NonNull Application application) {
        super(application);
    }

    @Override
    public void loadData(boolean isRefresh) {

    }
}
