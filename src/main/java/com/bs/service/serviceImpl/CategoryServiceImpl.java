package com.bs.service.serviceImpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bs.IDao.CategoryMapper;
import com.bs.domain.Category;
import com.bs.service.CategoryService;

@Service("CategoryService") 
public class CategoryServiceImpl implements CategoryService{
	@Resource
	private CategoryMapper categoryDao;

	@Override
	public List<Category> getCategoryList(int pageSize, int pageCount) {
		return categoryDao.getCategoryList((pageCount-1)*pageSize, pageSize);
	}

	@Override
	public List<Category> getAllCategorys() {
		return categoryDao.getAllCategorys();
	}

	@Override
	public int getCategoryCount() {
		return categoryDao.getCategoryCount();
	}

	@Override
	public int addCategory(Category category) {
		return categoryDao.insertSelective(category);
	}

	@Override
	public int updateCategory(Category category) {
		return categoryDao.updateByPrimaryKeySelective(category);
	}

	@Override
	public int deleteCategory(int id) {
		return categoryDao.deleteByPrimaryKey(id);
	}

	@Override
	public Category getCategoryById(int id) {
		return categoryDao.selectByPrimaryKey(id);
	}

	@Override
	public Category getCategoryByName(String name) {
		return categoryDao.getCategoryByName(name);
	}

}
