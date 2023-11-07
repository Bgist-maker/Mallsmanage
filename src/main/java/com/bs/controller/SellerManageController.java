package com.bs.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
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
import com.bs.domain.Seller;
import com.bs.service.SellerService;

@Controller
public class SellerManageController {
	
	@Autowired
	private SellerService sellerService;
	
	@InitBinder  
	public void initBinder(WebDataBinder binder) {  
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
		dateFormat.setLenient(false);  
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));   //true:允许输入空值，false:不能为空值
	}
	@InitBinder("seller")    
	public void initBinder2(WebDataBinder binder) {    
		binder.setFieldDefaultPrefix("seller.");    
	}   
	
	@RequestMapping("/getAllSellers")
	public @ResponseBody List<Seller> getAllSellers(){
		List<Seller> sellers = sellerService.getAllSellers();
		return sellers;
	}
	
	@RequestMapping("sellerList")
	public ModelAndView sellerList(HttpServletRequest req){
		
		ModelAndView modelAndView = new ModelAndView();
		int pageSize = 10;
		int pageCount = Integer.parseInt(req.getParameter("pageCount")==null?"1":req.getParameter("pageCount"));
		int total = 0;
		
		List<Seller> sellers = sellerService.getSellerList(pageSize, pageCount);
		total = sellerService.getSellerCount();
		
		modelAndView.setViewName("sellerList");
		modelAndView.addObject("sellers",sellers);
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
	
	@RequestMapping("/addSellerBefore")
	public ModelAndView addSellerBefore(){
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("addSeller");
		return modelAndView;
	}
	
	@RequestMapping("/addSeller")
	public ModelAndView addSeller(@ModelAttribute Seller seller,HttpServletRequest req){
		ModelAndView modelAndView = new ModelAndView();
		String username = seller.getUsername();
		String company = seller.getCompany();
		Date birthday = seller.getBirthday();
		String phone = seller.getPhone();
		String email = seller.getEmail();
		String city = seller.getCity();
		String address = seller.getAddress();
		Integer isimportant = seller.getIsimportant();
		Integer userid = seller.getUserid();
		if(StringUtils.isEmpty(username) || StringUtils.isEmpty(company)|| StringUtils.isEmpty(phone)
				|| StringUtils.isEmpty(email)|| StringUtils.isEmpty(city)|| StringUtils.isEmpty(address) || birthday==null||(isimportant ==1 && userid == null)){
			modelAndView.setViewName("addSeller");
			modelAndView.addObject("message", "请填写完整信息以后再提交！");
			return modelAndView;
		}
		
		int count = sellerService.addSeller(seller);
		
		if(count == 1) {
			int pageSize = 10;
			int pageCount = Integer.parseInt(req.getParameter("pageCount")==null?"1":req.getParameter("pageCount"));
			int total = 0;
			
			List<Seller> sellers = sellerService.getSellerList(pageSize, pageCount);
			total = sellerService.getSellerCount();
			
			modelAndView.setViewName("sellerList");
			modelAndView.addObject("sellers",sellers);
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
	
	@RequestMapping("/updateSellerBefore")
	public ModelAndView updateSellerBefore(@RequestParam("id") String id){
		
		ModelAndView modelAndView = new ModelAndView();
		Seller tempSeller = sellerService.getSellerById(Integer.valueOf(id));
		modelAndView.addObject("tempSeller", tempSeller);
		modelAndView.setViewName("updateSeller");
		return modelAndView;
	}
	
	@RequestMapping("/updateSeller")
	public ModelAndView updateSeller(@ModelAttribute Seller seller,HttpServletRequest req) throws ParseException{
		
		ModelAndView modelAndView = new ModelAndView();
		String username = seller.getUsername();
		String company = seller.getCompany();
		Date birthday = seller.getBirthday();
		String phone = seller.getPhone();
		String email = seller.getEmail();
		String city = seller.getCity();
		String address = seller.getAddress();
		Integer isimportant = seller.getIsimportant();
		Integer userid = seller.getUserid();
		if(StringUtils.isEmpty(username) || StringUtils.isEmpty(company)|| StringUtils.isEmpty(phone)
				|| StringUtils.isEmpty(email)|| StringUtils.isEmpty(city)|| StringUtils.isEmpty(address) || birthday==null||(isimportant ==1 && userid == null)){
			modelAndView.setViewName("updateSeller");
			modelAndView.addObject("tempSeller", seller);
			modelAndView.addObject("message", "请填写完整信息以后再提交！");
			return modelAndView;
		}
		int count = sellerService.updateSeller(seller);
		if(count == 1) {
			int pageSize = 10;
			int pageCount = Integer.parseInt(req.getParameter("pageCount")==null?"1":req.getParameter("pageCount"));
			int total = 0;
			
			List<Seller> sellers = sellerService.getSellerList(pageSize, pageCount);
			total = sellerService.getSellerCount();
			
			modelAndView.setViewName("sellerList");
			modelAndView.addObject("sellers",sellers);
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
			modelAndView.setViewName("updateSeller");
			modelAndView.addObject("tempSeller", seller);
			modelAndView.addObject("message", "更新失败，请刷新以后重试！");
		}
		return modelAndView;
	}

	@RequestMapping("/deleteSeller")
	public @ResponseBody Map<String, String> deleteSeller(@RequestParam("id") String id,HttpServletRequest request){
		Map<String,String> result = new HashMap<String,String>();
		List<Seller> sellers = sellerService.getAllSellers();
		for(Seller seller:sellers){
			if(seller.getId()==Integer.valueOf(id)){
				if(seller.getIsimportant()==1) {
					result.put("result", "0");
					result.put("message", "删除失败，此客户为重要客户，若删除请先修改为非重要！");
					return result;
				}
			}
		}
		int count = sellerService.deleteSeller(Integer.valueOf(id));
		if(count >= 1) {
			result.put("result", "1");
		}else{
			result.put("result", "0");
			result.put("message", "删除失败，请刷新以后重试！");
		}
		return result;
	}
}
