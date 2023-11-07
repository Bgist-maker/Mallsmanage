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

import com.bs.domain.Message;
import com.bs.service.MessageService;

@Controller
public class MessageManageController {
	
	@Autowired
	private MessageService messageService;
	
	@InitBinder("message")    
	public void initBinder2(WebDataBinder binder) {    
		binder.setFieldDefaultPrefix("message.");    
	}   
	
	
	@RequestMapping("/messageList")
	public ModelAndView messageList(HttpServletRequest request){
		
		ModelAndView modelAndView = new ModelAndView();
		int pageSize = 10;
		int pageCount = Integer.parseInt(request.getParameter("pageCount")==null?"1":request.getParameter("pageCount"));
		int total = 0;
		Map<String,String> map = new HashMap<String,String>();
		String userid = request.getParameter("userid");
		String buyid = request.getParameter("buyid");
		String sellerid = request.getParameter("sellerid");
		String isdeal = request.getParameter("isdeal");
		if(!StringUtils.isEmpty(buyid)){
			map.put("buyid", buyid);
		}
		if(!StringUtils.isEmpty(sellerid)){
			map.put("sellerid", sellerid);
		}
		
		map.put("userid", userid);
		map.put("isdeal", isdeal);
		map.put("pageIndex", String.valueOf((pageCount-1)*pageSize));
		map.put("pageSize", String.valueOf(pageSize));
		List<Message> messages = messageService.getMessageList(map);
		total = messageService.getMessageCount(map);
		
		modelAndView.setViewName("messageList");
		modelAndView.addObject("messages",messages);
		modelAndView.addObject("userid",userid);
		modelAndView.addObject("buyid",buyid);
		modelAndView.addObject("sellerid",sellerid);
		modelAndView.addObject("isdeal",isdeal);
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
	
	@RequestMapping("/addMessageBefore")
	public ModelAndView addMessageBefore(HttpServletRequest request){
		String userid = request.getParameter("userid");
		String buyid = request.getParameter("buyid");
		String sellerid = request.getParameter("sellerid");
		String isdeal = request.getParameter("isdeal");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("userid",userid);
		modelAndView.addObject("buyid",buyid);
		modelAndView.addObject("sellerid",sellerid);
		modelAndView.addObject("isdeal",isdeal);
		modelAndView.setViewName("addMessage");
		return modelAndView;
	}
	
	@RequestMapping("/addMessage")
	public ModelAndView addMessage(@ModelAttribute Message message,HttpServletRequest request){
		ModelAndView modelAndView = new ModelAndView();
		String msg = message.getMessage();
		if(StringUtils.isEmpty(msg)){
			modelAndView.setViewName("addMessage");
			modelAndView.addObject("message", "请填写完整信息以后再提交！");
			return modelAndView;
		}
		String userid = request.getParameter("userid");
		String buyid = request.getParameter("buyid");
		String sellerid = request.getParameter("sellerid");
		String isdeal = request.getParameter("isdeal");
		if(StringUtils.isEmpty(buyid)){
			message.setObjecttype(0);
		}else{
			message.setObjecttype(1);
		}
		int count = messageService.addMessage(message);
		
		if(count == 1) {
			int pageSize = 10;
			int pageCount = Integer.parseInt(request.getParameter("pageCount")==null?"1":request.getParameter("pageCount"));
			int total = 0;
			
			Map<String,String> map = new HashMap<String,String>();
			if(!StringUtils.isEmpty(buyid)){
				map.put("buyid", buyid);
			}
			if(!StringUtils.isEmpty(sellerid)){
				map.put("sellerid", sellerid);
			}
			
			map.put("userid", userid);
			map.put("isdeal", isdeal);
			map.put("pageIndex", String.valueOf((pageCount-1)*pageSize));
			map.put("pageSize", String.valueOf(pageSize));
			List<Message> messages = messageService.getMessageList(map);
			total = messageService.getMessageCount(map);
			
			modelAndView.setViewName("messageList");
			modelAndView.addObject("messages",messages);
			modelAndView.addObject("userid",userid);
			modelAndView.addObject("buyid",buyid);
			modelAndView.addObject("sellerid",sellerid);
			modelAndView.addObject("isdeal",isdeal);
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
			modelAndView.setViewName("addMessage");
			modelAndView.addObject("message", "添加失败，请刷新以后重试！");
		}
		return modelAndView;
	}
	
	@RequestMapping("/updateMessageBefore")
	public ModelAndView updateMessageBefore(@RequestParam("id") String id,HttpServletRequest request){
		
		ModelAndView modelAndView = new ModelAndView();
		Message tempMessage = messageService.getMessageById(Integer.valueOf(id));
		modelAndView.addObject("tempMessage", tempMessage);
		modelAndView.setViewName("updateMessage");
		return modelAndView;
	}
	
	@RequestMapping("/updateMessage")
	public ModelAndView updateMessage(@ModelAttribute Message message,HttpServletRequest request){
		
		ModelAndView modelAndView = new ModelAndView();
		String msg = message.getMessage();
		if(StringUtils.isEmpty(msg)){
			modelAndView.setViewName("updateMessage");
			modelAndView.addObject("tempMessage", message);
			modelAndView.addObject("message", "请填写完整信息以后再提交！");
			return modelAndView;
		}
		int count = messageService.updateMessage(message);
		if(count == 1) {
			int pageSize = 10;
			int pageCount = Integer.parseInt(request.getParameter("pageCount")==null?"1":request.getParameter("pageCount"));
			int total = 0;
			
			Map<String,String> map = new HashMap<String,String>();
			String userid = String.valueOf(message.getUserid());
			String buyid =String.valueOf(message.getBuyid());
			String sellerid = String.valueOf(message.getSellerid());
			String isdeal = String.valueOf(message.getIsdeal());
			if(!StringUtils.isEmpty(buyid)&& !"null".equals(buyid)){
				map.put("buyid", buyid);
			}
			if(!StringUtils.isEmpty(sellerid) && !"null".equals(sellerid)){
				map.put("sellerid", sellerid);
			}
			
			map.put("userid", userid);
			map.put("isdeal", isdeal);
			map.put("pageIndex", String.valueOf((pageCount-1)*pageSize));
			map.put("pageSize", String.valueOf(pageSize));
			List<Message> messages = messageService.getMessageList(map);
			total = messageService.getMessageCount(map);
			
			modelAndView.setViewName("messageList");
			modelAndView.addObject("messages",messages);
			modelAndView.addObject("userid",userid);
			modelAndView.addObject("buyid",buyid);
			modelAndView.addObject("sellerid",sellerid);
			modelAndView.addObject("isdeal",isdeal);
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
			modelAndView.setViewName("updateMessage");
			modelAndView.addObject("tempMessage", message);
			modelAndView.addObject("message", "更新失败，请刷新以后重试！");
		}
		return modelAndView;
	}

	@RequestMapping("/deleteMessage")
	public @ResponseBody Map<String, String> deleteMessage(@RequestParam("id") String id,HttpServletRequest request){
		Map<String,String> result = new HashMap<String,String>();
		
		int count = messageService.deleteMessage(Integer.valueOf(id));
		if(count >= 1) {
			result.put("result", "1");
		}else{
			result.put("result", "0");
			result.put("message", "删除失败，请刷新以后重试！");
		}
		return result;
	}
}
