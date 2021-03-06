package com.mahallat.dao;

import com.mahallat.entity.User;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.jboss.logging.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserDao extends JpaRepository<User, Integer> {
	User findByUsername(String username);
	
	User findByEmail(String email);

	@Query("From User as user")
	List<User> findStoreUsers();

	@Query("From User as user  where user.id  =  :#{#id}")
	User findOne(Integer id);

	Optional<User> findByResetToken(String resetToken);

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("update User user set user.active =?1 where user.id =?2")
	void activate(Boolean active , Integer id);
	
}
