package com.hkycu.goods.service.impl;

import com.hkycu.goods.dao.UmsMemberMapper;
import com.hkycu.goods.model.UmsMember;
import com.hkycu.goods.result.CodeMsg;
import com.hkycu.goods.result.Result;
import com.hkycu.goods.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class MemberServiceImpl implements MemberService{
    @Autowired
    private UmsMemberMapper umsMemberMapper;
    @Override
    public Result<UmsMember> login(UmsMember member) {
        Result<UmsMember> result = null;

        List<UmsMember> memberList = this.umsMemberMapper.list(member);
        if(ObjectUtils.isEmpty(memberList) || memberList.size() == 0) { // 是否查询到结果
            result = new Result<UmsMember>(CodeMsg.USERNAME_PASSWORD_ERROR);
        }else { // 查询到了数据
            result = new Result<UmsMember>(CodeMsg.SUCESS);
            result.setData(memberList.get(0));
        }
        return result;
    }

    @Override
    public UmsMember loginByPhone(String username, String password) {
        return umsMemberMapper.selectByPhone(username,password);
    }
}
