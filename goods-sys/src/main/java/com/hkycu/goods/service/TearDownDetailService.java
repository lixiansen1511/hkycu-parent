package com.hkycu.goods.service;

import com.hkycu.goods.model.OmsOrderItem;
import com.hkycu.goods.model.TearDownDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TearDownDetailService {
    int insertTearDownDetail(TearDownDetail tearDownDetail);
    int batchInsertOrderItem(@Param("list") List<TearDownDetail> tearDownDetails);
}
