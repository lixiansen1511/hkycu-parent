package com.hkycu.goods.elastic.service;

import com.hkycu.goods.elastic.domain.Goods;

import java.util.List;

public interface GoodsServiceDB {
    /** 根据商品ID查询商品 */
    Goods getGoodsById(Long goodsId);

    /** 查询所有的商品 */
    List<Goods> getGoodsList();
}