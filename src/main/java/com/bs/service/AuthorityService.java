package com.bs.service;

import java.util.List;

import com.bs.domain.Authority;

public interface AuthorityService {
	
	List<Authority> getAllAuthoritys();
	    
    List<Authority> getAuthoritysByRoleId(int roleid);
    
    int batchInsertAuthoritys(List<Authority> authoritys);
    
    int deleteAuthoritysByRoleId(int roleid);
    
    int updateRoleAuthority(int roleid,List<Authority> authoritys);
}
