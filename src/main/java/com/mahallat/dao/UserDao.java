package com.mahallat.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContexts;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.mahallat.entity.User;

@Transactional
@Repository
public class UserDao implements IUserDao {
	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAllUsers() {
		String hql = "From User as usr where usr.active=1";
		return (List<User>) entityManager.createQuery(hql).getResultList();
	}
	
	
}
