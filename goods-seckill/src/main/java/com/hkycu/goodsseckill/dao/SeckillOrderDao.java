package com.hkycu.goodsseckill.dao;

import com.hkycu.goodsseckill.model.SeckillOrder;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeckillOrderDao {
    int deleteByPrimaryKey(Long id);

    int insert(SeckillOrder record);

    int insertSelective(SeckillOrder record);

    SeckillOrder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SeckillOrder record);

    int updateByPrimaryKey(SeckillOrder record);

    SeckillOrder selectByUserIdAndGoodsId(@Param("userId") long userId, @Param("goodsId") long goodsId);

    List<SeckillOrder> queryOrderList();
}