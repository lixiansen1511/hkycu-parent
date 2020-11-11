package com.hkycu.goodsseckill.dao;

import com.hkycu.goodsseckill.model.SeckillOrder;
import com.hkycu.goodsseckill.model.SeckillOrderInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeckillOrderInfoDao {
    int deleteByPrimaryKey(Long id);

    int insert(SeckillOrderInfo record);

    int insertSelective(SeckillOrderInfo record);

    SeckillOrderInfo selectByPrimaryKey(Long id);

    List<SeckillOrderInfo> querySeckillOrderInfoList();

    int updateByPrimaryKeySelective(SeckillOrderInfo record);

    int updateByPrimaryKey(SeckillOrderInfo record);

}