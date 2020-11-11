package com.hkycu.goods.service.impl;

import com.hkycu.goods.dao.TearDownDetailDao;
import com.hkycu.goods.model.OmsOrderItem;
import com.hkycu.goods.model.TearDownDetail;
import com.hkycu.goods.service.TearDownDetailService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TearDownDetailServiceImpl implements TearDownDetailService{

    @Autowired
    private TearDownDetailDao tearDownDetailDao;

    @Override
    public int insertTearDownDetail(TearDownDetail tearDownDetail) {
        return tearDownDetailDao.insertSelective(tearDownDetail);
    }

    @Override
    public int batchInsertOrderItem(@Param("list") List<TearDownDetail> tearDownDetails) {
        return tearDownDetailDao.batchInsertTearDownDetail(tearDownDetails);
    }
}
