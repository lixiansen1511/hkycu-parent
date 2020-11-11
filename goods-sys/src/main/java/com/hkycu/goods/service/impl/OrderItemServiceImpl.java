package com.hkycu.goods.service.impl;

import com.hkycu.goods.dao.OmsOrderItemDao;
import com.hkycu.goods.model.OmsOrderItem;
import com.hkycu.goods.service.OrderItemService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OmsOrderItemDao orderItemDao;

    @Override
    public int insertOrderItem(OmsOrderItem omsOrderItem) {
        return orderItemDao.insertSelective(omsOrderItem);
    }

    @Override
    public List<OmsOrderItem> selectItemByOrderId(Long orderId) {
        return orderItemDao.selectItemByOrderId(orderId);
    }

    @Override
    public List<OmsOrderItem> selectOrderItemByUid(Long memberId) {
        return orderItemDao.selectBrandAndPrice(memberId);
    }

    @Override
    public int batchInsertOrderItem(@Param("list") List<OmsOrderItem> omsOrderItems) {
        return orderItemDao.batchInsertOrderItem(omsOrderItems);
    }
}
