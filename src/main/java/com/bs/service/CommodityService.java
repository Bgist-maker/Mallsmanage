package com.bs.service;

import java.util.List;
import java.util.Map;

import com.bs.domain.Commodity;

public interface CommodityService {
	
	List<Commodity> getCommodityListForSearch(Map<String,String> map);
	List<Commodity> getCommodityList(int pageSize,int pageCount);
	List<Commodity> getAllCommoditys();
	List<Commodity> getAllCommoditysByCategoryid(int categoryid);
	int getCommodityCount();
	int getCommodityCountForSearch(Map<String,String> map);
	int addCommodity(Commodity commodity);
	int updateCommodity(Commodity commodity);
	int deleteCommodity(int id);
	Commodity getCommodityById(int id);
	Commodity getCommodityByname(String name);
}
