package com.mahallat.services;

import java.util.List;
import java.util.Optional;

import com.mahallat.entity.User;

public interface IUserService {
	User findOne(String username);

	public User findUserByEmail(String email);

	public void saveUser(User user);

	public List<User> findStoreUsers();

	public User findById(Integer id);

	public boolean updateUser(User user);
	
	public void deleteUser(User user);
	
}
