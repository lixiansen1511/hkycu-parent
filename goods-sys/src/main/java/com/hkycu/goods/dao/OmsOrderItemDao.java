package com.hkycu.goods.dao;

import com.hkycu.goods.model.OmsOrderItem;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OmsOrderItemDao {
    int deleteByPrimaryKey(Long id);

    int insert(OmsOrderItem record);

    int insertSelective(OmsOrderItem record);

    OmsOrderItem selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OmsOrderItem record);

    int updateByPrimaryKey(OmsOrderItem record);

    List<OmsOrderItem> selectItemByOrderId(Long orderId);

    List<OmsOrderItem> selectBrandAndPrice(Long memberId);

    /** 批量添加订单详情 */
    int batchInsertOrderItem(@Param("list") List<OmsOrderItem> omsOrderItems);
}