package com.hkycu.goodsseckill.service;

import com.hkycu.goodsseckill.model.GoodsDetailVo;
import com.hkycu.goodsseckill.model.SeckillOrder;
import com.hkycu.goodsseckill.model.SeckillOrderInfo;
import com.hkycu.goodsseckill.model.UmsMember;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface SeckillOrderInfoService {

    List<SeckillOrderInfo> queryOrderList();

    SeckillOrderInfo selectSeckillOrderInfoById(long id);

}
