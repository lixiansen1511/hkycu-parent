package com.hkycu.goods.web;

import com.hkycu.goods.model.Cart;
import com.hkycu.goods.model.PmsProduct;
import com.hkycu.goods.model.UmsMember;
import com.hkycu.goods.result.CodeMsg;
import com.hkycu.goods.result.Result;
import com.hkycu.goods.service.GoodsService;
import com.hkycu.goods.util.CookieUtil;
import com.hkycu.goods.util.OrderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    /**
     * 查询商品列表
     */
    @RequestMapping("/queryList")
    @ResponseBody
    public Result<List<PmsProduct>> queryList(PmsProduct goods, HttpServletRequest request) {
       UmsMember member = (UmsMember) request.getSession().getAttribute("member");

        if (ObjectUtils.isEmpty(member)) { // 用户未登录
            Result result = new Result(CodeMsg.USER_NO_LOGIN);
            return result;
        }

        List<PmsProduct> list = new ArrayList<>();

        try {
            list = this.goodsService.queryList(goods);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Result<List<PmsProduct>>(list);
    }

    /**
     * 购物车-1：添加商品到购物车列表
     */
    @RequestMapping(value = "/addGoodsToCart", method = RequestMethod.GET)
    @ResponseBody
    public Result<List<Cart>> addGoodsToCart(@RequestParam Integer goodsId,
                                             @RequestParam Integer stock,
                                             HttpServletRequest request,
                                             HttpServletResponse response)
            throws UnsupportedEncodingException {

        /*
         * 1、取到cookie
         * 2、取到cookie的value即cartlist
         * 3、判断cartlist是否为空
         * 4、cartlist为空，判断cookie是否为空，是：创建cookie，把cart加入cookie的value中；否：new cookie把cart加入cookie的value中，
         * 5、cartlist不为空，cart加入到cookie中
         * */

        // 从cookie中获取购物车列表
        List<Cart> cartList = CookieUtil.getCartInCookie(request, response);
        Cookie cookie_2st;
        PmsProduct goods = new PmsProduct();

        // 查询商品的具体信息
        try {
           /* PmsProduct goods1 = new PmsProduct();
            goods1.setId(Long.parseLong("" + goodsId));
            List<PmsProduct> goodsList = this.goodsService.queryList(goods1);//todo
            goods = goodsList.get(0);*/

            goods = this.goodsService.queryProductById(Long.parseLong("" + goodsId));

        } catch (Exception e) {
            e.printStackTrace();
        }

        // 如果购物车列表为空
        if (cartList == null || cartList.size() <= 0) {
            Cart cartVo = new Cart();
            cartVo.setStock(stock);
            cartVo.setPrice(goods.getPrice().doubleValue());
            cartVo.setId(goods.getId());
            cartVo.setProductCategoryName(goods.getProductCategoryName());
            cartVo.setName(goods.getName());
            cartVo.setProduce(goods.getProduce());
            cartVo.setProduceId(goods.getProduceId());
            cartVo.setPic(goods.getPic());
            cartVo.setDescription(goods.getDescription());
            cartVo.setProduceId(goods.getProduceId());
            // 将当前的商品添加到购物车列表中
            cartList.add(cartVo);

            if (ObjectUtils.isEmpty(CookieUtil.getCookie(request))) {
                // 制作购物车cookie数据
                cookie_2st = new Cookie("cart", URLEncoder.encode(
                        CookieUtil.makeCookieValue(cartList), "UTF-8"
                ));
                // 设置该项目下都可以访问该cookie
                cookie_2st.setPath("/");
                // 设置cookie的有效时间30分钟
                cookie_2st.setMaxAge(60 * 30);
                // 添加客户端的相应中
                response.addCookie(cookie_2st);
            } else {
                cookie_2st = CookieUtil.getCookie(request);
                // 设置该项目下都可以访问该cookie
                cookie_2st.setPath("/");
                // 设置cookie的有效时间30分钟
                cookie_2st.setMaxAge(60 * 30);
          /*      cookie_2st = new Cookie("cart", URLEncoder.encode(
                        CookieUtil.makeCookieValue(cartList), "UTF-8"
                ));*/
                cookie_2st.setValue(URLEncoder.encode(
                        CookieUtil.makeCookieValue(cartList), "UTF-8"
                ));
                // 添加客户端的相应中
                response.addCookie(cookie_2st);
            }
        } else { // 购物车不为空

            int bj = 0;
            for (Cart cart : cartList) {
                // 如果购物车中存在商品则数量+1
                long id = cart.getId(); // 购物车中
                long id_1 = goodsId; // 页面中传递过来的
                if (id == id_1) {
                    cart.setStock(cart.getStock() + stock);//
                    bj = 1;
                    break;
                }

            }


            if (bj == 0) {
                Cart cartVo = new Cart();
                if (cartVo.getStock() != null) {
                    cartVo.setStock(cartVo.getStock() + stock);
                } else {
                    cartVo.setStock(stock);
                }

                cartVo.setPrice(goods.getPrice().doubleValue());
                cartVo.setId(goods.getId());
                cartVo.setProductCategoryName(goods.getProductCategoryName());
                cartVo.setName(goods.getName());
                cartVo.setProduce(goods.getProduce());
                cartVo.setProduceId(goods.getProduceId());
                cartVo.setPic(goods.getPic());
                cartVo.setDescription(goods.getDescription());
                // 将当前传来的商品添加到购物车列表中
                cartList.add(cartVo);
            }
            // 获取名为cart的cookie
            cookie_2st = CookieUtil.getCookie(request);
            // 设置该项目下都可以访问该cookie
            cookie_2st.setPath("/");
            // 设置cookie的有效时间30分钟
            cookie_2st.setMaxAge(60 * 30);

            cookie_2st.setValue(URLEncoder.encode(
                    CookieUtil.makeCookieValue(cartList), "UTF-8"
            ));

            // 添加客户端的相应中
            response.addCookie(cookie_2st);
        }

        Result<List<Cart>> result = new Result<>(CodeMsg.SUCESS);
        result.setData(cartList);
        request.getSession().setAttribute("res", result);
        return result;
    }

    @RequestMapping("/getCart")
    @ResponseBody
    public Result<List<Cart>> getCart(HttpServletRequest request,
                                      HttpServletResponse response) throws UnsupportedEncodingException {
        List<Cart> carts = CookieUtil.getCartInCookie(request, response);
        Result<List<Cart>> result = new Result<>(CodeMsg.SUCESS);
        result.setData(carts);
        /*     Result<List<Cart>> result = (Result<List<Cart>>) request.getSession().getAttribute("res");*/
        return result;
    }

    @RequestMapping("/clearAllCookie")
    @ResponseBody
    public Result clearCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = CookieUtil.getCookie(request);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        cookie.setValue(null);
        response.addCookie(cookie);
        Result result = new Result(CodeMsg.SUCESS);
        return result;
    }

    @RequestMapping("/deleteByGoodsId")
    @ResponseBody
    public Result<List<Cart>> deleteByGoodsId(Long goodsId,
                                              HttpServletRequest request,
                                              HttpServletResponse response)
            throws UnsupportedEncodingException {
        // 获取cookie中购物车列表
        List<Cart> cartList = CookieUtil.getCartInCookie(request, response);
        Cart deleteCart = null;
        // 判断购物车列表是否为空
        if (cartList.size() > 0) {
            // 遍历购物车列表寻找相同id的商品
            for (Cart cart : cartList) {
                if (cart.getId().equals(goodsId)) {
                    deleteCart = cart;
                    break;
                }
            }
            // 判断是否找到相同id的商品
            if (deleteCart != null) {
                // 判断购物车中商品的数量
                if (deleteCart.getStock() > 1) {
                    // 数量大于1， 让数量-1
                    deleteCart.setStock(deleteCart.getStock() - 1);
                    cartList.remove(deleteCart);
                    cartList.add(deleteCart);
                } else {
                    // 此时该商品的数量已经为0，直接在购物车中删除该商品
                    cartList.remove(deleteCart);
                }
                // 刷新cookie
                Cookie cookie = CookieUtil.getCookie(request);
                cookie.setValue(URLEncoder.encode(
                        CookieUtil.makeCookieValue(cartList), "UTF-8"));
                // 设置cookie的有效期时间为10分钟
                cookie.setMaxAge(60 * 10);
                // 设置路径
                cookie.setPath("/");
                // 更新cookie
                response.addCookie(cookie);
            }
        }
        Result<List<Cart>> result = new Result<>(CodeMsg.SUCESS);
        result.setData(cartList);

        return result;
    }

    @RequestMapping("/add")
    @ResponseBody
    public Result addGoods(PmsProduct goods) {
        System.out.println("====" + goods.getName() + goods.getPrice() + "==description=" + goods.getDescription());
        System.out.println("==pic==" + goods.getPic());
        goods.setProductSn(OrderUtil.generateTransferNo());
        goods.setDescription(goods.getDescription().substring(3,goods.getDescription().length()-4));
        System.out.println("==description=" + goods.getDescription());
        goods.setProduceId(1001);
        goods.setPic(goods.getPic());
        goods.setPromotionStartTime(new Date());
        int flag = goodsService.addGoods(goods);
        System.out.println("==flag==" + flag);
        if (flag > 0) {
            return new Result(CodeMsg.SUCESS);
        }
        return new Result(CodeMsg.REQUEST_ILLEGAL);
    }



    @RequestMapping("/uploadPhoto")
    @ResponseBody
    public Result uploadPhoto(@RequestParam("pic") MultipartFile pic,HttpServletResponse response) throws IOException {
        System.out.println(pic);

        /*
        * 编码为字符串*/
        String s = Base64Utils.encodeToString(pic.getBytes());
        System.out.println("s:"+s);

        /* *
         *2.解码成字节数组
        */
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] bytes = decoder.decode(s);

        /*
         * 3.字节流转文件
         */
        String d = System.getProperty("user.dir");
        File fileMkdir = new File(d+"\\goods-sys\\src\\main\\resources\\static\\img");
        if (!fileMkdir.exists()){
            fileMkdir.mkdir();
        }
        String pathName = fileMkdir.getPath() + "\\" + pic.getOriginalFilename();

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(pathName);
            fos.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        Result result= new Result(CodeMsg.SUCESS);
        result.setData("img\\"+ pic.getOriginalFilename());
        return result;
    }

    /*后台管理商品列表*/
    @RequestMapping("/queryListByAdmin")
    @ResponseBody
    public Result<List<PmsProduct>> queryListByAdmin(PmsProduct goods, HttpServletRequest request) {
/*        UmsMember member = (UmsMember) request.getSession().getAttribute("member");

        if (ObjectUtils.isEmpty(member)) { // 用户未登录
            Result result = new Result(CodeMsg.USER_NO_LOGIN);
            return result;
        }*/

        List<PmsProduct> list = new ArrayList<>();

        try {
            list = this.goodsService.queryList(goods);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Result<List<PmsProduct>>(list);
    }

    @RequestMapping("/queryGoodsById")
    @ResponseBody
    public Result<Cart> queryGoodsById(Long id){
        PmsProduct goods = goodsService.queryProductById(id);
        System.out.println("***goods:"+goods.toString());
        if (ObjectUtils.isEmpty(goods)){
            return new Result<>(CodeMsg.GOODS_NOT_EXISTS);
        }
        Cart cart = new Cart();
        cart.setId(goods.getId());
        cart.setName(goods.getName());
        cart.setDescription(goods.getDescription());
        cart.setPic(goods.getPic());
        cart.setPrice(Double.parseDouble(goods.getPrice()+""));
        cart.setProduce(goods.getProduce());
        cart.setProduceId(goods.getProduceId());
        cart.setProductCategoryName(goods.getProductCategoryName());
        cart.setProductSn(goods.getProductSn());
        cart.setSale(goods.getSale());
        cart.setStock(goods.getStock());

        Result<Cart> result = new Result<>(CodeMsg.SUCESS);
        result.setData(cart);
        return result;
    }

    @RequestMapping("/goodsRemove")
    @ResponseBody
    public Result goodsRemove(long id){
        int flag = goodsService.deleteGoodsById(id);
        if (flag == 0){
            return new Result(CodeMsg.GOODS_NOT_EXISTS);
        }

        Result result = new Result(CodeMsg.SUCESS);
        return result;
    }

    @RequestMapping("/goodsEdit")
    @ResponseBody
    public Result goodsEdit(PmsProduct goods){
        goods.setDescription(goods.getDescription().substring(3,goods.getDescription().length()-4));
        int f = goodsService.updateGoods(goods);
        if (f == 0){
            return new Result(CodeMsg.GOODS_NOT_EXISTS);
        }
        return new Result(CodeMsg.SUCESS);
    }


}