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

import com.bs.domain.User;
import com.bs.service.UserService;
import com.bs.util.CommonUtils;

@Controller
public class UserManageController {
	
	@Autowired
	private UserService userService;
	
	@InitBinder("user")    
	public void initBinder2(WebDataBinder binder) {    
		binder.setFieldDefaultPrefix("user.");    
	}   
	
	@RequestMapping("/getAllUsers")
	public @ResponseBody List<User> getAllUsers(){
		List<User> users = userService.getAllUser();
		return users;
	}
	
	@RequestMapping("userList")//用来注解请求的路径，如果具体到某个方法时再跟某个方法的路径
	public ModelAndView userList(HttpServletRequest req){
		
		ModelAndView modelAndView = new ModelAndView();
		int pageSize = 10;
		int pageCount = Integer.parseInt(req.getParameter("pageCount")==null?"1":req.getParameter("pageCount"));
		int total = 0;
		
		List<User> users = userService.getUserLists(pageSize, pageCount);
		total = userService.getUserCount();
		
		modelAndView.setViewName("userList");
		modelAndView.addObject("users",users);
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
	
	@RequestMapping("/addUserBefore")
	public ModelAndView addUserBefore(){
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("addUser");
		return modelAndView;
	}
	
	@RequestMapping("/addUser")
	public ModelAndView addUser(@ModelAttribute User user,HttpServletRequest req){
		ModelAndView modelAndView = new ModelAndView();
		String userno = user.getUserno();
		String truename = user.getTruename();
		String password = user.getPassword();
		if(StringUtils.isEmpty(userno) || StringUtils.isEmpty(truename)|| StringUtils.isEmpty(password)){
			modelAndView.setViewName("addUser");
			modelAndView.addObject("message", "请填写完整信息以后再提交！");
			return modelAndView;
		}
		User tempUser = userService.getUserByUserNo(userno);
		if(tempUser != null){
			modelAndView.setViewName("addUser");
			modelAndView.addObject("message", "账号已经存在，请修改以后再提交！");
			return modelAndView;
		}
		
		user.setPassword(CommonUtils.getMD5Pssword(password));
		int count = userService.addUser(user);
		
		if(count == 1) {
			int pageSize = 10;
			int pageCount = Integer.parseInt(req.getParameter("pageCount")==null?"1":req.getParameter("pageCount"));
			int total = 0;
			
			List<User> users = userService.getUserLists(pageSize, pageCount);
			total = userService.getUserCount();
			
			modelAndView.setViewName("userList");
			modelAndView.addObject("users",users);
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
	
	@RequestMapping("/updateUserBefore")
	public ModelAndView updateUserBefore(@RequestParam("id") String id){
		
		ModelAndView modelAndView = new ModelAndView();
		User tempUser = userService.getUserById(Integer.valueOf(id));
		modelAndView.addObject("tempUser", tempUser);
		modelAndView.setViewName("updateUser");
		return modelAndView;
	}
	
	@RequestMapping("/updateUser")
	public ModelAndView updateUser(@ModelAttribute User user,HttpServletRequest req){
		
		ModelAndView modelAndView = new ModelAndView();
		String userno = user.getUserno();
		String truename = user.getTruename();
		String password = user.getPassword();
		if(StringUtils.isEmpty(userno) || StringUtils.isEmpty(truename)|| StringUtils.isEmpty(password)){
			modelAndView.setViewName("updateUser");
			modelAndView.addObject("tempUser", user);
			modelAndView.addObject("message", "请填写完整信息以后再提交！");
			return modelAndView;
		}
		User tempUser = userService.getUserByUserNo(userno);
		if(tempUser != null && !tempUser.getId().equals(user.getId())) {
			modelAndView.setViewName("updateUser");
			modelAndView.addObject("tempUser", user);
			modelAndView.addObject("message", "账号已经存在，请修改以后再提交！");
			return modelAndView;
		} 
		String exsitPassword = user.getPassword();
		if(StringUtils.isEmpty(exsitPassword)){
			user.setPassword(userService.getUserById(user.getId()).getPassword());
		}else{
			user.setPassword(CommonUtils.getMD5Pssword(exsitPassword));
		}
		int count = userService.updateUser(user);
		if(count == 1) {
			int pageSize = 10;
			int pageCount = Integer.parseInt(req.getParameter("pageCount")==null?"1":req.getParameter("pageCount"));
			int total = 0;
			
			List<User> users = userService.getUserLists(pageSize, pageCount);
			total = userService.getUserCount();
			
			modelAndView.setViewName("userList");
			modelAndView.addObject("users",users);
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
			modelAndView.setViewName("updateUser");
			modelAndView.addObject("tempUser", user);
			modelAndView.addObject("message", "更新失败，请刷新以后重试！");
		}
		return modelAndView;
	}

	@RequestMapping("/deleteUser")
	public @ResponseBody Map<String, String> deleteUser(@RequestParam("id") String id,HttpServletRequest request){
		Map<String,String> result = new HashMap<String,String>();
		int count = userService.deleteUsersById(Integer.valueOf(id));
		if(count >= 1) {
			result.put("result", "1");
		}else{
			result.put("result", "0");
			result.put("message", "删除失败，请刷新以后重试！");
		}
		return result;
	}
}
