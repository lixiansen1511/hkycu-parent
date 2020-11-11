package com.hkycu.goodsseckill.dao;

import com.hkycu.goodsseckill.model.UmsMember;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UmsMemberDao {
    int deleteByPrimaryKey(Long id);

    int insert(UmsMember record);

    int insertSelective(UmsMember record);

    UmsMember selectByPrimaryKey(Long id);

    UmsMember selectByPhone(String phone);

    UmsMember selectByPhoneAndPassword(@Param("phone") String phone, @Param("password") String password);

    UmsMember selectByusername(@Param("username") String username,@Param("password") String password);

    int updateByPrimaryKeySelective(UmsMember record);

    int updateByPrimaryKey(UmsMember record);
}