package com.bs.service;

import java.util.List;
import java.util.Map;

import com.bs.domain.Operation;

public interface OperationService {
	
	List<Operation> getOperationList(int pageSize,int pageCount,Map<String,String> map);
	int getOperationCount(Map<String,String> map);
	int addOperation(Operation Operation);
	int updateOperation(Operation Operation);
	int deleteOperation(int id);
	Operation getOperationById(int id);
	List<Operation> getAllOperationList();
}
