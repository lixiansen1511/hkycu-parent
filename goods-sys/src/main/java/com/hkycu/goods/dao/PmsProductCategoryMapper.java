package com.hkycu.goods.dao;


import com.hkycu.goods.model.PmsProductCategory;
import org.springframework.stereotype.Repository;

@Repository
public interface PmsProductCategoryMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PmsProductCategory record);

    int insertSelective(PmsProductCategory record);

    PmsProductCategory selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PmsProductCategory record);

    int updateByPrimaryKey(PmsProductCategory record);
}