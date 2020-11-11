package com.hkycu.goodsseckill.redis;

public class SeckillKey extends BasePrefix {

    public SeckillKey(String prefix) {
        super(prefix);
    }
    
    public static SeckillKey getSeckillPath = new SeckillKey("mp");
    public static SeckillKey isGoodsOver = new SeckillKey("go");
}