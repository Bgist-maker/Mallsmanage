package com.bs.controller;

import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import com.bs.domain.Operation;
import com.bs.domain.User;
import com.bs.service.CommodityService;
import com.bs.service.OperationService;
import com.bs.service.UserService;
import com.bs.util.CommonUtils;

@Controller
public class OperationManageController {
	
	@Autowired
	private OperationService operationService;
	@Autowired
	private CommodityService commodityService;
	@Autowired
	private UserService userService;
	
	@InitBinder("operation")    
	public void initBinder2(WebDataBinder binder) {    
		binder.setFieldDefaultPrefix("operation.");    
	}   
	
	@RequestMapping("/operationList")//请求xml中的路径
	public ModelAndView operationList(HttpServletRequest request){
		
		ModelAndView modelAndView = new ModelAndView();
		int pageSize = 10;
		int pageCount = Integer.parseInt(request.getParameter("pageCount")==null?"1":request.getParameter("pageCount"));
		int total = 0;
		Map<String,String> map = new HashMap<String,String>();
		String orderNoSearchValue = request.getParameter("orderNo");
		String commodityNameSearchValue = request.getParameter("commodityName");
		String usernoSearchValue = request.getParameter("userno");
		String beginDate = request.getParameter("beginDate");
		String endDate = request.getParameter("endDate");
		
		String orderNo = "";
		String commodityName = "";
		String userno = "";
		try{
			orderNo = orderNoSearchValue==null?"":URLDecoder.decode(orderNoSearchValue,"utf-8");
			commodityName = commodityNameSearchValue==null?"":URLDecoder.decode(commodityNameSearchValue,"utf-8");
			userno = usernoSearchValue==null?"":URLDecoder.decode(usernoSearchValue,"utf-8");
		}catch(Exception e){
			e.printStackTrace();
		}
		if(!StringUtils.isEmpty(orderNo)){
			map.put("orderno", orderNo);
		}
		if(!StringUtils.isEmpty(commodityName)){
			Commodity commodity = commodityService.getCommodityByname(commodityName);
			if(commodity != null){
				map.put("commodityid", String.valueOf(commodity.getId()));
			}else{
				map.put("commodityid", "-1");
			}
		}
		if(!StringUtils.isEmpty(userno)){
			User user = userService.getUserByUserNo(userno);
			if(user!=null){
				map.put("userid", String.valueOf(user.getId()));
			}else{
				map.put("userid", "-1");
			}
		}
		if(!StringUtils.isEmpty(beginDate)){
			map.put("beginDate", beginDate+ " 00:00:00");
		}
		if(!StringUtils.isEmpty(endDate)){
			map.put("endDate", endDate+ " 24:00:00");
		}
		List<Operation> operations =  operationService.getOperationList(pageSize, pageCount,map);
		total = operationService.getOperationCount(map);
		
		modelAndView.setViewName("operationList");
		modelAndView.addObject("operations",operations);
		modelAndView.addObject("orderNo",StringUtils.isEmpty(orderNo)?"单号":orderNo);
		modelAndView.addObject("commodityName",StringUtils.isEmpty(commodityName)?"商品名":commodityName);
		modelAndView.addObject("userno",StringUtils.isEmpty(userno)?"员工工号":userno);
		modelAndView.addObject("beginDate",StringUtils.isEmpty(beginDate)?"":beginDate);
		modelAndView.addObject("endDate",StringUtils.isEmpty(endDate)?"":endDate);
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
	
	@RequestMapping("/addOperationBefore")
	public ModelAndView addOperationBefore(){
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("addOperation");
		return modelAndView;
	}
	
	@RequestMapping("/addOperation")
	public ModelAndView addOperation(@ModelAttribute Operation operation,HttpServletRequest request){
		HttpSession session = request.getSession(true);
		
		ModelAndView modelAndView = new ModelAndView();
		Integer amount = operation.getAmount();
		if(amount == null){
			modelAndView.setViewName("addOperation");
			modelAndView.addObject("message", "请填写完整信息以后再提交！");
			return modelAndView;
		}
		Commodity commodity = commodityService.getCommodityById(operation.getCommodityid());
		int stockamount = commodity.getStockamount();
		int operatetype =  operation.getOperatetype();
		if(operatetype==1){
			if(stockamount<amount){
				modelAndView.setViewName("addOperation");
				modelAndView.addObject("message", "库存数量不足！");
				return modelAndView;
			}
		}
		User user = (User)session.getAttribute("user");
		operation.setOperatetime(new Date());
		operation.setOrderno(CommonUtils.getUUID());
		operation.setUserid(user.getId());
		
		
		int count = operationService.addOperation(operation);
		
		if(count == 1) {
			if(operatetype==0){
				commodity.setStockamount(stockamount+amount);
			}else{
				commodity.setStockamount(stockamount-amount);
			}
			commodityService.updateCommodity(commodity);
			int pageSize = 10;
			int pageCount = Integer.parseInt(request.getParameter("pageCount")==null?"1":request.getParameter("pageCount"));
			int total = 0;
			Map<String,String> map = new HashMap<String,String>();
			List<Operation> operations =  operationService.getOperationList(pageSize, pageCount,map);
			total = operationService.getOperationCount(map);
			
			modelAndView.setViewName("operationList");
			modelAndView.addObject("operations",operations);
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
	
	@RequestMapping("/updateOperationBefore")
	public ModelAndView updateOperationBefore(@RequestParam("id") String id){
		
		ModelAndView modelAndView = new ModelAndView();
		Operation tempOperation = operationService.getOperationById(Integer.valueOf(id));
		modelAndView.addObject("tempOperation", tempOperation);
		modelAndView.setViewName("updateOperation");
		return modelAndView;
	}
	
	@RequestMapping("/updateOperation")
	public ModelAndView updateOperation(@ModelAttribute Operation operation,HttpServletRequest request){
		
		ModelAndView modelAndView = new ModelAndView();
		Integer amount = operation.getAmount();
		if(amount == null){
			modelAndView.setViewName("updateOperation");
			modelAndView.addObject("tempOperation", operation);
			modelAndView.addObject("message", "请填写完整信息以后再提交！");
			return modelAndView;
		}
		Operation tempOperation = operationService.getOperationById(operation.getId());
		Commodity oldCommodity = tempOperation.getCommodity();
		int oldAmount = tempOperation.getAmount();
		int oldOperatetype = tempOperation.getOperatetype();
		int oldStockamount = oldCommodity.getStockamount();
		
		if(oldOperatetype ==0){
			oldCommodity.setStockamount(oldStockamount - oldAmount);
		}else{
			oldCommodity.setStockamount(oldStockamount + oldAmount);
		}
		commodityService.updateCommodity(oldCommodity);
		
		int commodityid = operation.getCommodityid();
		int operatetype = operation.getOperatetype();
		Commodity commodity = commodityService.getCommodityById(commodityid);
		int stockamount = commodity.getStockamount();
		if(operatetype==1){
			if(stockamount <amount){
				modelAndView.setViewName("updateOperation");
				modelAndView.addObject("tempOperation", operation);
				modelAndView.addObject("message", "库存数量不足！");
				return modelAndView;
			}
		}
		
		int count = operationService.updateOperation(operation);
		
		if(count == 1) {
			if(operatetype ==0){
				commodity.setStockamount(stockamount + amount);
			}else{
				commodity.setStockamount(stockamount - amount);
			}
			commodityService.updateCommodity(commodity);
			int pageSize = 10;
			int pageCount = Integer.parseInt(request.getParameter("pageCount")==null?"1":request.getParameter("pageCount"));
			int total = 0;
			
			Map<String,String> map = new HashMap<String,String>();
			List<Operation> operations =  operationService.getOperationList(pageSize, pageCount,map);
			total = operationService.getOperationCount(map);
			
			modelAndView.setViewName("operationList");
			modelAndView.addObject("operations",operations);
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
			modelAndView.setViewName("updateOperation");
			modelAndView.addObject("tempOperation", operation);
			modelAndView.addObject("message", "更新失败，请刷新以后重试！");
		}
		return modelAndView;
	}

	@RequestMapping("/deleteOperation")
	public @ResponseBody Map<String, String> deleteOperation(@RequestParam("id") String id,HttpServletRequest request){
		Map<String,String> result = new HashMap<String,String>();
		
		Operation tempOperation = operationService.getOperationById(Integer.valueOf(id));
		Commodity oldCommodity = tempOperation.getCommodity();
		int oldAmount = tempOperation.getAmount();
		int oldOperatetype = tempOperation.getOperatetype();
		int oldStockamount = oldCommodity.getStockamount();
		
		if(oldOperatetype ==0){
			oldCommodity.setStockamount(oldStockamount - oldAmount);
		}else{
			oldCommodity.setStockamount(oldStockamount + oldAmount);
		}
		
		
		int count = operationService.deleteOperation(Integer.valueOf(id));
		if(count >= 1) {
			commodityService.updateCommodity(oldCommodity);
			result.put("result", "1");
		}else{
			result.put("result", "0");
			result.put("message", "删除失败，请刷新以后重试！");
		}
		return result;
	}
}
