package com.mahallat.services;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mahallat.dao.IRoleDao;
import com.mahallat.dao.IStoreDao;
import com.mahallat.dao.IUserDao;
import com.mahallat.entity.Role;
import com.mahallat.entity.Store;
import com.mahallat.entity.User;

@Service(value = "userService")
public class UserService implements UserDetailsService, IUserService {
	@Autowired
	private IUserDao userDAO;

	@Autowired
	private IRoleDao roleDao;

	@Override
	public User findOne(String username) {
		return userDAO.findByUsername(username);
	}

	@Override
	public User findById(Integer id) {
		return userDAO.findOne(id);
	}

	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		User user = userDAO.findByUsername(userId);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				getAuthority());
	}

	private List<SimpleGrantedAuthority> getAuthority() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_MOBILE"));
	}

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public User findUserByEmail(String email) {
		return userDAO.findByEmail(email);
	}

	@Override
	public void saveUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setActive(0);
		user.setCreatedAt(new Timestamp(System.currentTimeMillis()));
		Role userRole = roleDao.findByName("STORE");
		user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
		userDAO.save(user);
	}

	@Override
	public boolean updateUser(User user) {
		user.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
		userDAO.save(user);
		return true;
	}

	@Override
	public List<User> findStoreUsers() {
		return userDAO.findStoreUsers();
	}

}
