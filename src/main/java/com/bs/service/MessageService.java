package com.bs.service;

import java.util.List;
import java.util.Map;

import com.bs.domain.Message;

public interface MessageService {
	
	int addMessage(Message message);
	int updateMessage(Message message);
	int deleteMessage(int id);
	Message getMessageById(int id);
	List<Message> getMessageList(Map<String, String> map);
	int getMessageCount(Map<String, String> map);
}
