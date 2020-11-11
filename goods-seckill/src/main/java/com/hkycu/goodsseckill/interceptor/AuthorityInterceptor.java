package com.hkycu.goodsseckill.interceptor;

import com.alibaba.fastjson.JSON;
import com.hkycu.goodsseckill.annotations.AccessLimit;
import com.hkycu.goodsseckill.model.UmsMember;
import com.hkycu.goodsseckill.redis.AccessKey;
import com.hkycu.goodsseckill.redis.RedisService;
import com.hkycu.goodsseckill.redis.UserKey;
import com.hkycu.goodsseckill.result.CodeMsg;
import com.hkycu.goodsseckill.result.Result;
import com.hkycu.goodsseckill.util.CookieUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

/** 使用拦截器统一校验用户权限 */
@Component
public class AuthorityInterceptor implements HandlerInterceptor {

    @Resource
    private RedisService redisService;

    private Logger logger = LoggerFactory.getLogger(AuthorityInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 对Controller中的方法名进行拦截处理
        if(handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;

            // 解析方法
            String methodName = handlerMethod.getMethod().getName();
            String className = handlerMethod.getBean().getClass().getSimpleName();
            // 请求的参数
            StringBuffer requestParamBuffer = new StringBuffer();
            Map parameterMap = request.getParameterMap();
            Iterator iterator = parameterMap.entrySet().iterator();
            while(iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                String mapKey = (String) entry.getKey();
                String mapValue = "";

                // request中的参数map的value返回的是一个String[]，需要转换成一个字符串
                Object object = entry.getValue();
                if(object instanceof String[]) {
                    String[] strings = (String[]) object;
                    mapValue = Arrays.toString(strings);
                }
                
                requestParamBuffer.append(mapKey).append("=").append(mapValue);
            }
            
            // 秒杀限流的处理
            AccessLimit accessLimit =
handlerMethod.getMethodAnnotation(AccessLimit.class);
            if(ObjectUtils.isEmpty(accessLimit)) {
                return true;
            }
            
            int seconds = accessLimit.seconds();
            int maxCount = accessLimit.maxCount();
            boolean needLogin = accessLimit.needLogin();
            
            String key = request.getRequestURI();
            
            // 对于拦截器中拦截login登录的处理，直接放行，不再进行拦截
            if(!StringUtils.equals(className, "SeckillController")) {  // 不是秒杀所在的Controller，直接放行
                logger.info("权限拦截器拦截到请求放行, className:{}, methodName:{}",  
className, methodName);
                return true;
            }

            logger.info("----> 权限拦截器拦截到请求  SeckillController, className:{}, methodName:{}, param:{}", className, methodName, requestParamBuffer);

            UmsMember user = null;

            String loginToken = CookieUtil.readLoginToken(request);

            if(StringUtils.isNotEmpty(loginToken)) {
                user = this.redisService.get(UserKey.getByName, loginToken,UmsMember.class);
            }

            if(needLogin) { // 需要登录
                if(ObjectUtils.isEmpty(user)) {  // 未登录，返回：用户未登录
                    render(response, CodeMsg.USER_NO_LOGIN);
                    return false;
                }
                key += "_" + user.getId();
            }

            // 对特定时间内访问次数的处理。
            AccessKey accessKey = AccessKey.withExpire;
            Integer count = this.redisService.get(accessKey, key, Integer.class);
            if(ObjectUtils.isEmpty(count)) {
                this.redisService.set(accessKey, key, 1, seconds);
                System.out.println("key:" + accessKey.getPrefix()+key);
            }else if(count < maxCount) {
                this.redisService.incr(accessKey, key);
                System.out.println("count:"+count);
            }else {
                render(response, CodeMsg.ACCESS_ILIMIT_REACHED);
                return false;
            }


        }else if(handler instanceof ResourceHttpRequestHandler) {  // 如果是资源请求
            return true;
        }

        return true;
    }

    private void render(HttpServletResponse response, CodeMsg codeMsg) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        OutputStream out = response.getOutputStream();
        String str = JSON.toJSONString(Result.error(codeMsg));
        out.write(str.getBytes("UTF-8"));
        out.flush();
        out.close();
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}