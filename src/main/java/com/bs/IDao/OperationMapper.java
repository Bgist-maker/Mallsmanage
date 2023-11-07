package com.bs.IDao;

import java.util.List;
import java.util.Map;

import com.bs.domain.Operation;

public interface OperationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Operation record);

    int insertSelective(Operation record);

    Operation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Operation record);

    int updateByPrimaryKey(Operation record);

	List<Operation> getOperationList(Map<String,String> map);
	
	List<Operation> getAllOperationList();

	int getOperationCount(Map<String,String> map);
}