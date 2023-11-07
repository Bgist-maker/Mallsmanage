package com.bs.IDao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bs.domain.User;

public interface UserMapper {
	
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);
    
    User selectByUserNo(String userno);
    
    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    List<User> getUserLists(@Param("pageIndex")int pageIndex,@Param("pageSize")int pageSize);
    
    List<User> getAllUser();
    
    int getUserCount();
}