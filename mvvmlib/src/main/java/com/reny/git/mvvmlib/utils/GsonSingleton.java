package com.reny.git.mvvmlib.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by reny on 2017/6/6.
 */

public class GsonSingleton {

    public static final Gson gson = new GsonBuilder().setLenient().create();

}
