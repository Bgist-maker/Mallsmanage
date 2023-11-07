package com.bs.service.serviceImpl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bs.IDao.BuyerMapper;
import com.bs.domain.Buyer;
import com.bs.service.BuyerService;

@Service("BuyerService") 
public class BuyerServiceImpl implements BuyerService{
	@Resource
	private BuyerMapper BuyerDao;

	@Override
	public List<Buyer> getBuyerList(int pageSize, int pageCount) {
		return BuyerDao.getBuyerList((pageCount-1)*pageSize, pageSize);
	}

	@Override
	public int getBuyerCount() {
		return BuyerDao.getBuyerCount();
	}
	@Override
	public List<Map<String, Object>> getBuyerCityCount() {
	
		return BuyerDao.getBuyerCityCount();
		
	}
	@Override
	public int addBuyer(Buyer Buyer) {
		return BuyerDao.insertSelective(Buyer);
	}

	@Override
	public int updateBuyer(Buyer Buyer) {
		return BuyerDao.updateByPrimaryKeySelective(Buyer);
	}

	@Override
	public Buyer getBuyerById(int id) {
		return BuyerDao.selectByPrimaryKey(id);
	}

	@Override
	public List<Buyer> getAllBuyers() {
		return BuyerDao.getAllBuyers();
	}

	@Override
	public int deleteBuyer(int id) {
		return BuyerDao.deleteByPrimaryKey(id);
	}

	@Override
	public Buyer getBuyerByName(String name) {
		return BuyerDao.getBuyerByName(name);
	}
}
