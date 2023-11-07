package com.bs.service.serviceImpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bs.IDao.UserMapper;
import com.bs.domain.User;
import com.bs.service.UserService;

@Service("UserService") 
public class UserServiceImpl implements UserService{
	@Resource
	private UserMapper userDao;

	@Override
	public User getUserByUserNo(String userno) {
		return userDao.selectByUserNo(userno);
	}

	@Override
	public List<User> getUserLists(int pageSize, int pageCount) {
		return userDao.getUserLists((pageCount-1)*pageSize, pageSize);
	}

	@Override
	public int getUserCount() {
		return userDao.getUserCount();
	}

	@Override
	public int addUser(User user) {
		return userDao.insertSelective(user);
	}

	@Override
	public int updateUser(User user) {
		return userDao.updateByPrimaryKeySelective(user);
	}

	@Override
	public int deleteUsersById(int id) {
		return userDao.deleteByPrimaryKey(id);
	}

	@Override
	public User getUserById(int id) {
		return userDao.selectByPrimaryKey(id);
	}

	@Override
	public List<User> getAllUser() {
		return userDao.getAllUser();
	}


}
