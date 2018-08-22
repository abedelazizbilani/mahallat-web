package com.mahallat.services;

import java.util.List;
import com.mahallat.entity.User;

public interface IUserService {
	User findOne(String username);
	public User findUserByEmail(String email);
	public void saveUser(User user);
}
