package com.reny.git.rart;

import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.reny.git.mvvmlib.utils.AppUtils;
import com.reny.git.mvvmlib.utils.LogUtils;
import com.reny.git.mvvmlib.view.MultiStateConfig;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;

import me.jessyan.autosize.AutoSizeConfig;
import me.jessyan.autosize.unit.Subunits;

/**
 * Created by reny on 2019/10/24.
 */
public class MyApp extends Application {

    public static MyApp instance;
    public static Context getContext(){
        return instance.getApplicationContext();
    }

    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) -> {
            layout.setHeaderInsetStart(-8);
            return new MaterialHeader(context).setColorSchemeColors(0xff000000);
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator((context, layout) -> new ClassicsFooter(context));

        MultiStateConfig.getInstance().setConfig(
                new MultiStateConfig.Build()
                        .setTipEmpty("没有相关数据，点击重试")
                        .setTipError("加载失败，点击重试")
                        .setTipNoNetwork("没有网络，点击重试")
                        .setIndicatorName("BallSpinFadeLoaderIndicator")
                        .setIndicatorColor(R.color.theme)
                        .setImgError(R.mipmap.ic_err_mz)
                        .setImgEmpty(R.mipmap.ic_err_mz)
                        .setImgNoNetwork(R.mipmap.ic_err_mz)
        );
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        if (BuildConfig.DEBUG) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this); // 尽可能早，推荐在Application中初始化

        //不适配dp sp 启用mm单位 来适配屏幕 防止第三方控件用dp sp 造成影响
        AutoSizeConfig.getInstance().getUnitsManager()
                .setSupportDP(false)
                .setSupportSP(false)
                .setSupportSubunits(Subunits.MM);

        LogUtils.init(BuildConfig.DEBUG);
        //AppUtils.self().setApplication(this);

        //激活状态（Started）可以实时收到消息，非激活状态（Stoped）无法实时收到消息，需等到Activity重新变成激活状态，方可收到消息
        LiveEventBus.config().lifecycleObserverAlwaysActive(false);
    }
}
