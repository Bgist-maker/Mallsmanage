package com.bs.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bs.domain.User;
import com.bs.service.UserService;
import com.bs.util.CommonUtils;

@Controller
public class UserLoginController {
	
	@Autowired
	private UserService userService;
	
	// 绑定变量名字和属性，参数封装进类
	@InitBinder("user")    
	public void initBinder2(WebDataBinder binder) {    
		binder.setFieldDefaultPrefix("user.");    
	}   
	
	@RequestMapping("/login")
	public ModelAndView adminLogin(HttpServletRequest request){
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		return modelAndView;
	}
	
	@RequestMapping("/loginValidate")
	public ModelAndView loginValidate(HttpServletRequest request) throws Exception{
		
		ModelAndView modelAndView = new ModelAndView();
		HttpSession session = request.getSession(true);
		String userno = request.getParameter("userno");
		String password = request.getParameter("password");
		User user = userService.getUserByUserNo(userno);
		if(user == null){
			modelAndView.addObject("message", "输入错误，请重试！");
			modelAndView.setViewName("login");
		}else{
			if(user.getPassword().equals(CommonUtils.getMD5Pssword(password))){
					
					modelAndView.setViewName("main");
					session.setAttribute("user", user);//把这个登录的用户写到session里面，相当于把这个用户放到全局变量里面 
				
			}else{
				modelAndView.addObject("message", "用户名或者密码错误，请重试！");
				modelAndView.setViewName("login");
			}
		}
		
		return modelAndView;
	}
	
	@RequestMapping("/Logout")
	public ModelAndView Logout(HttpServletRequest request){
		ModelAndView modelAndView = new ModelAndView();
		HttpSession session = request.getSession(true);
		session.setAttribute("user", null);//
		modelAndView.setViewName("login");
		return modelAndView;
	}

}
