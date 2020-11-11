package com.hkycu.goodsseckill.service;

import com.hkycu.goodsseckill.model.*;

import java.util.List;

public interface SeckillOrderService {
    /** 创建一个动态路径字符串 */
    String createSeckillPath(UmsMember user, long goodsId);

    /*校验path*/
    boolean checkPath(UmsMember user,long goodsId,String path);

    SeckillOrder getSeckillOrderByUserIdAndGoodsId(long userId,long goodsId);


    SeckillOrderInfo saveOrder(UmsMember user, SeckillGoods goods);

    Long getSeckillResult(Long id, long goodsId);

}