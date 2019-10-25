package com.reny.git.mvvmlib.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.Objects;

import androidx.annotation.StringRes;

/**
 * Created by reny on 2017/11/15.
 */

public class AppUtils {

    private Application mApplication;
    private Toast toast = null;
    private KProgressHUD progressHUD;

    private AppUtils(){
    }

    private static class SingletonHolder {
        private static final AppUtils INSTANCE = new AppUtils();
    }

    public static AppUtils self() {
        return SingletonHolder.INSTANCE;
    }


    public void setApplication(Application mApplication) {
        this.mApplication = mApplication;
    }


    public Context getContext(){
        return mApplication;
    }


    public boolean isConnected() {
        NetworkInfo net = ((ConnectivityManager)(Objects.requireNonNull(getContext().getSystemService(Context.CONNECTIVITY_SERVICE)))).getActiveNetworkInfo();
        return net != null && net.isConnected();
    }


    public void showSnackbar(Activity activity, String message) {
        if (null == activity) {
            LogUtils.e("mActivity == null");
            return;
        }
        View view = activity.getWindow().getDecorView().findViewById(android.R.id.content);
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
    }

    public void showSnackbarLong(Activity activity, String message) {
        if (null == activity) {
            LogUtils.e("mActivity == null");
            return;
        }
        View view = activity.getWindow().getDecorView().findViewById(android.R.id.content);
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }

    @SuppressLint("ShowToast")
    public void showToast(@StringRes int resId) {
        if(null == toast){
            toast = Toast.makeText(getContext(), resId, Toast.LENGTH_SHORT);
        }
        toast.setText(resId);
        toast.show();
    }


    @SuppressLint("ShowToast")
    public void showToast(String message) {
        if(null == toast){
            toast = Toast.makeText(getContext(), message, Toast.LENGTH_SHORT);
        }
        toast.setText(message);
        toast.show();
    }


    @SuppressLint("ShowToast")
    public void showToastLong(@StringRes int resId) {
        if(null == toast){
            toast = Toast.makeText(getContext(), resId, Toast.LENGTH_LONG);
        }
        toast.setText(resId);
        toast.show();
    }


    @SuppressLint("ShowToast")
    public void showToastLong(String message) {
        if(null == toast){
            toast = Toast.makeText(getContext(), message, Toast.LENGTH_LONG);
        }
        toast.setText(message);
        toast.show();
    }


    public void showLoading(Activity activity, boolean isCancel, String... tips) {
        if(null != activity) {
            if (null == progressHUD) {
                progressHUD = KProgressHUD.create(activity)
                        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                        .setAnimationSpeed(2)
                        .setDimAmount(0.5f);
            }
            progressHUD.setCancellable(isCancel);
            if (tips.length > 0) {
                progressHUD.setLabel(tips[0]);
                if (tips.length > 1) {
                    progressHUD.setDetailsLabel(tips[1]);
                }
            }
            progressHUD.show();
        }
    }

    public void hideLoading() {
        if(null != progressHUD){
            if(progressHUD.isShowing()){
                progressHUD.dismiss();
            }
        }
    }

    public void release(){
        this.progressHUD = null;
    }

}
