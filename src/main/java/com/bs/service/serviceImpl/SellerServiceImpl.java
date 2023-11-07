package com.bs.service.serviceImpl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.bs.IDao.SellerMapper;
import com.bs.domain.Seller;
import com.bs.service.SellerService;

@Service("SellerService") 
public class SellerServiceImpl implements SellerService{
	@Resource
	private SellerMapper SellerDao;

	@Override
	public List<Seller> getSellerList(int pageSize, int pageCount) {
		return SellerDao.getSellerList((pageCount-1)*pageSize, pageSize);
	}

	@Override
	public int getSellerCount() {
		return SellerDao.getSellerCount();
	}
	@Override
	public List<Map<String, Object>> getSellerCityCount() {
	
		return SellerDao.getSellerCityCount();
		
	}
	@Override
	public int addSeller(Seller seller) {
		return SellerDao.insertSelective(seller);
	}

	@Override
	public int updateSeller(Seller seller) {
		return SellerDao.updateByPrimaryKeySelective(seller);
	}

	@Override
	public Seller getSellerById(int id) {
		return SellerDao.selectByPrimaryKey(id);
	}

	@Override
	public List<Seller> getAllSellers() {
		return SellerDao.getAllSellers();
	}

	@Override
	public int deleteSeller(int id) {
		return SellerDao.deleteByPrimaryKey(id);
	}
}
