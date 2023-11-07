package com.bs.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

import com.bs.domain.Buyer;
import com.bs.domain.Commodity;
import com.bs.domain.Seller;
import com.bs.service.BuyerService;
import com.bs.service.CommodityService;

@Controller
public class BuyerManageController {
	
	@Autowired
	private BuyerService buyerService;
	@Autowired
	private CommodityService commodityService;
	
	@InitBinder("buyer")    
	public void initBinder2(WebDataBinder binder) {    
		binder.setFieldDefaultPrefix("buyer.");    
	}   
	
	@RequestMapping("/getAllBuyers")
	public @ResponseBody List<Buyer> getAllBuyers(){
		List<Buyer> buyers = buyerService.getAllBuyers();
		return buyers;
	}
	
	@RequestMapping("buyerList")
	public ModelAndView buyerList(HttpServletRequest req) throws ParseException{
		
		ModelAndView modelAndView = new ModelAndView();
		int pageSize = 10;
		int pageCount = Integer.parseInt(req.getParameter("pageCount")==null?"1":req.getParameter("pageCount"));
		int total = 0;
		
		List<Buyer> buyers = buyerService.getBuyerList(pageSize, pageCount);
		total = buyerService.getBuyerCount();
		
		modelAndView.setViewName("buyerList");
		modelAndView.addObject("buyers",buyers);
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
	
	@RequestMapping("/addBuyerBefore")
	public ModelAndView addBuyerBefore(){
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("addBuyer");
		return modelAndView;
	}
	
	@RequestMapping("/addBuyer")
	public ModelAndView addBuyer(@ModelAttribute Buyer buyer,HttpServletRequest req){
		ModelAndView modelAndView = new ModelAndView();
		String name = buyer.getName();
		String leader = buyer.getLeader();
		String phone = buyer.getPhone();
		String email = buyer.getEmail();
		String city = buyer.getCity();
		String address = buyer.getAddress();
		Integer isimportant = buyer.getIsimportant();
		Integer userid = buyer.getUserid();
		if(StringUtils.isEmpty(name) || StringUtils.isEmpty(leader)|| StringUtils.isEmpty(phone)
				|| StringUtils.isEmpty(email)|| StringUtils.isEmpty(city)|| StringUtils.isEmpty(address) ||(isimportant ==1 && userid == null)){
			modelAndView.setViewName("addBuyer");
			modelAndView.addObject("message", "请填写完整信息以后再提交！");
			return modelAndView;
		}
		if(phone.length()!=11 || !email.contains("@")){
			modelAndView.setViewName("addBuyer");
			modelAndView.addObject("message", "手机或邮箱输入有误！");
			return modelAndView;
		}
		Buyer tempBuyer = buyerService.getBuyerByName(name);
		if(tempBuyer != null){
			modelAndView.setViewName("addBuyer");
			modelAndView.addObject("message", "供应商已经存在，请修改以后再提交！");
			return modelAndView;
		}
		int count = buyerService.addBuyer(buyer);
		
		if(count == 1) {
			int pageSize = 10;
			int pageCount = Integer.parseInt(req.getParameter("pageCount")==null?"1":req.getParameter("pageCount"));
			int total = 0;
			
			List<Buyer> buyers = buyerService.getBuyerList(pageSize, pageCount);
			total = buyerService.getBuyerCount();
			
			modelAndView.setViewName("buyerList");
			modelAndView.addObject("buyers",buyers);
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
			modelAndView.setViewName("addUser");
			modelAndView.addObject("message", "添加失败，请刷新以后重试！");
		}
		return modelAndView;
	}
	
	@RequestMapping("/updateBuyerBefore")
	public ModelAndView updateBuyerBefore(@RequestParam("id") String id){
		
		ModelAndView modelAndView = new ModelAndView();
		Buyer tempBuyer = buyerService.getBuyerById(Integer.valueOf(id));
		modelAndView.addObject("tempBuyer", tempBuyer);
		modelAndView.setViewName("updateBuyer");
		return modelAndView;
	}
	
	@RequestMapping("/updateBuyer")
	public ModelAndView updateBuyer(@ModelAttribute Buyer buyer,HttpServletRequest req){
		
		ModelAndView modelAndView = new ModelAndView();
		String name = buyer.getName();
		String leader = buyer.getLeader();
		String phone = buyer.getPhone();
		String email = buyer.getEmail();
		String city = buyer.getCity();
		String address = buyer.getAddress();
		Integer isimportant = buyer.getIsimportant();
		Integer userid = buyer.getUserid();
	
		if(StringUtils.isEmpty(name) || StringUtils.isEmpty(leader)|| StringUtils.isEmpty(phone)
				|| StringUtils.isEmpty(email)|| StringUtils.isEmpty(city)|| StringUtils.isEmpty(address) ||(isimportant ==1 && userid == null)){
			modelAndView.setViewName("updateBuyer");
			modelAndView.addObject("message", "请填写完整信息以后再提交！");
			modelAndView.addObject("tempBuyer", buyer);
			return modelAndView;
		}
		if(phone.length()!=11 || !email.contains("@")){
			modelAndView.setViewName("updateBuyer");
			modelAndView.addObject("tempBuyer", buyer);
			modelAndView.addObject("message", "请检查填写信息以后再提交！");
			return modelAndView;
		}
		Buyer tempBuyer = buyerService.getBuyerByName(name);
		if(tempBuyer != null && tempBuyer.getId()!=buyer.getId()){
			modelAndView.setViewName("updateBuyer");
			modelAndView.addObject("tempBuyer", buyer);
			modelAndView.addObject("message", "供应商已经存在，请修改以后再提交！");
			return modelAndView;
		}
		int count = buyerService.updateBuyer(buyer);
		if(count == 1) {
			int pageSize = 10;
			int pageCount = Integer.parseInt(req.getParameter("pageCount")==null?"1":req.getParameter("pageCount"));
			int total = 0;
			
			List<Buyer> buyers = buyerService.getBuyerList(pageSize, pageCount);
			total = buyerService.getBuyerCount();
			
			modelAndView.setViewName("buyerList");
			modelAndView.addObject("buyers",buyers);
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
			modelAndView.setViewName("updateBuyer");
			modelAndView.addObject("tempBuyer", buyer);
			modelAndView.addObject("message", "更新失败，请刷新以后重试！");
		}
		return modelAndView;
	}

	@RequestMapping("/deleteBuyer")
	public @ResponseBody Map<String, String> deleteBuyer(@RequestParam("id") String id,HttpServletRequest request){
		Map<String,String> result = new HashMap<String,String>();
		List<Commodity> commoditys = commodityService.getAllCommoditys();
		for(Commodity commodity:commoditys){
			if(commodity.getBuyerid()==Integer.valueOf(id)){
				result.put("result", "0");
				result.put("message", "删除失败，商品【"+commodity.getName()+"】来自该供应商！");
				return result;
			}
		}
		List<Buyer> buyers = buyerService.getAllBuyers();
		for(Buyer buyer:buyers){
			if(buyer.getId()==Integer.valueOf(id)){
				if(buyer.getIsimportant()==1) {
					result.put("result", "0");
					result.put("message", "删除失败，此供应商为重要供应商，若删除请先修改为非重要！");
					return result;
				}
			}
		}
		int count = buyerService.deleteBuyer(Integer.valueOf(id));
		if(count >= 1) {
			result.put("result", "1");
		}else{
			result.put("result", "0");
			result.put("message", "删除失败，请刷新以后重试！");
		}
		return result;
	}
}
