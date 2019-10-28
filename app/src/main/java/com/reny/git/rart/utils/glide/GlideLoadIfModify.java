package com.reny.git.rart.utils.glide;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

import com.bumptech.glide.signature.ObjectKey;
import com.reny.git.rart.MyApp;
import com.reny.git.rart.R;
import com.reny.git.rart.api.APIConfig;
import com.reny.git.rart.utils.SPUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by reny on 2019/9/26.
 *
 * -----  图片url不变的情况 如果修改了图片  需要更新缓存   -------
 *
 */
public class GlideLoadIfModify {

    private static OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(5L, TimeUnit.SECONDS)
            .build();

    private static Handler mainHandler = new Handler(Looper.getMainLooper());



    public static void load(@NonNull ImageView imageView, String url, @DrawableRes int...imgIds) {
        String lastModified = SPUtils.init(R.string.GlideLoadIfModify).getString(url, "");
        Drawable[] placeholders = GlideHelper.getPlaceholders(MyApp.getContext(), imgIds);
        try {
            request(imageView, url, lastModified, placeholders);
        }catch (Exception e){e.printStackTrace();}
    }

    private static void request(final ImageView imageView
            , final String url
            , final String lastModified
            , final Drawable[] placeholders) {



        Request request = new Request.Builder()
                .addHeader("Referer", APIConfig.Referer)
                .addHeader("If-Modified-Since", lastModified)
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mainHandler.post(() -> {
                    //已在主线程中，更新UI
                    if(null != imageView) {
                        try {
                            GlideApp.with(MyApp.getContext())
                                    .load(GlideHelper.buildGlideUrl(url))
                                    .signature(new ObjectKey(lastModified))
                                    .placeholder(placeholders[0]).error(placeholders[1]).fallback(placeholders[2])
                                    .into(imageView);
                        }catch (Exception ex){ex.printStackTrace();}
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                int code = response.code();
                ObjectKey signature = null;
                if (code == 304) {
                    // 说明图片没变
                    //LogUtils.e("GlideLoadIfModify 304");
                    signature = new ObjectKey(lastModified);
                } else if (code == 200) {
                    // 图片变了
                    final String newTime = response.header("Last-Modified");
                    SPUtils.init(R.string.GlideLoadIfModify).putString(url, newTime);
                    if (newTime != null) {
                        signature = new ObjectKey(newTime);
                    }
                }
                ObjectKey finalSignature = signature;
                mainHandler.post(() -> {
                    if(null != imageView) {
                        try {
                            GlideRequest<Drawable> load = GlideApp.with(MyApp.getContext()).load(GlideHelper.buildGlideUrl(url));
                            if (finalSignature != null) {
                                load.signature(finalSignature)
                                        .placeholder(placeholders[0]).error(placeholders[1]).fallback(placeholders[2])
                                        .into(imageView);
                            } else {
                                load.placeholder(placeholders[0]).error(placeholders[1]).fallback(placeholders[2])
                                        .into(imageView);
                            }
                        }catch (Exception ex){ex.printStackTrace();}
                    }
                });
            }
        });
    }


}
