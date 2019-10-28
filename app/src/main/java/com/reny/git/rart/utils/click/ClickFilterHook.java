package com.reny.git.rart.utils.click;

import com.reny.git.mvvmlib.utils.LogUtils;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class ClickFilterHook {

    private boolean supportFastClick = false;

    @Before("execution(@com.reny.git.rart.utils.click.FastClick  * *(..))")
    public void fastClickFilterHook(JoinPoint joinPoint){
        supportFastClick = true;
    }

    @Around("execution(* android.view.View.OnClickListener.onClick(..))")
    public void clickFilterHook(ProceedingJoinPoint joinPoint) {
        if (!FastClickCheck.isFastClick() || supportFastClick) {
            try {
                joinPoint.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        } else {
            LogUtils.e("ClickFilterHook：重复点击,已过滤");
        }
    }
}
