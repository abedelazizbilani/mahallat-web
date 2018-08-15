package com.mahallat.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mahallat.dao.IStoreDao;
import com.mahallat.dao.IUserDao;
import com.mahallat.entity.Store;
import com.mahallat.entity.User;
@Service
public class UserService implements IUserService{
	@Autowired
	private IUserDao userDAO;
	
	@Override
	public List<User> getAllUsers(){
		return userDAO.getAllUsers();
	}}
