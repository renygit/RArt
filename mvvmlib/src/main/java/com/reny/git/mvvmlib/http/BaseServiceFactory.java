package com.reny.git.mvvmlib.http;

import com.google.gson.Gson;
import com.reny.git.mvvmlib.http.converter.GsonConverterFactory;
import com.reny.git.mvvmlib.utils.LogUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by reny on 2017/1/5.
 */

public abstract class BaseServiceFactory<S> {

    private Map<Class<S>, S> serviceMap;

    public S getService(Class<S> serviceClass) {
        if (null == serviceMap) {
            serviceMap = new HashMap<>();
        }
        if (null != serviceMap.get(serviceClass)) {
            return serviceMap.get(serviceClass);
        } else {
            S service = createService(serviceClass);
            serviceMap.put(serviceClass, service);
            return service;
        }
    }

    public S createService(Class<S> serviceClass) {
        String baseUrl = getDefaultBaseUrl();
        try {
            Field field1 = serviceClass.getField("BASE_URL");
            baseUrl = (String) field1.get(serviceClass);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            LogUtils.e("Your Service NoSuchFieldException: BASE_URL");
        } catch (IllegalAccessException e) {
            e.getMessage();
            e.printStackTrace();
            LogUtils.e("IllegalAccessException");
        }
        assert baseUrl != null;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create(getGson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(serviceClass);
    }

    protected abstract OkHttpClient getOkHttpClient();

    @Nullable
    protected abstract String getDefaultBaseUrl();

    @Nullable
    protected Gson getGson(){
        return null;
    }

}
