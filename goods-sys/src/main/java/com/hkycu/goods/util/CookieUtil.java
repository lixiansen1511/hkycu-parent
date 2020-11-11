package com.hkycu.goods.util;

import com.hkycu.goods.model.Cart;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class CookieUtil {

    /*
    * 制作cookie的value*/
    public static String makeCookieValue(List<Cart> cartList) {
        StringBuffer buffer_2st = new StringBuffer();

        if(ObjectUtils.isEmpty(cartList) || cartList.size() == 0) {
            buffer_2st.append("==");
        }

        for(Cart item : cartList) {
            buffer_2st.append(
                    item.getId() + "="
                            + item.getProductCategoryName() + "="
                            + item.getName() + "="
                            + item.getPrice() + "="
                            + item.getDescription() + "="
                            + item.getStock() + "="
                            + item.getSale() + "="
                            + item.getProduce() + "="
                            + item.getProduceId() + "="
                            + item.getPic() + "=="
            );
        }
        return buffer_2st.toString().substring(0, buffer_2st.toString().length() - 2);
    }

    /** 获取cookie中的购物车列表 */
    public static List<Cart> getCartInCookie(
            HttpServletRequest request, HttpServletResponse response)
            throws UnsupportedEncodingException {
        // 定义一个购物车列表
        List<Cart> items = new ArrayList<>();
        String value_1st;
        // 购物车cookie
        Cookie cart_cookie = getCookie(request);
        // 判断cookie是否为空
        if(!ObjectUtils.isEmpty(cart_cookie)) { // 此时购物车的cookie不为空
            // 获取cookie中String类型的value，从cookie获取购物车
            value_1st = URLDecoder.decode(cart_cookie.getValue(), "UTF-8");
            // 判断value是否为空
            if(!StringUtils.isEmpty(value_1st)) {
                // 解析字符串中的数据为对象并封装在list中返回
                String[] arr_1st = value_1st.split("=="); // item之间的分割
                for(String value_2st: arr_1st) {
                    String[] arr_2st = value_2st.split("="); // item内部属性的拆分
                    Cart item = new Cart();
                    // 商品id
                    item.setId(Long.parseLong(arr_2st[0]));
                    // 商品类型
                    item.setProductCategoryName(arr_2st[1]);
                    // 商品名称
                    item.setName(arr_2st[2]);
                    // 商品价格
                    item.setPrice(Double.parseDouble(arr_2st[3]));
                    // 商品详情
                    item.setDescription(arr_2st[4]);
                    // 购物的数量
                    item.setStock(Integer.parseInt(arr_2st[5]));
                    // 销量
                    if(arr_2st[6] != null && !"null".equals(arr_2st[6])) {
                        item.setSale(Integer.parseInt(arr_2st[6]));
                    }
                    // 商品的发货地址
                    item.setProduce(arr_2st[7]);
                    // 商品的发货地址id
                    item.setProduceId(Integer.parseInt(arr_2st[8]));
                    // 商品的图片地址
                    item.setPic(arr_2st[9]);

                    items.add(item);
                }
            }
        }
        return items;
    }

    /** 获取名为“cart”的cookie */
    public static Cookie getCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        Cookie cart_cookie = null;
        if(cookies!=null) {
            for (Cookie cookie : cookies) {
                // 获取购物车的cookie
                if ("cart".equals(cookie.getName())) {
                    cart_cookie = cookie;
                }
            }
        }
        return cart_cookie;
    }


    public static void clearCartCookie(HttpServletRequest request,HttpServletResponse response){
        Cookie cookie = getCookie(request);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        cookie.setValue(null);
        response.addCookie(cookie);
    }
}
