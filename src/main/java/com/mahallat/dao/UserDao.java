package com.mahallat.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class UserDao {
	@PersistenceContext
	private EntityManager entityManager;


}
