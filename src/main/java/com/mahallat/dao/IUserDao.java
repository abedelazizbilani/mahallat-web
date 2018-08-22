package com.mahallat.dao;

import com.mahallat.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserDao extends JpaRepository<User, Integer> {
	User findByUsername(String username);
	User findByEmail(String email);
}
