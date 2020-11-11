package com.hkycu.goods.elastic.service.Impl;

import com.hkycu.goods.elastic.domain.Goods;
import com.hkycu.goods.elastic.mapper.GoodsMapper;
import com.hkycu.goods.elastic.service.GoodsServiceDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class GoodsServiceDBImpl implements GoodsServiceDB{
    @Resource
    private GoodsMapper goodsMapper;
    @Override
    public Goods getGoodsById(Long goodsId) {
        return goodsMapper.getGoodsById(goodsId);
    }

    @Override
    public List<Goods> getGoodsList() {
        return goodsMapper.getGoodsList();
    }
}
