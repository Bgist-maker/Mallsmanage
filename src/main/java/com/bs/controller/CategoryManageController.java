package com.bs.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bs.domain.Category;
import com.bs.domain.Commodity;
import com.bs.service.CategoryService;
import com.bs.service.CommodityService;

@Controller
public class CategoryManageController {
	@Autowired
	private CommodityService commodityService;
	@Autowired
	private CategoryService categoryService;
	
	@InitBinder("category")    
	public void initBinder2(WebDataBinder binder) {    
		binder.setFieldDefaultPrefix("category.");    
	}   
	
	@RequestMapping("/getAllCategorys")
	public @ResponseBody List<Category> getAllCategorys(){
		List<Category> categorys = categoryService.getAllCategorys();
		return categorys;
	}
	
	@RequestMapping("/categoryList")
	public ModelAndView categoryList(HttpServletRequest request){
		
		ModelAndView modelAndView = new ModelAndView();
		int pageSize = 10;
		int pageCount = Integer.parseInt(request.getParameter("pageCount")==null?"1":request.getParameter("pageCount"));
		int total = 0;
		
		List<Category> categorys = categoryService.getCategoryList(pageSize, pageCount);
		total = categoryService.getCategoryCount();
		
		modelAndView.setViewName("categoryList");
		modelAndView.addObject("categorys",categorys);
		if(total==0){
			modelAndView.addObject("pageTotal",0);
			modelAndView.addObject("total",0);
			modelAndView.addObject("pageCount",pageCount);
		}else{
			modelAndView.addObject("pageTotal",total%pageSize==0?total/pageSize:total/pageSize+1);
			modelAndView.addObject("total",total);
			modelAndView.addObject("pageCount",pageCount);
		}
		return modelAndView;
	}
	
	@RequestMapping("/addCategoryBefore")
	public ModelAndView addCategoryBefore(){
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("addCategory");
		return modelAndView;
	}
	
	@RequestMapping("/addCategory")
	public ModelAndView addCategory(@ModelAttribute Category category,HttpServletRequest request){
		ModelAndView modelAndView = new ModelAndView();
		String name = category.getName();
		Integer maxamount  = category.getMaxamount();
		Integer minamount  = category.getMinamount();
		Integer sellMaxamount  = category.getSellMaxamount();
		Integer sellMinamount  = category.getSellMinamount();
		if(StringUtils.isEmpty(name) || maxamount == null|| minamount == null|| sellMaxamount == null|| sellMinamount == null){
			modelAndView.setViewName("addCategory");
			modelAndView.addObject("message", "请填写完整信息以后再提交！");
			return modelAndView;
		}
		Category tempCategory = categoryService.getCategoryByName(name);
		if(tempCategory != null){
			modelAndView.setViewName("addCategory");
			modelAndView.addObject("message", "商品类别名称已经存在，请修改以后再提交！");
			return modelAndView;
		}
		
		int count = categoryService.addCategory(category);
		
		if(count == 1) {
			int pageSize = 10;
			int pageCount = Integer.parseInt(request.getParameter("pageCount")==null?"1":request.getParameter("pageCount"));
			int total = 0;
			
			List<Category> categorys = categoryService.getCategoryList(pageSize, pageCount);
			total = categoryService.getCategoryCount();
			
			modelAndView.setViewName("categoryList");
			modelAndView.addObject("categorys",categorys);
			if(total==0){
				modelAndView.addObject("pageTotal",0);
				modelAndView.addObject("total",0);
				modelAndView.addObject("pageCount",pageCount);
			}else{
				modelAndView.addObject("pageTotal",total%pageSize==0?total/pageSize:total/pageSize+1);
				modelAndView.addObject("total",total);
				modelAndView.addObject("pageCount",pageCount);
			}
		}else{
			modelAndView.setViewName("addCategory");
			modelAndView.addObject("message", "添加失败，请刷新以后重试！");
		}
		return modelAndView;
	}
	
	@RequestMapping("/updateCategoryBefore")
	public ModelAndView updateCategoryBefore(@RequestParam("id") String id){
		
		ModelAndView modelAndView = new ModelAndView();
		Category tempCategory = categoryService.getCategoryById(Integer.valueOf(id));
		modelAndView.addObject("tempCategory", tempCategory);
		modelAndView.setViewName("updateCategory");
		return modelAndView;
	}
	
	@RequestMapping("/updateCategory")
	public ModelAndView updateCategory(@ModelAttribute Category category,HttpServletRequest request){
		
		ModelAndView modelAndView = new ModelAndView();
		String name = category.getName();
		Integer maxamount  = category.getMaxamount();
		Integer minamount  = category.getMinamount();
		if(StringUtils.isEmpty(name) || maxamount == null|| minamount == null){
			modelAndView.setViewName("updateCategory");
			modelAndView.addObject("tempCategory", category);
			modelAndView.addObject("message", "请填写完整信息以后再提交！");
			return modelAndView;
		}
		Category tempCategory = categoryService.getCategoryByName(name);
		if(tempCategory != null && !tempCategory.getId().equals(category.getId())) {
			modelAndView.setViewName("updateCategory");
			modelAndView.addObject("tempCategory", category);
			modelAndView.addObject("message", "商品类别名称已经存在，请修改以后再提交！");
			return modelAndView;
		} 
		int count = categoryService.updateCategory(category);
		if(count == 1) {
			int pageSize = 10;
			int pageCount = Integer.parseInt(request.getParameter("pageCount")==null?"1":request.getParameter("pageCount"));
			int total = 0;
			
			List<Category> categorys = categoryService.getCategoryList(pageSize, pageCount);
			total = categoryService.getCategoryCount();
			
			modelAndView.setViewName("categoryList");
			modelAndView.addObject("categorys",categorys);
			if(total==0){
				modelAndView.addObject("pageTotal",0);
				modelAndView.addObject("total",0);
				modelAndView.addObject("pageCount",pageCount);
			}else{
				modelAndView.addObject("pageTotal",total%pageSize==0?total/pageSize:total/pageSize+1);
				modelAndView.addObject("total",total);
				modelAndView.addObject("pageCount",pageCount);
			}
		}else{
			modelAndView.setViewName("updateCategory");
			modelAndView.addObject("tempCategory", category);
			modelAndView.addObject("message", "更新失败，请刷新以后重试！");
		}
		return modelAndView;
	}

	@RequestMapping("/deleteCategory")
	public @ResponseBody Map<String, String> deleteCategory(@RequestParam("id") String id,HttpServletRequest request){
		Map<String,String> result = new HashMap<String,String>();
		List<Commodity> commoditys = commodityService.getAllCommoditys();
		for(Commodity commodity:commoditys){
			if(commodity.getCategoryid()==Integer.valueOf(id)){
				result.put("result", "0");
				result.put("message", "删除失败，商品【"+commodity.getName()+"】使用了该类别！");
				return result;
			}
		}
		int count = categoryService.deleteCategory(Integer.valueOf(id));
		if(count >= 1) {
			result.put("result", "1");
		}else{
			result.put("result", "0");
			result.put("message", "删除失败，请刷新以后重试！");
		}
		return result;
	}
}
