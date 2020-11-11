package com.hkycu.goodsseckill.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 秒杀限流 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AccessLimit {
    int seconds();                          // 时间
    int maxCount();                         // 次数
    boolean needLogin() default true;       // 默认需要登录
}