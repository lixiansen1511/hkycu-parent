package com.hkycu.goodsseckill.util;



import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {
    private final static String COOKIE_DOMAIN = "localhost";
    private final static String COOKIE_NAME = "seckill_login_token";

    /** 将token写入cookie */
    public static void writeLoginToken(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie(COOKIE_NAME, token);

        cookie.setDomain(COOKIE_DOMAIN);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(60 * 60 *24 * 30);

        response.addCookie(cookie);
    }

    /** 从cookie读取token */
    public static String readLoginToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for(Cookie cookie : cookies) {
                if(StringUtils.equals(cookie.getName(), "JSESSIONID")) {
                    System.out.println(cookie.getName());
                    return cookie.getValue();
                }
            }
        }

        return null;
    }


    /** 从cookie删除token */
    public static void delLoginToken(HttpServletRequest request,
                                     HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for(Cookie cookie : cookies) {
                if(StringUtils.equals(cookie.getName(), COOKIE_NAME)) {
                    cookie.setDomain(COOKIE_DOMAIN);
                    cookie.setPath("/");
                    cookie.setMaxAge(0);   // 设置为0， 代表删除该cookie
                    response.addCookie(cookie);

                    return ;
                }
            }
        }
    }
}
