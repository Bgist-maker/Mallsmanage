package com.bs.service;

import java.util.List;

import com.bs.domain.Unit;

public interface UnitService {
	
	List<Unit> getUnitList(int pageSize,int pageCount);
	List<Unit> getAllUnits();
	int getUnitCount();
	int addUnit(Unit Unit);
	int updateUnit(Unit Unit);
	int deleteUnit(int id);
	Unit getUnitById(int id);
	Unit getUnitByName(String name);
}
