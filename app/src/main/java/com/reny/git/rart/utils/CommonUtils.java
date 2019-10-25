package com.reny.git.rart.utils;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.core.app.ActivityCompat;

import com.reny.git.rart.MyApp;

import java.util.List;
import java.util.Objects;

import static android.content.Context.TELEPHONY_SERVICE;

/**
 * Created by reny on 2017/6/14.
 */

public class CommonUtils {

    public static int px2dp(float pxValue) {
        final float scale = MyApp.getContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int dp2px(float dipValue) {
        final float scale = MyApp.getContext().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int px2sp(float pxValue) {
        final float fontScale = MyApp.getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    public static int sp2px(float spValue) {
        final float fontScale = MyApp.getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }


    public static boolean isEmpty(Object datas) {
        boolean isEmpty = (null == datas);
        if(null != datas){
            if(datas instanceof String) {
                isEmpty = datas.toString().trim().length() == 0;
            }else if(datas instanceof List){
                isEmpty = (((List) datas).size() == 0);
            }
        }
        return isEmpty;
    }

    public static void showSoftInput(View view) {
        if (view == null)
            return;
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        //imm.showSoftInput(view, 0);
    }

    public static void hideSoftInput(View view) {
        if (view == null)
            return;
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null && imm.isActive()) imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    //打电话前可能需要判断是否可用 防止报 No Activity found to handle Intent { act=android.intent.action.DIAL }
    public static boolean isTelephonyEnabled(Context context){
        TelephonyManager tm = (TelephonyManager)context.getSystemService(TELEPHONY_SERVICE);
        return tm != null && tm.getSimState()== TelephonyManager.SIM_STATE_READY;
    }

    public static void goTel(Context context, final String tel) {
        if (TextUtils.isEmpty(tel)) return;
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ToastUtils.show("没有拨打电话的权限");
            return;
        }
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + tel);
        intent.setData(data);
        context.startActivity(intent);
    }

    public static void goTelCommon(Context context, final String tel){
        if(TextUtils.isEmpty(tel))return;
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + tel);
        intent.setData(data);
        context.startActivity(intent);
    }



    public static void copyText(Context context, String label, String text){
        ((ClipboardManager) Objects.requireNonNull(context.getSystemService(Context.CLIPBOARD_SERVICE))).setPrimaryClip(ClipData.newPlainText(label, text));
    }

}
