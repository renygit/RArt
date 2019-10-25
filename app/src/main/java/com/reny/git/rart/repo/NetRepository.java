package com.reny.git.rart.repo;

import com.reny.git.rart.core.BaseRepository;
import com.reny.git.rart.core.DCall;
import com.reny.git.rart.core.ServiceHelper;
import com.reny.git.rart.entity.response.SplashPushData;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

/**
 * Created by reny on 2019/10/24.
 */
public class NetRepository extends BaseRepository {


    public void loadSplash(DCall<SplashPushData> call){
        //RequestBody requestBody = getRequest("GetAdStartData").setCommand("AndroidPushQueryService/GetAdStartData").set("Count", 3).build();
        addDisposable(ServiceHelper.api().getSplashData(1)
                .subscribeOn((Schedulers.io()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(call));
    }


}
