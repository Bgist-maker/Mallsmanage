package com.bs.IDao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bs.domain.Unit;

public interface UnitMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Unit record);

    int insertSelective(Unit record);

    Unit selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Unit record);

    int updateByPrimaryKey(Unit record);

	List<Unit> getUnitList(@Param("pageIndex")int pageIndex,@Param("pageSize")int pageSize);

	int getUnitCount();

	List<Unit> getAllUnits();

	Unit getUnitByName(String name);
}