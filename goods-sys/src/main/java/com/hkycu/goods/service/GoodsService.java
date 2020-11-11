package com.hkycu.goods.service;

import com.hkycu.goods.model.PmsProduct;

import java.util.List;

public interface GoodsService {
    List<PmsProduct> queryList(PmsProduct goods) throws Exception;
    PmsProduct queryProductById(Long id);
    int addGoods(PmsProduct product);
    int deleteGoodsById(long id);
    int updateGoods(PmsProduct goods);
}
