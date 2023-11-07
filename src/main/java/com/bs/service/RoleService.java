package com.bs.service;

import java.util.List;

import com.bs.domain.Role;

public interface RoleService {
	
	List<Role> getRoleList(int pageSize,int pageCount);
	List<Role> getAllRoles();
	int getRoleCount();
	int addRole(Role role);
	int updateRole(Role role);
	int deleteRoles(String ids);
	Role getRoleById(int id);
	Role getRoleByName(String name);
}
