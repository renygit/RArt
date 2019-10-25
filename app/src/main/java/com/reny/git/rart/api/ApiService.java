package com.reny.git.rart.api;

import com.reny.git.rart.entity.response.SplashPushData;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by reny on 2017/1/5.
 */

public interface ApiService {

    //BaseServiceFactory->createService() 获取属性为BASE_URL的值
    String BASE_URL = APIConfig.BASE_URL;

    @GET("api/history/content/2/{page}")
    Observable<SplashPushData> getSplashData(@Path("page") int page);

}
