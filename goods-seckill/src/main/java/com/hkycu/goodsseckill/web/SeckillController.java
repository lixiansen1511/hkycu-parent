package com.hkycu.goodsseckill.web;


import com.hkycu.goodsseckill.Const;
import com.hkycu.goodsseckill.annotations.AccessLimit;
import com.hkycu.goodsseckill.dao.PmsProductDao;
import com.hkycu.goodsseckill.model.*;
import com.hkycu.goodsseckill.redis.GoodsKey;
import com.hkycu.goodsseckill.redis.RedisService;
import com.hkycu.goodsseckill.redis.UserKey;
import com.hkycu.goodsseckill.result.CodeMsg;
import com.hkycu.goodsseckill.result.Result;
import com.hkycu.goodsseckill.service.SeckillGoodsService;
import com.hkycu.goodsseckill.service.SeckillOrderService;
import com.hkycu.goodsseckill.util.CookieUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/seckill")
public class SeckillController implements InitializingBean{

    @Autowired
    private SeckillOrderService seckillOrderService;

    private Map<Long,Boolean> localOverMap = new HashMap<>();

    @Autowired
    private SeckillGoodsService seckillGoodsService;



    @Autowired
    private RedisService redisService;


    @Override
    public void afterPropertiesSet() throws Exception {
      List<SeckillGoods> goodsList =  this.seckillGoodsService.getSeckillGoodsList();
      if(ObjectUtils.isEmpty(goodsList)){
          return;
      }
      for (SeckillGoods goods:goodsList){
          //初始化商品至redis，stock作为value存入redis
          this.redisService.set(GoodsKey.getSeckillGoodsStock,""+goods.getId(),
                  goods.getStockCount(), Const.RedisCacheExpireTime.GOODS_LIST);
          localOverMap.put(goods.getId(),false);//标记商品
      }
    }

    /** 生成随机路径，用于隐藏秒杀路径 */
    @AccessLimit(seconds = 5, maxCount = 5, needLogin = true)
    @GetMapping("/path")
    @ResponseBody
    public Result<String> getSeckillPath(@RequestParam("goodsId") long goodsId,
                                         HttpServletRequest request) {
      /*  UmsMember user = (UmsMember) request.getSession().getAttribute("user");
        if(ObjectUtils.isEmpty(user)) {
            return Result.error(CodeMsg.USER_NO_LOGIN);
        }*/

        String loginToken = CookieUtil.readLoginToken(request);
        UmsMember user = this.redisService.get(UserKey.getByName, loginToken,
                UmsMember.class);

        //uuid作为path，将key为userId,goodsId,value为path存入redis
        String path = this.seckillOrderService.createSeckillPath(user, goodsId);
        return Result.success(path);
    }

    @PostMapping("/{path}/seckill")
    @ResponseBody
    public Result<Integer> list(Model model,
                                @RequestParam("goodsId") long goodsId,
                                @PathVariable("path")String path,
                                HttpServletRequest request){
      /*  UmsMember user = (UmsMember)request.getSession().getAttribute("user");
        if (ObjectUtils.isEmpty(user)){
            return Result.error(CodeMsg.USER_NO_LOGIN);
        }*/

       //判断用户是否登录
        String loginToken = CookieUtil.readLoginToken(request);
        UmsMember user = this.redisService.get(UserKey.getByName, loginToken,
                UmsMember.class);

        //检查路径
        boolean check = this.seckillOrderService.checkPath(user,goodsId,path);
        if(!check){
            return Result.error(CodeMsg.REQUEST_ILLEGAL);
        }

        //秒杀超时
        boolean over = localOverMap.get(goodsId);
        if (over){
            return Result.error(CodeMsg.SECKILL_OVER);
        }
        //标记减少redis访问(stock-1)
       long stock = this.redisService.decr(GoodsKey.getSeckillGoodsStock,""+goodsId);
        if (stock<0){
            localOverMap.put(goodsId,true);
            return Result.error(CodeMsg.SECKILL_OVER);
        }

        //判断是否秒杀到了
        SeckillOrder order = this.seckillOrderService.getSeckillOrderByUserIdAndGoodsId(user.getId(),goodsId);
        if(!ObjectUtils.isEmpty(order)){
            return Result.error(CodeMsg.REPEATE_SECKILL);
        }
        //还没有下单，此时需要减少库存量
        SeckillGoods goodsBo = this.seckillGoodsService.selectSeckillGoodsById(goodsId);
        //保存订单及订单详情
        SeckillOrderInfo orderInfo = this.seckillOrderService.saveOrder(user,goodsBo);
        return Result.success(0);

    }

    @RequestMapping("/result")
    @ResponseBody
    public Result<Long> seckillResult(@RequestParam("goodsId") long goodsId,HttpServletRequest request){
      /*  UmsMember user = (UmsMember)request.getSession().getAttribute("user");
        if(ObjectUtils.isEmpty(user)){
            return  Result.error(CodeMsg.USER_NO_LOGIN);
        }*/

        String loginToken = CookieUtil.readLoginToken(request);
        UmsMember user = this.redisService.get(UserKey.getByName, loginToken,
                UmsMember.class);

        Long result = this.seckillOrderService.getSeckillResult(user.getId(),goodsId);
        return Result.success(result);

    }

}