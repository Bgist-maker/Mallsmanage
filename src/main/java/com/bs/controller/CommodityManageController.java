package com.bs.controller;

import java.net.URLDecoder;
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
import com.bs.domain.Operation;
import com.bs.service.CategoryService;
import com.bs.service.CommodityService;
import com.bs.service.OperationService;
import com.bs.util.CommonUtils;

@Controller
public class CommodityManageController {
	
	@Autowired
	private CommodityService commodityService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private OperationService operationService;
	
	@InitBinder("commodity")    
	public void initBinder2(WebDataBinder binder) {    
		binder.setFieldDefaultPrefix("commodity.");    
	}   
	@RequestMapping("/getAllCommoditys")
	public @ResponseBody List<Commodity> getAllCommoditys(){
		List<Commodity> commoditys = commodityService.getAllCommoditys();
		return commoditys;
	}
	
	@RequestMapping("/commodityList")
	public ModelAndView commodityList(HttpServletRequest request){
		
		ModelAndView modelAndView = new ModelAndView();
		int pageSize = 10;
		int pageCount = Integer.parseInt(request.getParameter("pageCount")==null?"1":request.getParameter("pageCount"));
		int total = 0;
		
		List<Commodity> commoditys = commodityService.getCommodityList(pageSize, pageCount);
		total = commodityService.getCommodityCount();
		
		modelAndView.setViewName("commodityList");
		modelAndView.addObject("commoditys",commoditys);
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
	@RequestMapping("/commodityListForSearch")
	public ModelAndView commodityListForSearch(HttpServletRequest request){
		
		ModelAndView modelAndView = new ModelAndView();
		int pageSize = 10;
		int pageCount = Integer.parseInt(request.getParameter("pageCount")==null?"1":request.getParameter("pageCount"));
		int total = 0;
		String commodityNameSearchValue = request.getParameter("commodityName");
		String categoryNameSearchValue = request.getParameter("categoryName");
		String minSearchValue = request.getParameter("min");
		String maxSearchValue = request.getParameter("max");
		String commodityName = "";
		String categoryName = "";
		String min = "";
		String max = "";
		try{
			commodityName = commodityNameSearchValue==null?"":URLDecoder.decode(commodityNameSearchValue,"utf-8");
			categoryName = categoryNameSearchValue==null?"":URLDecoder.decode(categoryNameSearchValue,"utf-8");
			min = minSearchValue==null?"":URLDecoder.decode(minSearchValue,"utf-8");
			max = maxSearchValue==null?"":URLDecoder.decode(maxSearchValue,"utf-8");
		}catch(Exception e){
			e.printStackTrace();
		}
		Map<String,String> map = new HashMap<String,String>();
		if(!StringUtils.isEmpty(commodityName)){
			Commodity commodity = commodityService.getCommodityByname(commodityName);
			if(commodity != null){
				map.put("commodityid", String.valueOf(commodity.getId()));
			}else{
				map.put("commodityid", "-1");
			}
		}
		if(!StringUtils.isEmpty(categoryName)){
			Category category = categoryService.getCategoryByName(categoryName);
			if(category != null){
				map.put("categoryid", String.valueOf(category.getId()));
			}else{
				map.put("categoryid", "-1");
			}
		}
		if(!StringUtils.isEmpty(min)){
			if(!CommonUtils.isValidNum(min)){
				map.put("min", min);
			}
		}
		if(!StringUtils.isEmpty(max)){
			if(!CommonUtils.isValidNum(max)){
				map.put("max", max);
			}
		}
		map.put("pageIndex", String.valueOf((pageCount-1)*pageSize));
		map.put("pageSize", String.valueOf(pageSize));
		List<Commodity> commoditys = commodityService.getCommodityListForSearch(map);
		total = commodityService.getCommodityCountForSearch(map);
		
		modelAndView.setViewName("commodityListForSearch");
		modelAndView.addObject("commoditys",commoditys);
		modelAndView.addObject("commodityName",StringUtils.isEmpty(commodityName)?"商品名":commodityName);
		modelAndView.addObject("categoryName",StringUtils.isEmpty(categoryName)?"类别":categoryName);
		modelAndView.addObject("min",StringUtils.isEmpty(min)?"最小库存量":min);
		modelAndView.addObject("max",StringUtils.isEmpty(max)?"最大库存量":max);
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
	
	@RequestMapping("/addCommodityBefore")
	public ModelAndView addCommodityBefore(){
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("addCommodity");
		return modelAndView;
	}
	
	@RequestMapping("/addCommodity")
	public ModelAndView addCommodity(@ModelAttribute Commodity commodity,HttpServletRequest request){
		ModelAndView modelAndView = new ModelAndView();
		String name = commodity.getName();
		Integer stockamount = commodity.getStockamount();
		String brand = commodity.getBrand();
		Double buy = commodity.getBuy();
		Double sell = commodity.getSell();
		
		if(StringUtils.isEmpty(name) || stockamount == null || StringUtils.isEmpty(brand) || buy == null|| sell == null){
			modelAndView.setViewName("addCommodity");
			modelAndView.addObject("message", "请填写完整信息以后再提交！");
			return modelAndView;
		}
		
		int count = commodityService.addCommodity(commodity);//与service接口是否连接成功
		
		if(count == 1) {
			int pageSize = 10;
			int pageCount = Integer.parseInt(request.getParameter("pageCount")==null?"1":request.getParameter("pageCount"));
			int total = 0;
			
			List<Commodity> commoditys = commodityService.getCommodityList(pageSize, pageCount);
			total = commodityService.getCommodityCount();
			
			modelAndView.setViewName("commodityList");
			modelAndView.addObject("commoditys",commoditys);
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
			modelAndView.setViewName("addCommodity");
			modelAndView.addObject("message", "添加失败，请刷新以后重试！");
		}
		return modelAndView;
	}
	
	@RequestMapping("/updateCommodityBefore")
	public ModelAndView updateCommodityBefore(@RequestParam("id") String id){
		
		ModelAndView modelAndView = new ModelAndView();
		Commodity tempCommodity = commodityService.getCommodityById(Integer.valueOf(id));
		modelAndView.addObject("tempCommodity", tempCommodity);
		modelAndView.setViewName("updateCommodity");
		return modelAndView;
	}
	
	@RequestMapping("/updateCommodity")
	public ModelAndView updateCommodity(@ModelAttribute Commodity commodity,HttpServletRequest request){
		
		ModelAndView modelAndView = new ModelAndView();
		String name = commodity.getName();
		Integer stockamount = commodity.getStockamount();
		String brand = commodity.getBrand();
		Double buy = commodity.getBuy();
		Double sell = commodity.getSell();
		
		if(StringUtils.isEmpty(name) || stockamount == null || StringUtils.isEmpty(brand) || buy == null|| sell == null){
			modelAndView.setViewName("updateCommodity");
			modelAndView.addObject("tempCommodity", commodity);
			modelAndView.addObject("message", "请填写完整信息以后再提交！");
			return modelAndView;
		}
		int count = commodityService.updateCommodity(commodity);
		if(count == 1) {
			int pageSize = 10;
			int pageCount = Integer.parseInt(request.getParameter("pageCount")==null?"1":request.getParameter("pageCount"));
			int total = 0;
			
			List<Commodity> commoditys = commodityService.getCommodityList(pageSize, pageCount);
			total = commodityService.getCommodityCount();
			
			modelAndView.setViewName("commodityList");
			modelAndView.addObject("commoditys",commoditys);
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
			modelAndView.setViewName("updateCommodity");
			modelAndView.addObject("tempCommodity", commodity);
			modelAndView.addObject("message", "更新失败，请刷新以后重试！");
		}
		return modelAndView;
	}

	@RequestMapping("/deleteCommodity")
	public @ResponseBody Map<String, String> deleteCommodity(@RequestParam("id") String id,HttpServletRequest request){
		Map<String,String> result = new HashMap<String,String>();
		List<Operation> Operations = operationService.getAllOperationList();
		for(Operation operation:Operations){
			if(operation.getCommodityid()==Integer.valueOf(id)){
				result.put("result", "0");
				result.put("message", "删除失败，出库记录【"+operation.getOrderno()+"】包含该商品");
				return result;
			}
		}
		int count = commodityService.deleteCommodity(Integer.valueOf(id));
		if(count >= 1) {
			result.put("result", "1");
		}else{
			result.put("result", "0");
			result.put("message", "删除失败，请刷新以后重试！");
		}
		return result;
	}
}
