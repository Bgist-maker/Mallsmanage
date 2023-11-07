package com.bs.service.serviceImpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bs.IDao.AuthorityMapper;
import com.bs.IDao.RoleMapper;
import com.bs.domain.Role;
import com.bs.service.RoleService;

@Service("RoleService") 
public class RoleServiceImpl implements RoleService{
	@Resource
	private RoleMapper roleDao;
	@Resource
	private AuthorityMapper authorityDao;

	@Override
	public List<Role> getRoleList(int pageSize, int pageCount) {
		return roleDao.getRoleList((pageCount-1)*pageSize, pageSize);
	}

	@Override
	public int getRoleCount() {
		return roleDao.getRoleCount();
	}

	@Override
	public int addRole(Role role) {
		return roleDao.insertSelective(role);
	}

	@Override
	public int updateRole(Role role) {
		return roleDao.updateByPrimaryKeySelective(role);
	}

	@Override
	public Role getRoleById(int id) {
		return roleDao.selectByPrimaryKey(id);
	}

	@Override
	public List<Role> getAllRoles() {
		return roleDao.getAllRoles();
	}

	@Override
	public int deleteRoles(String ids) {
		return roleDao.deleteRolesByIds(ids);
	}

	@Override
	public Role getRoleByName(String name) {
		return roleDao.getRoleByName(name);
	}
}
