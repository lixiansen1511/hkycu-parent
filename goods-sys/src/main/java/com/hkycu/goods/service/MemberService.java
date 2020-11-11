package com.hkycu.goods.service;

import com.hkycu.goods.model.UmsMember;
import com.hkycu.goods.result.Result;
import org.apache.ibatis.annotations.Param;

public interface MemberService {
    Result<UmsMember> login(UmsMember member);
    UmsMember loginByPhone(@Param("username") String username, @Param("password") String password);
}
