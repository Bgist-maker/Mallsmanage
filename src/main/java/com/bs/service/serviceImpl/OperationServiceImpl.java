package com.bs.service.serviceImpl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bs.IDao.OperationMapper;
import com.bs.domain.Operation;
import com.bs.service.OperationService;

@Service("OperationService") 
public class OperationServiceImpl implements OperationService{
	@Resource
	private OperationMapper operationDao;

	@Override
	public List<Operation> getOperationList(int pageSize, int pageCount,Map<String,String> map) {
		map.put("pageIndex", String.valueOf((pageCount-1)*pageSize));
		map.put("pageSize", String.valueOf(pageSize));
		return operationDao.getOperationList(map);
	}
	@Override
	public List<Operation> getAllOperationList() {
		return operationDao.getAllOperationList();
	}

	@Override
	public int getOperationCount(Map<String,String> map) {
		return operationDao.getOperationCount(map);
	}

	@Override
	public int addOperation(Operation Operation) {
		return operationDao.insertSelective(Operation);
	}

	@Override
	public int updateOperation(Operation Operation) {
		return operationDao.updateByPrimaryKeySelective(Operation);
	}

	@Override
	public int deleteOperation(int id) {
		return operationDao.deleteByPrimaryKey(id);
	}

	@Override
	public Operation getOperationById(int id) {
		return operationDao.selectByPrimaryKey(id);
	}
}
