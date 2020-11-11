package com.hkycu.goods.dao;

import com.hkycu.goods.model.OmsOrder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OmsOrderDao {
    int deleteByPrimaryKey(Long id);

    int insert(OmsOrder record);

    int insertSelective(OmsOrder record);

    OmsOrder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OmsOrder record);

    int updateByPrimaryKey(OmsOrder record);

    List<OmsOrder> selectByMember(Long memberId);
}