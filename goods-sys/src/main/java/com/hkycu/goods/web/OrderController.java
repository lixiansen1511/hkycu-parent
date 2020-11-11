package com.hkycu.goods.web;


import com.hkycu.goods.model.*;
import com.hkycu.goods.result.CodeMsg;
import com.hkycu.goods.result.Result;
import com.hkycu.goods.service.OrderItemService;
import com.hkycu.goods.service.OrderService;
import com.hkycu.goods.service.TearDownDetailService;
import com.hkycu.goods.util.CookieUtil;
import com.hkycu.goods.util.OrderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private TearDownDetailService tearDownDetailService;

    @RequestMapping("/addorders")
    @ResponseBody
    @Transactional
    public Result<List<OmsOrderItem>> addOrders(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {

        UmsMember member = (UmsMember) request.getSession().getAttribute("member");

        /*保存订单*/

        OmsOrder order = new OmsOrder();
        order.setMemberId(Long.parseLong(""+ member.getId()));
        order.setOrderSn(OrderUtil.generateTransferNo());
        /* order.setCreateTime(new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss").format(new Date()));*/
        order.setReceiverName(member.getNickname());
        order.setReceiverPhone(member.getPhone());
        order.setDeleteStatus(0);
        orderService.insertOrder(order);
        Long orderId = order.getId();

        /*从购物车中结算商品*/
        List<OmsOrderItem> orderItems = new ArrayList<OmsOrderItem>();
        List<Cart>  carts = CookieUtil.getCartInCookie(request,response);
        List<TearDownDetail>  tearDownDetails = new ArrayList<TearDownDetail>();
        int flag=0;
        for(Cart cart : carts){
            OmsOrderItem item = new OmsOrderItem();
            item.setProductId(cart.getId());
            item.setProductName(cart.getName());
            item.setProductPrice(cart.getPrice()*cart.getStock());
            item.setProductQuantity(cart.getStock());
            item.setPromotionName(cart.getDescription());
            item.setOrderId(orderId);
            item.setProductPic(cart.getPic());
            item.setProductBrand(cart.getProductCategoryName());
            item.setOrderSn(OrderUtil.generateTransferNo());

         /*   flag = orderItemService.insertOrderItem(item);*/

            orderItems.add(item);

            /*拆单*/
           TearDownDetail tearDownDetail = new TearDownDetail(orderId,item.getProductName(), cart.getStock().longValue(),item.getProductId().intValue());
            tearDownDetail.setProduceName("慧科宜春供应商");
            tearDownDetail.setProduceId(cart.getProduceId());//todo
           /* tearDownDetailService.insertTearDownDetail(tearDownDetail);*/

            tearDownDetails.add(tearDownDetail);
        }

    /*    int i = 10;
        System.out.println(i/0);*/

        /*订单详情的批处理*/
        orderItemService.batchInsertOrderItem(orderItems);

        //*拆单的批处理*//*
        tearDownDetailService.batchInsertOrderItem(tearDownDetails);

        Result<List<OmsOrderItem>> result=null;
/*
        if(flag>=0){
            result = new Result<>(CodeMsg.SUCESS);
        }else {
            result = new Result<>(CodeMsg.ADDORDER_ERROR);
        }
*/
        result = new Result<>(CodeMsg.SUCESS);

        CookieUtil.clearCartCookie(request,response);

        return result;
    }

    @RequestMapping("/queryteardown")
    @ResponseBody
    public Result<List<OmsOrderItem>> queryTearDown(Long orderId){
        return null;//todo
    }

    @RequestMapping("/queryorders")
    @ResponseBody
    public Result<List<OmsOrder>> queryOrders(HttpServletRequest request,Long memberId){
        UmsMember member = (UmsMember) request.getSession().getAttribute("member");
        if (ObjectUtils.isEmpty(member)){
            return new Result<>(CodeMsg.USER_NO_LOGIN);
        }
        List<OmsOrder> orders = orderService.queryOrderListByUid(Long.parseLong(member.getId() + ""));
        Result<List<OmsOrder>> result = new Result<List<OmsOrder>>(CodeMsg.SUCESS);
        result.setData(orders);
        return result;
    }

    @RequestMapping("/queryorderdetails")
    @ResponseBody
    public Result<List<OmsOrderItem>> queryOrderDetail(HttpServletRequest request, HttpServletResponse response,Long orderId) throws UnsupportedEncodingException {

        List<OmsOrderItem> omsOrderItems = orderItemService.selectItemByOrderId(orderId);
        Result<List<OmsOrderItem>> result = new Result<>(CodeMsg.SUCESS);
        result.setData(omsOrderItems);
        return result;
    }
}
