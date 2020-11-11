package com.hkycu.goodsseckill.filter;

import com.hkycu.goodsseckill.Const;
import com.hkycu.goodsseckill.model.UmsMember;
import com.hkycu.goodsseckill.redis.RedisService;
import com.hkycu.goodsseckill.redis.UserKey;
import com.hkycu.goodsseckill.util.CookieUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/** 处理登录的过滤器 */
@Component
public class LoginFilter implements Filter {
    @Autowired
    private RedisService redisService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        String loginToken = CookieUtil.readLoginToken(httpServletRequest);

        if(!StringUtils.isEmpty(loginToken)) {
            UmsMember user = this.redisService.get(UserKey.getByName, loginToken,
                    UmsMember.class);
            if(!ObjectUtils.isEmpty(user)) {
                this.redisService.expice(UserKey.getByName, loginToken,
                        Const.RedisCacheExpireTime.
                                REDIS_SESSION_EXPIRETIME);
            }
        }

        chain.doFilter(httpServletRequest, httpServletResponse);
    }

    @Override
    public void destroy() {

    }
}