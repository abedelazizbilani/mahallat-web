package com.mahallat.dao;

import java.util.List; 
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import com.mahallat.entity.Store;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository("storeDao")
public class StoreDao implements IStoreDao {
	@PersistenceContext
	private EntityManager entityManager;	
	@Override
	public Store one(int id) {
		return entityManager.find(Store.class, id);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Store> getAllStores() {
		String hql = "FROM Store as store";
		return (List<Store>) entityManager.createQuery(hql).getResultList();
	}
}
