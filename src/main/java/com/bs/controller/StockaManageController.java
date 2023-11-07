package com.bs.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bs.bean.EchartBean;
import com.bs.domain.Category;
import com.bs.domain.Commodity;
import com.bs.domain.Operation;
import com.bs.service.CategoryService;
import com.bs.service.CommodityService;
import com.bs.service.OperationService;
import com.bs.util.CommonUtils;

@Controller
public class StockaManageController {
	
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private CommodityService commodityService;
	@Autowired
	private OperationService operationService;
	
	@RequestMapping("/stocka")
	public ModelAndView stocka(HttpServletRequest request){
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("stocka");
		return modelAndView;
	}
	
	@RequestMapping("/stockaAnalyz")//库存分析
	public @ResponseBody EchartBean stockaAnalyz(HttpServletRequest request){
		EchartBean echartBean = new EchartBean();
		List<Category> categorys = categoryService.getAllCategorys();
		List<Commodity> commoditys = commodityService.getAllCommoditys();
		Map<Integer,Category> mapForCategory = new LinkedHashMap<Integer,Category>();
		List<String> xAxis = new LinkedList<String>();
		List<String> series = new LinkedList<String>();
		for(Category category:categorys){
			mapForCategory.put(category.getId(), category);
			xAxis.add(category.getName());
		}
		for(Entry<Integer, Category> entry: mapForCategory.entrySet()){
			int i = 0;
			for(Commodity commodity:commoditys){
				if(commodity.getCategoryid() == entry.getKey()){
					int stockamount = commodity.getStockamount();
					i= i+stockamount;
				}
			}
			series.add(String.valueOf(i));
		}
		echartBean.setSeries(series);
		echartBean.setxAxis(xAxis);
		return echartBean;
	}
	@RequestMapping("/categoryAnalyz")//种类分析
	public @ResponseBody Map<String,String> categoryAnalyz(HttpServletRequest request) throws Exception{
		Map<String,String> result = new HashMap<String,String>();
		String name =request.getParameter("name");
		StringBuffer message =new StringBuffer();
		Category category = categoryService.getCategoryByName(name);
		Set<String> set = new HashSet<String>();
		List<Commodity> commoditys = commodityService.getAllCommoditysByCategoryid(category.getId());
		for(Commodity commodity:commoditys){
			int stockamount = commodity.getStockamount();
			if(stockamount<category.getMinamount()){
				set.add(commodity.getName()+"("+commodity.getBrand()+")");
				message.append(commodity.getName()+"("+commodity.getBrand()+"):缺货");
				message.append("<br/>");
			}
			if(stockamount>category.getMaxamount()){
				set.add(commodity.getName()+"("+commodity.getBrand()+")");
				message.append(commodity.getName()+"("+commodity.getBrand()+"):积压");
				message.append("<br/>");
			}
		}
		SimpleDateFormat  simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Map<String,String> map = new HashMap<String,String>();
		map.put("beginDate", simpleDateFormat.format(CommonUtils.getDateBefore(new Date(),5))+ " 00:00:00");
		map.put("endDate", simpleDateFormat.format(new Date())+ " 24:00:00");
		int size = operationService.getOperationCount(map);
		if(size==0){
			size =5;
		}
		List<Operation> operations =  operationService.getOperationList(size, 1,map);
		Map<String,Integer> messageMap = new HashMap<String,Integer>();
		for(Operation operation:operations){
			
			Commodity tempCommodity = operation.getCommodity();
			if(tempCommodity==null || tempCommodity.getCategoryid() != category.getId()){
				continue;
			}
			if(operation.getOperatetype()==0){
				continue;
			}
			String key = tempCommodity.getName()+"("+tempCommodity.getBrand()+")";
			if(messageMap.containsKey(key)){
				int amount = messageMap.get(key);
				messageMap.put(key, operation.getAmount()+amount);
			}else{
				messageMap.put(key, operation.getAmount());
			}
		}
		for(Entry<String,Integer> entry:messageMap.entrySet()){
			String key = entry.getKey();
			int value = entry.getValue();
			if(value>(category.getSellMaxamount()*5)){
				if(set.add(key)){
					message.append(key+":紧俏");
					message.append("<br/>");
				}
				
			}
			if(value<(category.getSellMinamount()*5)){
				if(set.add(key)){
					message.append(key+":滞销");
					message.append("<br/>");
				}
			}
		}
		if(message.length()==0){
			result.put("message", "没有缺货以及积压");
		}else{
			result.put("message", message.toString());
		}
		return result;
	}
}
