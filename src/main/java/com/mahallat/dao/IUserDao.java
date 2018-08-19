package com.mahallat.dao;

import com.mahallat.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserDao extends CrudRepository<User, Integer> {
	User findByUsername(String username);
}
