package com.reny.git.rart.core;

import com.reny.git.mvvmlib.http.BaseServiceFactory;
import com.reny.git.rart.BuildConfig;
import com.reny.git.rart.api.APIConfig;

import java.net.Proxy;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by reny on 2017/2/9.
 */

public class ServiceFactory<S> extends BaseServiceFactory<S> {

    private final long DEFAULT_TIMEOUT = 60;//超时时间 60秒

    private static class SingletonHolder {
        private static final ServiceFactory INSTANCE = new ServiceFactory();
    }

    public static ServiceFactory getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    protected OkHttpClient getOkHttpClient() {
        //定制OkHttp
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        //设置超时时间
        clientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        clientBuilder.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        clientBuilder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        clientBuilder.retryOnConnectionFailure(true);
        if(!BuildConfig.DEBUG) {
            clientBuilder.proxy(Proxy.NO_PROXY);//防止代理抓包
        }

        clientBuilder.hostnameVerifier((hostname, session) -> true);
        /*clientBuilder.cookieJar(SingletonUtils.cookieJar);
        File cacheFile = new File(SingletonUtils.cacheDir, "HttpCache"); // 指定缓存路径
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); // 指定缓存大小100Mb
        clientBuilder.cache(cache);

        InputStream keyStore = MyApp.getContext().getResources().openRawResource(BuildConfig.DEBUG ? R.raw.zyctdw_debug : R.raw.zyctdw);
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, keyStore, "zyctdw");
        clientBuilder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
        clientBuilder.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);*/

        //clientBuilder.addNetworkInterceptor(new MyNetInterceptor());

        return clientBuilder.build();
    }

    @Override
    public String getDefaultBaseUrl() {
        //默认BaseUrl
        return APIConfig.BASE_URL;
    }
}
