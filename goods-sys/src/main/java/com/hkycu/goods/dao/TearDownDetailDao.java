package com.hkycu.goods.dao;

import com.hkycu.goods.model.OmsOrderItem;
import com.hkycu.goods.model.TearDownDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TearDownDetailDao {
    int deleteByPrimaryKey(Long id);

    int insert(TearDownDetail record);

    int insertSelective(TearDownDetail record);

    TearDownDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TearDownDetail record);

    int updateByPrimaryKey(TearDownDetail record);

    int batchInsertTearDownDetail(@Param("list") List<TearDownDetail> tearDownDetails);
}