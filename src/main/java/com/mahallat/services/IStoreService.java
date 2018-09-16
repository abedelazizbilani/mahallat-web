package com.mahallat.services;

import java.util.List;

import com.mahallat.entity.Product;
import com.mahallat.entity.Store;
import com.mahallat.entity.StoreRating;;

public interface IStoreService {
	List<Store> getAllStores();
	Store one(int id);
	Store storeByUserId(int id);
	List<Product> getAllProductsByStoreId(int id);
	boolean rate(StoreRating storeRating);
	boolean userHasStore(int id);
	void save(Store store);
	void update(Store store);
}
