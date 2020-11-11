package com.hkycu.goods.service.impl;

import com.hkycu.goods.dao.PmsProductMapper;
import com.hkycu.goods.model.PmsProduct;
import com.hkycu.goods.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private PmsProductMapper goodsDao;

    @Override
    public List<PmsProduct> queryList(PmsProduct goods) throws Exception {
        return this.goodsDao.queryList(goods);
    }

    @Override
    public PmsProduct queryProductById(Long id) {
        return this.goodsDao.selectByPrimaryKey(id);
    }

    @Override
    public int addGoods(PmsProduct product) {
        return goodsDao.insertSelective(product);
    }

    @Override
    public int deleteGoodsById(long id) {
        return goodsDao.deleteByPrimaryKey(id);
    }

    @Override
    public int updateGoods(PmsProduct goods) {
        return goodsDao.updateByPrimaryKeySelective(goods);
    }
}
