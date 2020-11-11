package com.hkycu.goodsseckill.web;

import com.hkycu.goodsseckill.dao.SeckillOrderDao;
import com.hkycu.goodsseckill.dao.SeckillOrderInfoDao;
import com.hkycu.goodsseckill.model.*;
import com.hkycu.goodsseckill.result.CodeMsg;
import com.hkycu.goodsseckill.result.Result;
import com.hkycu.goodsseckill.service.ProductSeckillService;
import com.hkycu.goodsseckill.service.SeckillOrderInfoService;
import com.hkycu.goodsseckill.service.SeckillOrderService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/order")
public class SeckillOrderController {

    @Autowired
    private SeckillOrderInfoService seckillOrderInfoService;

    @Autowired
    private ProductSeckillService productSeckillService;

    @RequestMapping("/list")
    @ResponseBody
    public Result<List<SeckillGoodsVo>> queryOrderList(HttpServletRequest request){
      /*  UmsMember user = (UmsMember)request.getSession().getAttribute("user");
        if (ObjectUtils.isEmpty(user)){
            return new Result<>(CodeMsg.USER_NO_LOGIN);
        }*/
        List<SeckillGoodsVo> seckillGoodsVos = new ArrayList<>();
        List<SeckillOrderInfo> seckillOrdersInfos = seckillOrderInfoService.queryOrderList();
        for ( SeckillOrderInfo seckillOrderInfo:seckillOrdersInfos) {
            SeckillGoodsVo seckillGoodsVo = new SeckillGoodsVo();
            seckillGoodsVo.setSeckillOrderInfo(seckillOrderInfo);
            SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            seckillGoodsVo.setCreateDateStr(format.format(seckillOrderInfo.getCreateDate()));
            seckillGoodsVos.add(seckillGoodsVo);
        }

       Result<List<SeckillGoodsVo>> result = new Result<>(CodeMsg.SUCESS);
       result.setData(seckillGoodsVos);
       return result;
    }

    @RequestMapping("/detail")
    @ResponseBody
    public Result<SeckillGoodsVo> selectSeckillDetail(@RequestParam("orderId") long orderId,HttpServletRequest request){
 /*       UmsMember user = (UmsMember)request.getSession().getAttribute("user");
        if (ObjectUtils.isEmpty(user)){
            return new Result<>(CodeMsg.USER_NO_LOGIN);
        }*/


        SeckillOrderInfo seckillOrderInfo = seckillOrderInfoService.selectSeckillOrderInfoById(orderId);
        SeckillGoodsVo seckillGoodsVo = new SeckillGoodsVo();
        seckillGoodsVo.setSeckillOrderInfo(seckillOrderInfo);
        SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        seckillGoodsVo.setCreateDateStr(format.format(seckillOrderInfo.getCreateDate()));


        PmsProduct pmsProduct = productSeckillService.selectSeckillDetailById(seckillOrderInfo.getGoodsId());
        if (ObjectUtils.isEmpty(pmsProduct)){
            return new Result<>(CodeMsg.GOODS_ERROR);
    }
        seckillGoodsVo.setPmsProduct(pmsProduct);

        Result<SeckillGoodsVo> result = new Result<>(CodeMsg.SUCESS);
        result.setData(seckillGoodsVo);
        return result;
    }
}
