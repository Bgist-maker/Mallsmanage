package com.bs.IDao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bs.domain.Seller;

public interface SellerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Seller record);

    int insertSelective(Seller record);

    Seller selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Seller record);

    int updateByPrimaryKey(Seller record);

	List<Seller> getSellerList(@Param("pageIndex")int pageIndex,@Param("pageSize")int pageSize);

	int getSellerCount();
	//Seller getSellerCityCount();
    List<Map<String,Object>>  getSellerCityCount();
	List<Seller> getAllSellers();
}