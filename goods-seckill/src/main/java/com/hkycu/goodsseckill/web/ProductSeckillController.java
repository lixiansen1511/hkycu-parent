package com.hkycu.goodsseckill.web;

import com.hkycu.goodsseckill.model.GoodsDetailVo;
import com.hkycu.goodsseckill.model.PmsProduct;
import com.hkycu.goodsseckill.model.SeckillGoods;
import com.hkycu.goodsseckill.model.UmsMember;
import com.hkycu.goodsseckill.redis.RedisService;
import com.hkycu.goodsseckill.redis.UserKey;
import com.hkycu.goodsseckill.result.CodeMsg;
import com.hkycu.goodsseckill.result.Result;
import com.hkycu.goodsseckill.service.ProductSeckillService;
import com.hkycu.goodsseckill.service.SeckillGoodsService;
import com.hkycu.goodsseckill.util.CookieUtil;
import com.sun.org.apache.bcel.internal.classfile.Code;
import io.lettuce.core.pubsub.RedisPubSubListener;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/goods")
public class ProductSeckillController {

    @Autowired
    private ProductSeckillService productSeckillService;

    @Autowired
    private SeckillGoodsService seckillGoodsService;

    @Autowired
    private RedisService redisService;


    @RequestMapping("/productSeckill")
    @ResponseBody
    public Result<List<SeckillGoods>> productSeckill(HttpServletRequest request){
       /* UmsMember user = (UmsMember)request.getSession().getAttribute("user");
        if(ObjectUtils.isEmpty(user)){
            return new Result<>(CodeMsg.USER_NO_LOGIN);
        }*/

        String loginToken = CookieUtil.readLoginToken(request);
        UmsMember user = this.redisService.get(UserKey.getByName, loginToken,
                UmsMember.class);
        if(ObjectUtils.isEmpty(user)){
            return new Result<>(CodeMsg.USER_NO_LOGIN);
        }

        List<SeckillGoods> seckillProducts = seckillGoodsService.querySeckillGoods();
        if(ObjectUtils.isEmpty(seckillProducts)){
            return  new Result<>(CodeMsg.Time_ERROR);
        }
        Result<List<SeckillGoods>> result = new Result<List<SeckillGoods>>(CodeMsg.SUCESS);
        result.setData(seckillProducts);
        return result;
    }

    @RequestMapping("/seckillProductDetail")
    @ResponseBody
    public Result<GoodsDetailVo> seckillProductDetail(Long id,HttpServletRequest request){
      /*  UmsMember user = (UmsMember)request.getSession().getAttribute("user");
        if(ObjectUtils.isEmpty(user)){
            return new Result<>(CodeMsg.USER_NO_LOGIN);
        }*/

        String loginToken = CookieUtil.readLoginToken(request);
        UmsMember user = this.redisService.get(UserKey.getByName, loginToken,
                UmsMember.class);
        if(ObjectUtils.isEmpty(user)){
            return new Result<>(CodeMsg.USER_NO_LOGIN);
        }

        SeckillGoods seckillGoods = seckillGoodsService.selectSeckillGoodsById(id);
        if (ObjectUtils.isEmpty(seckillGoods)){
            return new Result<>(CodeMsg.GOODS_ERROR);
        }


        GoodsDetailVo goodsDetailVo = new GoodsDetailVo();
        SimpleDateFormat format  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        goodsDetailVo.setStartDateStr(format.format(seckillGoods.getStartDate()));
        goodsDetailVo.setEndDateStr(format.format(seckillGoods.getEndDate()));
        goodsDetailVo.setSeckillGoods(seckillGoods);


        long seckillStartTime = seckillGoods.getStartDate().getTime();
        long seckillEndTime = seckillGoods.getEndDate().getTime();
        long date = new Date().getTime();
        if(seckillStartTime > date ){
            goodsDetailVo.setSeckillStatus(0);
            goodsDetailVo.setRemainSeconds(-1);
            return new Result<>(CodeMsg.SKILLNOTSTART);
        }else if(seckillStartTime < date && seckillEndTime > date){
            goodsDetailVo.setSeckillStatus(1);
            goodsDetailVo.setRemainSeconds((int)(seckillEndTime-date)/1000);
        }else {
            goodsDetailVo.setSeckillStatus(2);
            goodsDetailVo.setRemainSeconds(-1);
            return new Result<>(CodeMsg.SKILLFINISHED);
        }

        Result<GoodsDetailVo>  result = new Result<>(CodeMsg.SUCESS);
        result.setData(goodsDetailVo);
        return result;
    }


}
