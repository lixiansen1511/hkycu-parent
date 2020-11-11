package com.hkycu.goodsseckill.web;

import com.hkycu.goodsseckill.Const;
import com.hkycu.goodsseckill.model.Roles;
import com.hkycu.goodsseckill.model.UmsMember;
import com.hkycu.goodsseckill.redis.RedisService;
import com.hkycu.goodsseckill.redis.UserKey;
import com.hkycu.goodsseckill.result.CodeMsg;
import com.hkycu.goodsseckill.result.Result;
import com.hkycu.goodsseckill.service.MemberService;
import com.hkycu.goodsseckill.util.CookieUtil;
import com.hkycu.goodsseckill.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/user")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private RedisService redisService;

    @PostMapping("/login")
    @ResponseBody
    public Result login(@RequestBody UmsMember member, HttpServletRequest request, HttpServletResponse response){
        UmsMember member1 = memberService.loginByPhone(member.getPhone());
        if(ObjectUtils.isEmpty(member1)){
            return new Result(CodeMsg.USERNAME_NOT_EXISTS);
        }
        String dbPassword = MD5Util.inputPassToDbPass(member.getPassword(),member1.getSalt());

        if(!StringUtils.equals(dbPassword,member1.getPassword())){
            return new Result(CodeMsg.PASSWORD_ERROR);
        }
        Result result  = new Result(CodeMsg.SUCESS);
        member1.setPassword(StringUtils.EMPTY);
        member1.setSalt(StringUtils.EMPTY);
        result.setData(member1);
       /* request.getSession().setAttribute("user",member1);*/
        if(result.isSuccess()) {
            System.out.println("SessionId:"+request.getSession().getId());
            CookieUtil.writeLoginToken(response, request.getSession().getId());
            redisService.set(UserKey.getByName, request.getSession().getId(),
                    result.getData(),
                    Const.RedisCacheExpireTime.REDIS_SESSION_EXPIRETIME);
        }
        return result;
    }



}
