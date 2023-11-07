package com.bs.IDao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bs.domain.Buyer;

public interface BuyerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Buyer record);

    int insertSelective(Buyer record);

    Buyer selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Buyer record);

    int updateByPrimaryKey(Buyer record);

	List<Buyer> getBuyerList(@Param("pageIndex")int pageIndex,@Param("pageSize")int pageSize);

	int getBuyerCount();

	List<Buyer> getAllBuyers();
	List<Map<String,Object>>  getBuyerCityCount();

	Buyer getBuyerByName(String name);
}