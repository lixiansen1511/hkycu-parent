package com.hkycu.goods.service;

import com.hkycu.goods.model.OmsOrder;

import java.util.List;

public interface OrderService {
    OmsOrder queryOrderById(Long id);
    List<OmsOrder> queryOrderListByUid(Long memberId);
    int insertOrder(OmsOrder omsOrder);

}
