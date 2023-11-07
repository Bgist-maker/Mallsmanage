package com.bs.service;

import java.util.List;
import java.util.Map;

import com.bs.domain.Seller;

public interface SellerService {
	
	List<Seller> getSellerList(int pageSize,int pageCount);
	List<Seller> getAllSellers();
	int getSellerCount();
	List<Map<String, Object>> getSellerCityCount();
	int addSeller(Seller seller);
	int updateSeller(Seller seller);
	int deleteSeller(int id);
	Seller getSellerById(int id);
	
}
