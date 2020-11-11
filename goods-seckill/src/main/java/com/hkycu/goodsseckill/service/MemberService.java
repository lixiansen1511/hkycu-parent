package com.hkycu.goodsseckill.service;

import com.hkycu.goodsseckill.model.UmsMember;
import org.apache.ibatis.annotations.Param;

public interface MemberService {
    UmsMember login(@Param("phone") String phone, @Param("password") String password);
    UmsMember loginByPhone(@Param("phone") String phone);
    UmsMember loginByusername(@Param("username") String username,@Param("password") String password);
}