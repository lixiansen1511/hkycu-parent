package com.hkycu.goods.dao;

import com.hkycu.goods.model.PmsProduct;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PmsProductMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PmsProduct record);

    int insertSelective(PmsProduct record);

    PmsProduct selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PmsProduct record);

    int updateByPrimaryKey(PmsProduct record);

    List<PmsProduct> queryList(PmsProduct goods);
}