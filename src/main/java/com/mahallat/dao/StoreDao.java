package com.mahallat.dao;

import java.util.List; 
import javax.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import com.mahallat.entity.Store;

@Repository
public class StoreDao implements IStoreDao {
	private EntityManager entityManager;	
	@Override
	public Store one(int id) {
		return entityManager.find(Store.class, id);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Store> getAllStores() {
		String hql = "FROM Sotre as store ORDER BY store.id";
		return (List<Store>) entityManager.createQuery(hql).getResultList();
	}
}
