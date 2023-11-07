package com.bs.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bs.domain.Authority;
import com.bs.domain.User;

@Controller
public class initController {
	@Autowired
	private com.bs.service.AuthorityService authorityService;
	
	@RequestMapping("/left")
	public ModelAndView left(HttpServletRequest request){
		ModelAndView modelAndView = new ModelAndView();
		User user = (User)request.getSession(true).getAttribute("user");
		List<Authority> authoritys = authorityService.getAuthoritysByRoleId(user.getRoleid());
		modelAndView.setViewName("left");
		modelAndView.addObject("authoritys", authoritys);
		return modelAndView;
	}
	@RequestMapping("/head")
	public ModelAndView head(HttpServletRequest request){
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("head");
		return modelAndView;
	}
	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request){
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("index");
		return modelAndView;
	}
}
