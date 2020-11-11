package com.hkycu.goods.service.impl;

import com.hkycu.goods.dao.OmsOrderDao;
import com.hkycu.goods.model.OmsOrder;
import com.hkycu.goods.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OmsOrderDao omsOrderDao;
    @Override
    public OmsOrder queryOrderById(Long id) {
        return omsOrderDao.selectByPrimaryKey(id);
    }

    @Override
    public List<OmsOrder> queryOrderListByUid(Long memberId) {
        return omsOrderDao.selectByMember(memberId);
    }

    @Override
    public int insertOrder(OmsOrder omsOrder) {
        return omsOrderDao.insertSelective(omsOrder);
    }
}
