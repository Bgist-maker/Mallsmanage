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

import com.bs.domain.Commodity;
import com.bs.domain.Unit;
import com.bs.service.CommodityService;
import com.bs.service.UnitService;

@Controller
public class UnitManageController {
	
	@Autowired
	private UnitService unitService;
	@Autowired
	private CommodityService commodityService;
	
	@InitBinder("unit")    
	public void initBinder2(WebDataBinder binder) {    
		binder.setFieldDefaultPrefix("unit.");    
	}   
	
	@RequestMapping("/getAllUnits")
	public @ResponseBody List<Unit> getAllUnits(){
		List<Unit> units = unitService.getAllUnits();
		return units;
	}
	
	@RequestMapping("/unitList")
	public ModelAndView unitList(HttpServletRequest request){
		
		ModelAndView modelAndView = new ModelAndView();
		int pageSize = 10;
		int pageCount = Integer.parseInt(request.getParameter("pageCount")==null?"1":request.getParameter("pageCount"));
		int total = 0;
		
		List<Unit> units =  unitService.getUnitList(pageSize, pageCount);
		total = unitService.getUnitCount();
		
		modelAndView.setViewName("unitList");
		modelAndView.addObject("units",units);
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
	
	@RequestMapping("/addUnitBefore")
	public ModelAndView addUnitBefore(){
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("addUnit");
		return modelAndView;
	}
	
	@RequestMapping("/addUnit")
	public ModelAndView addUnit(@ModelAttribute Unit unit,HttpServletRequest request){
		ModelAndView modelAndView = new ModelAndView();
		String name = unit.getName();
		if(StringUtils.isEmpty(name)){
			modelAndView.setViewName("addUnit");
			modelAndView.addObject("message", "请填写完整信息以后再提交！");
			return modelAndView;
		}
		Unit tempUnit = unitService.getUnitByName(name);
		if(tempUnit != null){
			modelAndView.setViewName("addUnit");
			modelAndView.addObject("message", "商品计量单位名称已经存在，请修改以后再提交！");
			return modelAndView;
		}
		
		int count = unitService.addUnit(unit);
		
		if(count == 1) {
			int pageSize = 10;
			int pageCount = Integer.parseInt(request.getParameter("pageCount")==null?"1":request.getParameter("pageCount"));
			int total = 0;
			
			List<Unit> units =  unitService.getUnitList(pageSize, pageCount);
			total = unitService.getUnitCount();
			
			modelAndView.setViewName("unitList");
			modelAndView.addObject("units",units);
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
	
	@RequestMapping("/updateUnitBefore")
	public ModelAndView updateUnitBefore(@RequestParam("id") String id){
		
		ModelAndView modelAndView = new ModelAndView();
		Unit tempUnit = unitService.getUnitById(Integer.valueOf(id));
		modelAndView.addObject("tempUnit", tempUnit);
		modelAndView.setViewName("updateUnit");
		return modelAndView;
	}
	
	@RequestMapping("/updateUnit")
	public ModelAndView updateUnit(@ModelAttribute Unit unit,HttpServletRequest request){
		
		ModelAndView modelAndView = new ModelAndView();
		String name = unit.getName();
		if(StringUtils.isEmpty(name)){
			modelAndView.setViewName("updateUnit");
			modelAndView.addObject("tempUnit", unit);
			modelAndView.addObject("message", "请填写完整信息以后再提交！");
			return modelAndView;
		}
		Unit tempUnit = unitService.getUnitByName(name);
		if(tempUnit != null){
			modelAndView.setViewName("addUnit");
			modelAndView.addObject("message", "商品计量单位名称已经存在，请修改以后再提交！");
			return modelAndView;
		}
		int count = unitService.updateUnit(unit);
		if(count == 1) {
			int pageSize = 10;
			int pageCount = Integer.parseInt(request.getParameter("pageCount")==null?"1":request.getParameter("pageCount"));
			int total = 0;
			
			List<Unit> units =  unitService.getUnitList(pageSize, pageCount);
			total = unitService.getUnitCount();
			
			modelAndView.setViewName("unitList");
			modelAndView.addObject("units",units);
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
			modelAndView.setViewName("updateUnit");
			modelAndView.addObject("tempUnit", unit);
			modelAndView.addObject("message", "更新失败，请刷新以后重试！");
		}
		return modelAndView;
	}

	@RequestMapping("/deleteUnit")
	public @ResponseBody Map<String, String> deleteUnit(@RequestParam("id") String id,HttpServletRequest request){
		Map<String,String> result = new HashMap<String,String>();
		List<Commodity> commoditys = commodityService.getAllCommoditys();
		for(Commodity commodity:commoditys){
			if(commodity.getUnitid()==Integer.valueOf(id)){
				result.put("result", "0");
				result.put("message", "删除失败，商品【"+commodity.getName()+"】使用了该单位！");
				return result;
			}
		}
		int count = unitService.deleteUnit(Integer.valueOf(id));
		if(count >= 1) {
			result.put("result", "1");
		}else{
			result.put("result", "0");
			result.put("message", "删除失败，请刷新以后重试！");
		}
		return result;
	}
}
