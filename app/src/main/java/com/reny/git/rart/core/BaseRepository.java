package com.reny.git.rart.core;

import com.reny.git.mvvmlib.base.RBaseRepository;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;

/**
 * Created by reny on 2019/10/24.
 */
public abstract class BaseRepository extends RBaseRepository {

    private Map<String, RequestBuild> requestMap;

    public RequestBuild getRequest(@NonNull String key){
        if(null == requestMap){
            requestMap = new HashMap<>();
        }
        if(!requestMap.containsKey(key) || null == requestMap.get(key)){
            RequestBuild request = new RequestBuild();
            requestMap.put(key, request);
            //LogUtils.e("创建了一个RequestBuild...");
            return request;
        }
        //LogUtils.e("复用了一个RequestBuild");
        return requestMap.get(key);
    }

}
