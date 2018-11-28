package com.mahallat.dao;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.mahallat.entity.Category;
import com.mahallat.entity.Product;
import com.mahallat.entity.ProductRating;
import com.mahallat.entity.Store;
import com.mahallat.entity.StoreLike;
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
		String hql = "FROM Product as product where product.active = 1 and product.store.id= ? ";
		return (List<Product>) entityManager.createQuery(hql).setParameter(1, id).getResultList();
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
	public List<StoreRating> ratingExist(int userId, int storeId) {
		String hql = "From StoreRating as storeRating where storeRating.user.id = ? and storeRating.store.id = ?";
		@SuppressWarnings("unchecked")
		List<StoreRating> ratings = entityManager.createQuery(hql).setParameter(1, userId).setParameter(2, storeId)
				.getResultList();
		return ratings;
	}

	@Override
	public Store storeByUserId(int id) {
		String hql = "FROM Store as store where store.user.id = ? ";
		return (Store) entityManager.createQuery(hql).setParameter(1, id).getSingleResult();
	}

	@Override

	public List<StoreLike> getStoreLikes(int storeId) {
		String hql = "select store.storeLikes From Store as store where store.id= ?";
		return entityManager.createQuery(hql).setParameter(1, storeId).getResultList();
	}
}
