package com.mahallat.services;

import java.util.List;
import java.util.Optional;

import com.mahallat.entity.User;

public interface IUserService {
	User findOne(String username);

	public User findUserByEmail(String email);

	public boolean saveUser(User user, String role);

	public List<User> findStoreUsers();

	public User findById(Integer id);

	public boolean updateUser(User user);
	
	public void deleteUser(User user);
	
	public void save(User user);

	public void activate(User user);
	
	public Optional<User> findUserByResetToken(String resetToken);	
}
