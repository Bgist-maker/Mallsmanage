package com.bs.service.serviceImpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bs.IDao.AuthorityMapper;
import com.bs.IDao.UnitMapper;
import com.bs.domain.Unit;
import com.bs.service.UnitService;

@Service("UnitService") 
public class UnitServiceImpl implements UnitService{
	@Resource
	private UnitMapper UnitDao;
	@Resource
	private AuthorityMapper authorityDao;

	@Override
	public List<Unit> getUnitList(int pageSize, int pageCount) {
		return UnitDao.getUnitList((pageCount-1)*pageSize, pageSize);
	}

	@Override
	public int getUnitCount() {
		return UnitDao.getUnitCount();
	}

	@Override
	public int addUnit(Unit Unit) {
		return UnitDao.insertSelective(Unit);
	}

	@Override
	public int updateUnit(Unit Unit) {
		return UnitDao.updateByPrimaryKeySelective(Unit);
	}

	@Override
	public Unit getUnitById(int id) {
		return UnitDao.selectByPrimaryKey(id);
	}

	@Override
	public List<Unit> getAllUnits() {
		return UnitDao.getAllUnits();
	}

	@Override
	public int deleteUnit(int id) {
		return UnitDao.deleteByPrimaryKey(id);
	}

	@Override
	public Unit getUnitByName(String name) {
		return UnitDao.getUnitByName(name);
	}
}
