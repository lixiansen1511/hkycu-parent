package com.hkycu.goods.service;

import com.hkycu.goods.model.OmsOrderItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderItemService {
    int insertOrderItem(OmsOrderItem omsOrderItem);
    List<OmsOrderItem> selectItemByOrderId(Long orderId);
    List<OmsOrderItem> selectOrderItemByUid(Long memberId);
    int batchInsertOrderItem(@Param("list") List<OmsOrderItem> omsOrderItems);
}
