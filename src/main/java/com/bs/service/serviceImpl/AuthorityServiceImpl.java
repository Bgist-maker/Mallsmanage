package com.bs.service.serviceImpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bs.IDao.AuthorityMapper;
import com.bs.domain.Authority;
import com.bs.service.AuthorityService;

@Service("AuthorityService") 
public class AuthorityServiceImpl implements AuthorityService{
	@Resource
	private AuthorityMapper authorityDao;

	@Override
	public List<Authority> getAllAuthoritys() {
		return authorityDao.getAllAuthoritys();
	}

	@Override
	public List<Authority> getAuthoritysByRoleId(int roleid) {
		return authorityDao.getAuthoritysByRoleId(roleid);
	}

	@Override
	public int batchInsertAuthoritys(List<Authority> authoritys) {
		return authorityDao.batchInsertAuthoritys(authoritys);
	}

	@Override
	public int deleteAuthoritysByRoleId(int roleid) {
		return authorityDao.deleteAuthoritysByRoleId(roleid);
	}

	@Override
	public int updateRoleAuthority(int roleid, List<Authority> authoritys) {
		authorityDao.deleteAuthoritysByRoleId(roleid);
		return authorityDao.batchInsertAuthoritys(authoritys);
	}


}
