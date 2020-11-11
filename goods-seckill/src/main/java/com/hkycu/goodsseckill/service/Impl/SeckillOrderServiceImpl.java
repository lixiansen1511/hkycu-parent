package com.hkycu.goodsseckill.service.Impl;

import com.hkycu.goodsseckill.Const;
import com.hkycu.goodsseckill.dao.SeckillGoodsDao;
import com.hkycu.goodsseckill.dao.SeckillOrderDao;
import com.hkycu.goodsseckill.dao.SeckillOrderInfoDao;
import com.hkycu.goodsseckill.model.SeckillGoods;
import com.hkycu.goodsseckill.model.SeckillOrder;
import com.hkycu.goodsseckill.model.SeckillOrderInfo;
import com.hkycu.goodsseckill.model.UmsMember;
import com.hkycu.goodsseckill.redis.RedisService;
import com.hkycu.goodsseckill.redis.SeckillKey;
import com.hkycu.goodsseckill.service.SeckillOrderService;
import com.hkycu.goodsseckill.util.MD5Util;
import com.hkycu.goodsseckill.util.OrderUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Date;
import java.util.UUID;

@Service
public class SeckillOrderServiceImpl implements SeckillOrderService {

    @Autowired
    private RedisService redisService;

    @Autowired
    private SeckillOrderDao seckillOrderDao;

    @Autowired
    private SeckillGoodsDao seckillGoodsDao;

    @Autowired
    private SeckillOrderInfoDao seckillOrderInfoDao;

    @Override
    public String createSeckillPath(UmsMember user, long goodsId) {
        if(ObjectUtils.isEmpty(user) || goodsId <= 0) {
            return null;
        }
        String pathStr = MD5Util.md5(UUID.randomUUID() + "123456");
        this.redisService.set(SeckillKey.getSeckillPath,//"mp"
                "" + user.getId() + "_" + goodsId,
                pathStr,
                Const.RedisCacheExpireTime.GOODS_ID);

        return pathStr;
    }

    @Override
    public boolean checkPath(UmsMember user, long goodsId, String path) {
        if (ObjectUtils.isEmpty(user) || StringUtils.isEmpty(path)) {
            return false;
        }
        String pathRedis = this.redisService.get(SeckillKey.getSeckillPath,""+user.getId()+"_"+goodsId,String.class);
        return path.equals(pathRedis);
    }

    @Override
    public SeckillOrder getSeckillOrderByUserIdAndGoodsId(long userId, long goodsId) {
        return seckillOrderDao.selectByUserIdAndGoodsId(userId,goodsId);
    }

    @Transactional
    @Override
    public SeckillOrderInfo saveOrder(UmsMember user, SeckillGoods goods) {
        int success = this.seckillGoodsDao.updateStock(goods.getId());
        if(success == 1){
            SeckillOrderInfo seckillOrderInfo = new SeckillOrderInfo();
            seckillOrderInfo.setCreateDate(new Date());
            seckillOrderInfo.setAddrId(0L);
            seckillOrderInfo.setGoodsCount(1);
            seckillOrderInfo.setGoodsId(goods.getId());
            seckillOrderInfo.setGoodsName(goods.getPmsProduct().getName());
            seckillOrderInfo.setGoodsPrice(goods.getSeckillPrice());
            seckillOrderInfo.setOrderChannel(1);
            seckillOrderInfo.setStatus(0);
            seckillOrderInfo.setMemberId(user.getId());
            this.seckillOrderInfoDao.insertSelective(seckillOrderInfo);

            SeckillOrder seckillOrder = new SeckillOrder();
            seckillOrder.setGoodsId(goods.getId());
            seckillOrder.setOrderId(seckillOrderInfo.getId());
            seckillOrder.setMemberId(user.getId());

            this.seckillOrderDao.insertSelective(seckillOrder);

            return seckillOrderInfo;
        }else{
            setGoodsOver(goods.getId());
            return null;
        }
    }

    @Override
    public Long getSeckillResult(Long id, long goodsId) {
       SeckillOrder seckillOrder =  seckillOrderDao.selectByUserIdAndGoodsId(id,goodsId);
       if (!ObjectUtils.isEmpty(seckillOrder)){
           return seckillOrder.getOrderId();
       }else{
           //判断商品是否存在
          boolean isOver = getGoodsOver(goodsId);
          if(isOver){
              return -1L;
          }else {
              return 0L;
          }
       }
    }

    private boolean getGoodsOver(long goodsId) {
        return this.redisService.exists(SeckillKey.isGoodsOver,""+goodsId);
    }

    private void setGoodsOver(Long goodsId) {
        this.redisService.set(SeckillKey.isGoodsOver,""+ goodsId,true,Const.RedisCacheExpireTime.GOODS_ID);
    }


}