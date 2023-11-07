package com.bs.service.serviceImpl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bs.IDao.CommodityMapper;
import com.bs.domain.Commodity;
import com.bs.service.CommodityService;

@Service("CommodityService") 
public class CommodityServiceImpl implements CommodityService{
	@Resource
	private CommodityMapper CommodityDao;

	@Override
	public List<Commodity> getCommodityList(int pageSize, int pageCount) {
		return CommodityDao.getCommodityList((pageCount-1)*pageSize, pageSize);
	}

	@Override
	public int getCommodityCount() {
		return CommodityDao.getCommodityCount();
	}

	@Override
	public int addCommodity(Commodity Commodity) {
		return CommodityDao.insertSelective(Commodity);
	}

	@Override
	public int updateCommodity(Commodity Commodity) {
		return CommodityDao.updateByPrimaryKeySelective(Commodity);
	}

	@Override
	public int deleteCommodity(int id) {
		return CommodityDao.deleteByPrimaryKey(id);
	}

	@Override
	public Commodity getCommodityById(int id) {
		return CommodityDao.selectByPrimaryKey(id);
	}

	@Override
	public List<Commodity> getAllCommoditys() {
		return CommodityDao.getAllCommoditys();
	}

	@Override
	public Commodity getCommodityByname(String name) {
		return CommodityDao.selectByName(name);
	}

	@Override
	public List<Commodity> getAllCommoditysByCategoryid(int categoryid) {
		return CommodityDao.getAllCommoditysByCategoryid(categoryid);
	}

	@Override
	public List<Commodity> getCommodityListForSearch(Map<String, String> map) {
		return CommodityDao.getCommodityListForSearch(map);
	}

	@Override
	public int getCommodityCountForSearch(Map<String, String> map) {
		return CommodityDao.getCommodityCountForSearch(map);
	}
}
