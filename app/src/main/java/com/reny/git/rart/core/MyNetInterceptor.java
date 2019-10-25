package com.reny.git.rart.core;

import com.reny.git.mvvmlib.utils.LogUtils;
import com.reny.git.rart.api.APIConfig;
import com.reny.git.rart.entity.response.GlobleResponse;
import com.reny.git.rart.utils.GZIPUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.Util;

/**
 * Created by admin on 2017/6/7.
 */

public class MyNetInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder requestBuilder = chain.request().newBuilder();
        requestBuilder.removeHeader("Accept-Encoding");
        requestBuilder.addHeader("Referer", APIConfig.Referer);
        Request request = requestBuilder.build();
        /*Headers headers = request.headers();
        for (int i = 0; i < headers.size(); i++) {
            LogUtils.e("oldheader   "+headers.name(i) + ": " + headers.value(i));
        }*/
        //LogUtils.e(request.url().url().toString());//打印要访问的地址 可注释
        Response response = chain.proceed(request);

        if (response.code() == 200) {
            byte[] decodeBytes;
            try {
                byte[] bytes = response.body().bytes();
                decodeBytes = GZIPUtils.decompress(bytes);
            } catch (Exception e) {
                e.printStackTrace();
                //LogUtils.e("responseBody解析出错："+response.code()+"--"+response.message());
                throw new ResultException(ResultException.UNKNOWNERROR, "数据解压出错");
            }

            if (null != decodeBytes) {
                String result = new String(decodeBytes, Util.UTF_8);
                //LogUtils.e("处理前："+result);
                try {
                    response = handlerResponse(response, new JSONObject(result));
                } catch (JSONException e) {
                    e.printStackTrace();
                    throw new ResultException(ResultException.UNKNOWNERROR, "json解析出错");
                }
                //LogUtils.e("再次打印（处理后）："+response.body().string());
                return response;
            } else {
                throw new ResultException(ResultException.UNKNOWNERROR, "返回数据为空");
            }

        } else {
            LogUtils.e("访问出错："+response.code()+"--"+response.message());
            throw new ResultException(response.code(), "网络访问出错，请检查网络后重试");
        }
    }


    private Response handlerResponse(Response response, JSONObject jsonObject) {
        if (null != jsonObject) {
            GlobleResponse globleResponse = new GlobleResponse();
            try {
                //globleResponse.setCommandName(jsonObject.getString("CommandName"));
                globleResponse.setErrorCode(jsonObject.getInt("ErrorCode"));

                if (globleResponse.getErrorCode() == ResultException.SUCCESSCODE) {
                    if (jsonObject.has("Data") && !jsonObject.isNull("Data")) {
                        JSONObject Data = jsonObject.getJSONObject("Data");
                        response = response.newBuilder()
                                .body(ResponseBody.create(null, Data.toString()))
                                .build();
                    }

                } else {
                    globleResponse.setErrorMessage(jsonObject.getString("ErrorMessage"));
                    String CommandName = "";
                    if (jsonObject.has("CommandName")){
                        CommandName = jsonObject.getString("CommandName");
                    }
                    switch (globleResponse.getErrorCode()){
                        case ResultException.LOGIN_OVERDUE:
                            //访问某个接口时报 登录超时
                            LogUtils.e("登录过期,请重新登录" + globleResponse.getCommandName());
                            /*Intent intent = new Intent(MyApp.getContext(), LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            MyApp.getContext().startActivity(intent);*/
                            break;
                        case ResultException.SERVICE_ERROR:
                            LogUtils.e(globleResponse.getErrorCode()+ "  " + CommandName + "  服务器出错");
                            break;
                        case ResultException.COMMANDNAME_ERROR:
                            LogUtils.e(globleResponse.getErrorCode()+ "  " + CommandName + "  请求命令参数错误");
                            break;
                    }

                    throw new ResultException(globleResponse.getErrorCode(), globleResponse.getErrorMessage());
                }
            } catch (JSONException e) {
                e.printStackTrace();
                throw new ResultException(ResultException.UNKNOWNERROR, "数据解析出错");
            }
        }
        return response;
    }


}
