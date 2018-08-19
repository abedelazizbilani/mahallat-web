package com.mahallat.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.mahallat.entity.Product;
import com.mahallat.entity.Store;
import com.mahallat.entity.StoreRating;

import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor = Exception.class)
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
		String hql = "FROM Store as store where store.active = 1";
		return (List<Store>) entityManager.createQuery(hql).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Product> getAllProductsByStoreId(int id) {
		String hql = "FROM Product as product where product.active = 1";
		return (List<Product>) entityManager.createQuery(hql).getResultList();
	}

	@Override
	public void rate(StoreRating storeRating) {
		entityManager.persist(storeRating);
	}

	@Override
	public boolean ratingExist(int user_id, int store_id) {
		String hql = "From StoreRating as storeRating where storeRating.user_id = ? and storeRating.store_id = ?";
		int count = entityManager.createQuery(hql).setParameter(1, user_id).setParameter(2, store_id).getResultList()
				.size();
		return count > 0 ? false : true;
	}
}
