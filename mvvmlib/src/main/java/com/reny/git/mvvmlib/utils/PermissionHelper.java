package com.reny.git.mvvmlib.utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/**
 * Created by reny on 2019/10/9.
 */
public class PermissionHelper {

    private static Map<Integer, Runnable> mapGrantedRunnable;
    private static Map<Integer, Runnable> mapDeniedRunnable;
    private static int mRequestCode = 1;


    public static void requestPermissions(Activity activity, Runnable grantedRunnable, Runnable deniedRunnable, String... permissions){
        if(null == mapGrantedRunnable){
            mapGrantedRunnable = new HashMap<>();
        }
        if(null == mapDeniedRunnable){
            mapDeniedRunnable = new HashMap<>();
        }

        //用于存放为授权的权限
        List<String> permissionList = new ArrayList<>();
        //遍历传递过来的权限集合
        for (String permission : permissions) {
            //判断是否已经授权
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED){
                //未授权，则加入待授权的权限集合中
                permissionList.add(permission);
            }
        }

        //判断集合
        if (!permissionList.isEmpty()){  //如果集合不为空，则需要去授权
            mapGrantedRunnable.put(mRequestCode, grantedRunnable);
            mapDeniedRunnable.put(mRequestCode, deniedRunnable);
            ActivityCompat.requestPermissions(activity, permissionList.toArray(new String[0]), mRequestCode);
            mRequestCode++;
        }else{  //为空，则已经全部授权
            new Handler(Looper.getMainLooper()).post(grantedRunnable);
        }
    }

    public static void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (mapGrantedRunnable.containsKey(requestCode)) {
            // 这里规定全部权限都通过才算通过
            boolean grant = true;
            // 在A申请权限，然后马上跳转到B，则grantResults.length=0
            if (grantResults.length == 0) grant = false;
            for (int result : grantResults) {
                if (result == PackageManager.PERMISSION_DENIED) {
                    grant = false;
                }
            }
            if (grant) {
                new Handler(Looper.getMainLooper()).post(mapGrantedRunnable.get(requestCode));
            } else {
                new Handler(Looper.getMainLooper()).post(mapDeniedRunnable.get(requestCode));
            }
            mapGrantedRunnable.remove(requestCode);
            mapDeniedRunnable.remove(requestCode);
        }
    }

}
