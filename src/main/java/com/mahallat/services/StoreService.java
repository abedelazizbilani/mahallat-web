package com.mahallat.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mahallat.dao.IStoreDao;
import com.mahallat.entity.Category;
import com.mahallat.entity.Product;
import com.mahallat.entity.Store;
import com.mahallat.entity.StoreRating;

import java.sql.Timestamp;
import java.util.List;

@Service
public class StoreService implements IStoreService {
	@Autowired
	private IStoreDao storeDAO;

	@Override
	public List<Store> getAllStores() {
		return storeDAO.getAllStores();
	}

	@Override
	public Store one(int id) {
		Store obj = storeDAO.one(id);
		return obj;
	}

	@Override
	public void save(Store store) {
		store.setCreatedAt(new Timestamp(System.currentTimeMillis()));
		storeDAO.save(store);
	}

	@Override
	public List<Product> getAllProductsByStoreId(int id) {
		return storeDAO.getAllProductsByStoreId(id);
	}
	
	@Override
	public boolean userHasStore(int id) {
		return storeDAO.userHasStore(id);
	}
	
	@Override
	public void update(Store store) {
		storeDAO.update(store);
	}

	@Override
	public synchronized boolean rate(StoreRating storeRating) {
		if (storeDAO.ratingExist(storeRating.getUser().getId(), storeRating.getStore().getId())) {
			return false;
		}
		storeDAO.rate(storeRating);
		return true;
	}

	@Override
	public Store storeByUserId(int id) {
		return storeDAO.storeByUserId(id);
	}

}
