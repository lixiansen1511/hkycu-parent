package com.hkycu.goodsseckill.service.Impl;

import com.hkycu.goodsseckill.dao.SeckillOrderInfoDao;
import com.hkycu.goodsseckill.model.SeckillOrder;
import com.hkycu.goodsseckill.model.SeckillOrderInfo;
import com.hkycu.goodsseckill.service.SeckillOrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SeckillOrderInfoServiceImpl implements SeckillOrderInfoService {
    @Autowired
    private SeckillOrderInfoDao seckillOrderInfoDao;
    @Override
    public  List<SeckillOrderInfo> queryOrderList() {
        return seckillOrderInfoDao.querySeckillOrderInfoList();
    }

    @Override
    public SeckillOrderInfo selectSeckillOrderInfoById(long id) {
        return seckillOrderInfoDao.selectByPrimaryKey(id);
    }
}
