package com.reny.git.rart.core;


import com.reny.git.rart.api.ApiService;

/**
 * Created by reny on 2017/2/9.
 */

public class ServiceHelper {

    public static ApiService api(){
        return (ApiService) ServiceFactory.getInstance().getService(ApiService.class);
    }

}
