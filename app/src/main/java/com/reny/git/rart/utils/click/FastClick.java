package com.reny.git.rart.utils.click;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/***
 * 因为AOP编程 实现了重复点击问题  如果有需要重复点击的情况 可以如下使用(添加@FastClick注解)
 *  XXX.setOnClickListener(new View.OnClickListener() {
 *             @FastClick
 *             @Override
 *             public void onClick(View v) {
 *                 //允许连续点击
 *             }
 *         });
 */
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD})
public @interface FastClick {

}
