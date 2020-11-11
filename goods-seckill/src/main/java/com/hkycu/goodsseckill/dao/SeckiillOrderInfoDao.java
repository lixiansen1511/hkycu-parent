package com.hkycu.goodsseckill.dao;

import com.hkycu.goodsseckill.model.SeckiillOrderInfo;

public interface SeckiillOrderInfoDao {
    int deleteByPrimaryKey(Long id);

    int insert(SeckiillOrderInfo record);

    int insertSelective(SeckiillOrderInfo record);

    SeckiillOrderInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SeckiillOrderInfo record);

    int updateByPrimaryKey(SeckiillOrderInfo record);
}