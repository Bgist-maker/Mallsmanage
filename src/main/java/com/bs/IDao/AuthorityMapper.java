package com.bs.IDao;

import java.util.List;

import com.bs.domain.Authority;

public interface AuthorityMapper {
	
 	int deleteByPrimaryKey(String id);

    int insert(Authority record);

    int insertSelective(Authority record);

    Authority selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Authority record);

    int updateByPrimaryKey(Authority record);
    
    List<Authority> getAllAuthoritys();
    
    List<Authority> getAuthoritysByRoleId(int roleid);
    
    int batchInsertAuthoritys(List<Authority> authoritys);
    
    int deleteAuthoritysByRoleId(int roleid);
}