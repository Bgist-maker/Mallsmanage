package com.bs.IDao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bs.domain.Commodity;

public interface CommodityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Commodity record);

    int insertSelective(Commodity record);

    Commodity selectByPrimaryKey(Integer id);
    
    Commodity selectByName(String name);

    int updateByPrimaryKeySelective(Commodity record);

    int updateByPrimaryKey(Commodity record);

	List<Commodity> getCommodityList(@Param("pageIndex")int pageIndex,@Param("pageSize")int pageSize);
	
	List<Commodity> getCommodityListForSearch(Map<String,String> map);
	
	List<Commodity> getAllCommoditys();
	
	List<Commodity> getAllCommoditysByCategoryid(int categoryid);

	int getCommodityCount();
	
	int getCommodityCountForSearch(Map<String,String> map);
}