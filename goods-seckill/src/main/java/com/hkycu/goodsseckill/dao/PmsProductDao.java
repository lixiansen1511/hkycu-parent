package com.hkycu.goodsseckill.dao;

import com.hkycu.goodsseckill.model.PmsProduct;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PmsProductDao {
    int deleteByPrimaryKey(Long id);

    int insert(PmsProduct record);

    int insertSelective(PmsProduct record);

    PmsProduct selectByPrimaryKey(Long id);

    List<PmsProduct> querySeckillProduct();

    int updateByPrimaryKeySelective(PmsProduct record);

    int updateByPrimaryKey(PmsProduct record);
}