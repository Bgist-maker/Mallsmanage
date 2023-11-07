package com.bs.service;

import java.util.List;
import java.util.Map;

import com.bs.domain.Buyer;

public interface BuyerService {
	
	List<Buyer> getBuyerList(int pageSize,int pageCount);
	List<Buyer> getAllBuyers();
	int getBuyerCount();
	List<Map<String, Object>> getBuyerCityCount();
	int addBuyer(Buyer Buyer);
	int updateBuyer(Buyer Buyer);
	int deleteBuyer(int id);
	Buyer getBuyerById(int id);
	Buyer getBuyerByName(String name);
}
