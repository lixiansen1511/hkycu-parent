package com.hkycu.goodsseckill.dao;

import com.hkycu.goodsseckill.model.SeckillGoods;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SeckillGoodsDao {
    int deleteByPrimaryKey(Long id);

    int insert(SeckillGoods record);

    int insertSelective(SeckillGoods record);

    SeckillGoods selectByPrimaryKey(Long id);

    List<SeckillGoods> selectBySeckillTime();

    SeckillGoods selectBySeckillProductId(@Param("id") Long id);

    int updateByPrimaryKeySelective(SeckillGoods record);

    int updateByPrimaryKey(SeckillGoods record);

    int updateStock(long id);
}