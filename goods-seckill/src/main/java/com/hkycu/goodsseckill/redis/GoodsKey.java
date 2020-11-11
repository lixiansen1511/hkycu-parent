package com.hkycu.goodsseckill.redis;

public class GoodsKey extends BasePrefix {
    public GoodsKey(String prefix) {
        super(prefix);
    }

    public static GoodsKey getGoodsList = new GoodsKey("gl");
    public static GoodsKey getGetGoodsDetail = new GoodsKey("gd");
    public static GoodsKey getSeckillGoodsStock = new GoodsKey("gs");
}
