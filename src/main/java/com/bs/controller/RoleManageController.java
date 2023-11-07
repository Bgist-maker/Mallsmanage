package com.bs.controller;

import java.util.ArrayList;
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

import com.bs.domain.Authority;
import com.bs.domain.Role;
import com.bs.domain.User;
import com.bs.service.AuthorityService;
import com.bs.service.RoleService;
import com.bs.service.UserService;

@Controller
public class RoleManageController {
	
	@Autowired
	private RoleService roleService;
	@Autowired
	private UserService userService;
	@Autowired
	private AuthorityService authorityService;
	
	@InitBinder("role")    
	public void initBinder2(WebDataBinder binder) {    
		binder.setFieldDefaultPrefix("role.");    
	}   
	
	@RequestMapping("/getAllRoles")
	public @ResponseBody List<Role> getAllRoles(){
		List<Role> roles = roleService.getAllRoles();
		return roles;
	}
	
	@RequestMapping("/roleList")
	public ModelAndView roleList(HttpServletRequest request){
		
		ModelAndView modelAndView = new ModelAndView();
		int pageSize = 10;
		int pageCount = Integer.parseInt(request.getParameter("pageCount")==null?"1":request.getParameter("pageCount"));
		int total = 0;
		
		List<Role> roles = roleService.getRoleList(pageSize, pageCount);
		total = roleService.getRoleCount();
		
		modelAndView.setViewName("roleList");
		modelAndView.addObject("roles",roles);
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
	
	@RequestMapping("/addRoleBefore")
	public ModelAndView addRoleBefore(){
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("addRole");
		return modelAndView;
	}
	
	@RequestMapping("/addRole")
	public ModelAndView addRole(@ModelAttribute Role role,HttpServletRequest request){
		ModelAndView modelAndView = new ModelAndView();
		String name = role.getName();
		if(StringUtils.isEmpty(name)){
			modelAndView.setViewName("addRole");
			modelAndView.addObject("message", "请填写完整信息以后再提交！");
			return modelAndView;
		}
		Role tempRole = roleService.getRoleByName(name);
		if(tempRole != null){
			modelAndView.setViewName("addRole");
			modelAndView.addObject("message", "角色名称已经存在，请修改以后再提交！");
			return modelAndView;
		}
		
		int count = roleService.addRole(role);
		
		if(count == 1) {
			int pageSize = 10;
			int pageCount = Integer.parseInt(request.getParameter("pageCount")==null?"1":request.getParameter("pageCount"));
			int total = 0;
			
			List<Role> roles = roleService.getRoleList(pageSize, pageCount);
			total = roleService.getRoleCount();
			
			modelAndView.setViewName("roleList");
			modelAndView.addObject("roles",roles);
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
			modelAndView.setViewName("addRole");
			modelAndView.addObject("message", "添加失败，请刷新以后重试！");
		}
		return modelAndView;
	}
	
	@RequestMapping("/updateRoleBefore")
	public ModelAndView updateRoleBefore(@RequestParam("id") String id){
		
		ModelAndView modelAndView = new ModelAndView();
		Role tempRole = roleService.getRoleById(Integer.valueOf(id));
		modelAndView.addObject("tempRole", tempRole);
		modelAndView.setViewName("updateRole");
		return modelAndView;
	}
	
	@RequestMapping("/updateRole")
	public ModelAndView updateRole(@ModelAttribute Role role,HttpServletRequest request){
		
		ModelAndView modelAndView = new ModelAndView();
		String name = role.getName();
		if(StringUtils.isEmpty(name)){
			modelAndView.setViewName("updateRole");
			modelAndView.addObject("tempRole", role);
			modelAndView.addObject("message", "请填写完整信息以后再提交！");
			return modelAndView;
		}
		Role tempRole = roleService.getRoleByName(role.getName());
		if(tempRole != null && !tempRole.getId().equals(role.getId())) {
			modelAndView.setViewName("updateRole");
			modelAndView.addObject("tempRole", role);
			modelAndView.addObject("message", "角色名称已经存在，请修改以后再提交！");
			return modelAndView;
		} 
		int count = roleService.updateRole(role);
		if(count == 1) {
			int pageSize = 10;
			int pageCount = Integer.parseInt(request.getParameter("pageCount")==null?"1":request.getParameter("pageCount"));
			int total = 0;
			
			List<Role> roles = roleService.getRoleList(pageSize, pageCount);
			total = roleService.getRoleCount();
			
			modelAndView.setViewName("roleList");
			modelAndView.addObject("roles",roles);
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
			modelAndView.setViewName("updateRole");
			modelAndView.addObject("tempRole", role);
			modelAndView.addObject("message", "更新失败，请刷新以后重试！");
		}
		return modelAndView;
	}
	
	@RequestMapping("/updateRoleAuthority")
	public ModelAndView updateRoleAuthority(HttpServletRequest request){
		
		ModelAndView modelAndView = new ModelAndView();
		String roleid = request.getParameter("roleid");
		String actionids = request.getParameter("actionids");
		List<Authority> authoritys = new ArrayList<Authority>();
		Authority authority = null;
		for(String actionid:actionids.split(",")){
			authority = new Authority();
			authority.setActionid(Integer.valueOf(actionid));
			authority.setRoleid(Integer.valueOf(roleid));
			authoritys.add(authority);
		}
		int count = authorityService.updateRoleAuthority(Integer.valueOf(roleid), authoritys);
		if(count > 0) {
			int pageSize = 10;
			int pageCount = Integer.parseInt(request.getParameter("pageCount")==null?"1":request.getParameter("pageCount"));
			int total = 0;
			
			List<Role> roles = roleService.getRoleList(pageSize, pageCount);
			total = roleService.getRoleCount();
			
			modelAndView.setViewName("roleList");
			modelAndView.addObject("roles",roles);
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
			modelAndView.setViewName("authorityList");
			modelAndView.addObject("message", "更新权限失败，请刷新以后重试！");
		}
		return modelAndView;
	}

	@RequestMapping("/deleteRoles")
	
	public @ResponseBody Map<String, String> deleteRoles(@RequestParam("ids") String ids,HttpServletRequest request){
		Map<String,String> result = new HashMap<String,String>();
		List<User> users = userService.getAllUser();
		for(User user:users){
			if(ids.contains("'"+user.getRoleid()+"'")){
				result.put("result", "0");
				result.put("message", "删除失败,角色名称【"+roleService.getRoleById(user.getRoleid()).getName()+"]被赋予给用户"+user.getUserno());
				return result;
			}
		}
		int count = roleService.deleteRoles(ids.contains(",")?ids.substring(1):ids);
		if(count >= 1) {
			result.put("result", "1");
		}else{
			result.put("result", "0");
			result.put("message", "删除失败，请刷新以后重试！");
		}
		return result;
	}
	
	@RequestMapping("/modifyAuthorityBefore")
	public ModelAndView modifyAuthorityBefore(@RequestParam("roleid") String roleid){
		
		ModelAndView modelAndView = new ModelAndView();
		Map<Integer,Integer> mapForAuthority = new HashMap<Integer,Integer>();
		List<Authority> authoritys = authorityService.getAuthoritysByRoleId(Integer.valueOf(roleid));
		for(Authority authority:authoritys){
			mapForAuthority.put(authority.getActionid(), authority.getRoleid());
		}
		modelAndView.setViewName("authorityList");
		modelAndView.addObject("mapForAuthority",mapForAuthority);
		modelAndView.addObject("roleid",roleid);
		return modelAndView;
	}
}
