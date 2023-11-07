package com.bs.IDao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bs.domain.Category;

public interface CategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Category record);

    int insertSelective(Category record);

    Category selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);
    
	List<Category> getCategoryList(@Param("pageIndex")int pageIndex,@Param("pageSize")int pageSize);
	
	List<Category> getAllCategorys();
	
	Category getCategoryByName(String name);
	
	int getCategoryCount();
}