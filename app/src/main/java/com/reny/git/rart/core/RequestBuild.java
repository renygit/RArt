package com.reny.git.rart.core;

import android.text.TextUtils;

import com.reny.git.mvvmlib.utils.GsonSingleton;
import com.reny.git.mvvmlib.utils.LogUtils;
import com.reny.git.rart.utils.GZIPUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.internal.Util;

/**
 * Created by admin on 2017/7/4.
 */

public class RequestBuild {

    private static final MediaType MT_JSON = MediaType.parse("application/json; charset=utf-8");
    private static final MediaType MT_FILE = MediaType.parse("application/octet-stream");

    private RequestObj command;
    private Object data;

    public RequestBuild setCommand(String commandName) {
        if(null == command) {
            command = new RequestObj();
        }
        if(!TextUtils.isEmpty(commandName)) {
            command.set("CommandName", commandName);
        }
        /*if(LoginData.isLogin() && !TextUtils.isEmpty(LoginData.self.getToken())){
            command.set("Token", LoginData.self.getToken());
        }else {
            String _UUID = SPUtils.init(R.string.Token).getString(R.string.Token);
            if(TextUtils.isEmpty(_UUID)){
                _UUID = UUID.randomUUID().toString();
                SPUtils.init(R.string.Token).putString(R.string.Token, _UUID);
            }
            command.set("Token", _UUID);
        }
        *//** 客户端版本 *//*
        command.set("ClientVersion", AppUtils.getVersionName(MyApp.getContext()));
        *//** 客户端类型 *//*
        command.set("PlatformType", Enumerations.PlatformType.TDW.getData());

        if (!TextUtils.isEmpty(LocationData.self().getBdLocation().getAddrStr())) {
            command.set("Position", LocationData.self().getBdLocation().getAddrStr());
        }

        command.set("TripleDesKey","");
        command.set("TripleDesIV","");
        command.set("EncryptData","");*/
        return this;
    }

    //扩展参数
    public RequestBuild setCommandExt(String k, Object v) {
        //command 不能为空 先调用setCommand(String commandName)
        command.set(k, v);
        return this;
    }


    //如果要复用这个类的对象 请先清除data，以免数据出错 不建议使用 因为会重复创建多个data对象
    @Deprecated
    public RequestBuild clearData(){
        data = null;
        return this;
    }

    //设置data数据
    public RequestBuild set(String k, Object v) {
        if(null == data){
            data = new RequestObj();
        }
        if(v instanceof List){
            try {
                v =  new JSONArray(GsonSingleton.gson.toJson(v));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        ((RequestObj)data).set(k, v);
        return this;
    }

    //设置data数据 支持实体类型 JSONObject类型
    public RequestBuild setData(Object data) {
        this.data = data;
        return this;
    }

    public RequestBody build(){
        if(null == command){
            return null;
        }else {
            if(null != data){
                if(data instanceof JSONObject){
                    command.set("Data", data);
                }else {
                    try {
                        command.set("Data", new JSONObject(GsonSingleton.gson.toJson(data)));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }else {
                command.set("Data", new JSONObject());
            }

            if(LogUtils.isPrintResponseData) {
                LogUtils.w("request:" + command.toString());
            }

            RequestBody requestBody = null;
            try {
                requestBody = RequestBody.create(MT_JSON, GZIPUtils.compress(command.toString().getBytes(Util.UTF_8)));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return requestBody;

        }
    }


    public RequestBody build(File file){
        return new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("image", "test.jpg", RequestBody.create(MT_FILE, file))
                .build();
    }



    /**
     * 仅仅去掉异常处理 实质就是JSONObject
     */
    private class RequestObj extends JSONObject {

        public RequestObj set(String k, Object v){
            try {
                this.put(k, v);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return this;
        }

    }

}
