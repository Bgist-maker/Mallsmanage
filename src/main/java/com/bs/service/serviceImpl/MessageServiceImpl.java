package com.bs.service.serviceImpl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bs.IDao.MessageMapper;
import com.bs.domain.Message;
import com.bs.service.MessageService;

@Service("MessageService")
public class MessageServiceImpl implements MessageService {
	@Resource
	private MessageMapper messageDao;

	@Override
	public List<Message> getMessageList(Map<String,String> map) {
		return messageDao.getMessageList(map);
	}

	@Override
	public int getMessageCount(Map<String,String> map) {
		return messageDao.getMessageCount(map);
	}

	@Override
	public int addMessage(Message message) {
		return messageDao.insertSelective(message);
	}

	@Override
	public int updateMessage(Message message) {
		return messageDao.updateByPrimaryKeySelective(message);
	}

	@Override
	public Message getMessageById(int id) {
		return messageDao.selectByPrimaryKey(id);
	}

	@Override
	public int deleteMessage(int id) {
		return messageDao.deleteByPrimaryKey(id);
	}

}
