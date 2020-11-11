package com.hkycu.goods.web;

import com.hkycu.goods.model.OmsOrderItem;
import com.hkycu.goods.model.UmsMember;
import com.hkycu.goods.result.CodeMsg;
import com.hkycu.goods.result.Result;
import com.hkycu.goods.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/counts")
public class CountsController {

    @Autowired
    public OrderItemService orderItemService;

    @RequestMapping("/queryList")
    @ResponseBody
    public Result<List<OmsOrderItem>> queryList(HttpServletRequest requestr){
       UmsMember member = (UmsMember)requestr.getSession().getAttribute("member");
       if(StringUtils.isEmpty(member)){
           Result result = new Result(CodeMsg.USER_NO_LOGIN);
           return result;
       }
        List<OmsOrderItem> orderItems = orderItemService.selectOrderItemByUid(Long.parseLong(""+member.getId()));
        Result<List<OmsOrderItem>> result = new Result<List<OmsOrderItem>>(CodeMsg.SUCESS);
        result.setData(orderItems);
        return result;
    }
}
