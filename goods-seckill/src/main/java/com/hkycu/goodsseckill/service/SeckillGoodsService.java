package com.hkycu.goodsseckill.service;

import com.hkycu.goodsseckill.model.SeckillGoods;

import java.util.List;

public interface SeckillGoodsService {

    List<SeckillGoods> querySeckillGoods();

    SeckillGoods selectSeckillGoodsById(Long id);

    List<SeckillGoods> getSeckillGoodsList();
}
