package com.bs.IDao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bs.domain.Role;

public interface RoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer id);
    
    Role getRoleByName(String name);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
    
    List<Role> getRoleList(@Param("pageIndex")int pageIndex,@Param("pageSize")int pageSize);
    
	List<Role> getAllRoles();
	
	int getRoleCount();
	
	int deleteRolesByIds(@Param("ids") String ids);
}