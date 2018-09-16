package com.mahallat.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.mahallat.entity.Category;
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
		String hql = "FROM Store as store";
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
	public void save(Store store) {
		entityManager.persist(store);
	}

	@Override
	public void update(Store store) {

		Store storeExist = one(store.getId());
		storeExist.setName(store.getName());
		storeExist.setDescription(store.getDescription());
		storeExist.setActive(store.getActive());
		storeExist.setImage(store.getImage());
		storeExist.setLatitude(store.getLatitude());
		storeExist.setLongitude(store.getLongitude());
		storeExist.setOpenHour(store.getOpenHour());
		storeExist.setCloseHour(store.getCloseHour());
		storeExist.setCategory(store.getCategory());

		entityManager.flush();
	}

	@Override
	public boolean userHasStore(int id) {
		String hql = "FROM Store as store where store.user.id = ? ";
		int count = entityManager.createQuery(hql).setParameter(1, id).getResultList().size();
		// check if user has store if bigger than zero than he has a store => cannot
		// create a store
		return count > 0 ? true : false;
	}

	@Override
	public boolean ratingExist(int user_id, int store_id) {
		String hql = "From StoreRating as storeRating where storeRating.user_id = ? and storeRating.store_id = ?";
		int count = entityManager.createQuery(hql).setParameter(1, user_id).setParameter(2, store_id).getResultList()
				.size();
		return count > 0 ? false : true;
	}

	@Override
	public Store storeByUserId(int id) {
		String hql = "FROM Store as store where store.user.id = ? ";
		return (Store) entityManager.createQuery(hql).setParameter(1, id).getSingleResult();
	}
}
