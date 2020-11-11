package com.hkycu.goods.dao;

import com.hkycu.goods.model.UmsMember;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UmsMemberMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UmsMember record);

    int insertSelective(UmsMember record);

    UmsMember selectByPrimaryKey(Long id);

    UmsMember selectByPhone(@Param("username") String username, @Param("password") String password);

    int updateByPrimaryKeySelective(UmsMember record);

    int updateByPrimaryKey(UmsMember record);

    List<UmsMember> list(UmsMember member);
}