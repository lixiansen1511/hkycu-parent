package com.hkycu.goodsseckill.service.Impl;

import com.hkycu.goodsseckill.dao.UmsMemberDao;
import com.hkycu.goodsseckill.model.UmsMember;
import com.hkycu.goodsseckill.service.MemberService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService{
    @Autowired
    private UmsMemberDao memberDao;
    @Override
    public UmsMember login(String phone, String password) {
        return memberDao.selectByPhoneAndPassword(phone,password);
    }

    @Override
    public UmsMember loginByPhone(@Param("phone") String phone) {
        return memberDao.selectByPhone(phone);
    }

    @Override
    public UmsMember loginByusername(String username, String password) {
        return null;
    }
}
