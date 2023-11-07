package com.bs.service;

import java.util.List;

import com.bs.domain.User;

public interface UserService {
	User getUserByUserNo(String userno);
	User getUserById(int id);
	List<User> getUserLists(int pageSize,int pageCount);
	List<User> getAllUser();
	int getUserCount();
	int addUser(User user);
	int updateUser(User user); 
	int deleteUsersById(int id);
}
