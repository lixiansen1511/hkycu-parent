package com.hkycu.goodsseckill.service.Impl;

import com.hkycu.goodsseckill.dao.SeckillGoodsDao;
import com.hkycu.goodsseckill.model.SeckillGoods;
import com.hkycu.goodsseckill.service.SeckillGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SeckillGoodsServiceImpl implements SeckillGoodsService {
    @Autowired
    private SeckillGoodsDao seckillGoodsDao;
    @Override
    public List<SeckillGoods> querySeckillGoods() {
        return seckillGoodsDao.selectBySeckillTime();
    }

    @Override
    public SeckillGoods selectSeckillGoodsById(Long id) {
        return seckillGoodsDao.selectBySeckillProductId(id);
    }

    @Override
    public List<SeckillGoods> getSeckillGoodsList() {
        return seckillGoodsDao.selectBySeckillTime();
    }
}
