package com.reny.git.rart.utils;

import com.reny.git.mvvmlib.utils.AppUtils;

public class ToastUtils {

    public static void show(String message) {
        AppUtils.self().showToastLong(message);
    }

}
