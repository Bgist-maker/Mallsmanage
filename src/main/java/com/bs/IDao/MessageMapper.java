package com.bs.IDao;

import java.util.List;
import java.util.Map;

import com.bs.domain.Message;

public interface MessageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Message record);

    int insertSelective(Message record);

    Message selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Message record);

    int updateByPrimaryKey(Message record);

	List<Message> getMessageList(Map<String, String> map);

	int getMessageCount(Map<String, String> map);
}