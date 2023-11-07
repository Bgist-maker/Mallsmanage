package com.bs.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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
import com.bs.service.CategoryService;
import com.bs.service.CommodityService;
import com.bs.service.OperationService;
import com.bs.service.SellerService;
import com.bs.util.CommonUtils;

@Controller
public class SellerAnayController {
	
	@Autowired
	private SellerService sellerService;
	
	@RequestMapping("/selleranalysis")
	public ModelAndView stocka(HttpServletRequest request){
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("selleranalysis");
		return modelAndView;
	}
//	List<Category> categorys = categoryService.getAllCategorys();
	
	@RequestMapping("/numAnalyz")
	public @ResponseBody EchartBean stockaAnalyz(HttpServletRequest request){
		EchartBean echartBean = new EchartBean();
		List<Map<String, Object>> citys = sellerService.getSellerCityCount();
//		List<Commodity> commoditys = commodityService.getAllCommoditys();
//		Map<Integer,Category> mapForCategory = new LinkedHashMap<Integer,Category>();
		List<String> xAxis = new LinkedList<String>();
		List<String> series = new LinkedList<String>();
		int flag=1;

		for(Map<String, Object> city:citys){
		//	mapForCategory.put(category.getId(), category);
			Set keySet = city.keySet(); // key的set集合
			Iterator it = keySet.iterator();
			
			while(it.hasNext()){
				Object k = it.next(); // key
				Object v = city.get(k);	//value	
				if(flag==1) {
				 xAxis.add(String.valueOf(v));
				 flag=0;}else {
				  flag=1;	 
				  series.add(String.valueOf(v));}
			}
			
		}

		echartBean.setSeries(series);
		echartBean.setxAxis(xAxis);
		return echartBean;
	}

}
