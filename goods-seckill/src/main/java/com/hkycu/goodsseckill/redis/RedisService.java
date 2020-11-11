package com.hkycu.goodsseckill.redis;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.security.Key;

/** redis提供的增删改查服务 */
@Service
public class RedisService {
    @Autowired
    private JedisPool jedisPool;

    /** 获取单个对象 */
    public <T> T get(KeyPrefix prefix, String key, Class<T> clazz) {
        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource(); // 获取redis的连接资源
            // 生成真正的key
            String realKey = prefix.getPrefix() + key;
            String valueStr = jedis.get(realKey);
            T t = stringToBean(valueStr, clazz);
            return t;
        } finally { // 关闭连接
            returnToPool(jedis);
        }
    }

    /** 设置对象
     * @param prefix key的前缀
     * @param key 通用的key
     * @param value 类型
     * @param expireTime 缓存的有效时间
     * @return 布尔值 */
    public <T> boolean set(KeyPrefix prefix, String key, T value, int expireTime) {
        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource();
            String valueStr = beanToString(value);

            if(valueStr == null || valueStr.length() <= 0) {
                return false;
            }

            // 生成真正的key
            String realKey = prefix.getPrefix() + key;// "SeckillKey":"mp"+"" + user.getId() + "_" + goodsId
            String statusdoRelay = "";
            if(expireTime == 0) { // 没有过期时间，直接保存
                System.out.println("==valueStr==="+valueStr);
                statusdoRelay = jedis.set(realKey, valueStr);

            }else { // 存在有效期，设置过期时间
                System.out.println("== valueStr2=="+valueStr);
                statusdoRelay = jedis.setex(realKey, expireTime, valueStr);
            }

            return true;
        } finally {
            returnToPool(jedis);
        }
    }

    /** 根据key删除 */
    public Long del(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        Long result = null;

        try{
            jedis = jedisPool.getResource();
            result = jedis.del(prefix.getPrefix() + key);
            return result;
        } finally {
            returnToPool(jedis);
        }
    }

    /** 减少值 */
    public <T> Long decr(KeyPrefix prefix, String key) {
        Jedis jedis = null;

        try{
            jedis = jedisPool.getResource();
            // 生成真正的key
            String realKey = prefix.getPrefix() + key;
            return jedis.decr(realKey);//value减1
        }finally {
            returnToPool(jedis);
        }
    }

    /** 增加值 */
    public <T> Long incr(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            // 生成真正的key
            String realKey = prefix.getPrefix() + key;
            return jedis.incr(realKey);
        }finally {
            returnToPool(jedis);
        }
    }

    /** 可以判断该缓存是否过期 */
    public Long expice(KeyPrefix prefix, String key, int expireTime) {
        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource();
            return jedis.expire(prefix.getPrefix() + key, expireTime);
        }finally {
            returnToPool(jedis);
        }
    }

    /** 通过key判断是否存在 */
    public boolean exists(KeyPrefix prefix, String key) {
        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource();
            // 生成真正的key
            String realKey = prefix.getPrefix() + key ;
            return jedis.exists(realKey);
        }finally {
            returnToPool(jedis);
        }
    }

    /** 将对象转换成字符串 */
    private static <T> String beanToString(T value) {
        if(value == null) {
            return null;
        }
        Class<?> clazz = value.getClass();
        if(clazz == int.class || clazz == Integer.class) {
            return "" + value;
        }else if(clazz == String.class) {
            return (String)value;
        }else if(clazz == long.class || clazz == Long.class) {
            return "" + value;
        }else {
            return JSON.toJSONString(value);
        }
    }

    /** 关闭连接 */
    private void returnToPool(Jedis jedis) {
        if(jedis != null) {
            jedis.close();
        }
    }

    /** 将字符串转换成对象 */
    private static <T> T stringToBean(String data, Class<T> clazz) {
        if(data == null || data.length() <= 0 || clazz == null) {
            return null;
        }
        if(clazz == int.class || clazz == Integer.class) {
            return (T) Integer.valueOf(data);
        }else if(clazz == String.class) {
            return (T) data;
        }else if(clazz == long.class || clazz == Long.class) {
            return (T) Long.valueOf(data);
        }else {
            return JSON.toJavaObject(JSON.parseObject(data), clazz);
        }
    }
}