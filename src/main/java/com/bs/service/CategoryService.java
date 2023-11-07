package com.bs.service;

import java.util.List;

import com.bs.domain.Category;

public interface CategoryService {
	
	List<Category> getCategoryList(int pageSize,int pageCount);
	List<Category> getAllCategorys();
	int getCategoryCount();
	int addCategory(Category category);
	int updateCategory(Category category);
	int deleteCategory(int id);
	Category getCategoryById(int id);
	Category getCategoryByName(String name);
}
