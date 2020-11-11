package com.hkycu.goodsseckill;

/** 常量类 */
public class Const {

    public interface RedisCacheExpireTime {
        int REDIS_SESSION_EXPIRETIME = 60 * 30 ;    // 30 分钟
        int GOODS_LIST = 60 * 30 * 12;              // 12 小时
        int GOODS_ID = 60*10;                          // 1 分钟
    }
}