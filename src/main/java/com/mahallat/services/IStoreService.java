package com.mahallat.services;

import java.util.List;

import com.mahallat.entity.Product;
import com.mahallat.entity.Store;
import com.mahallat.entity.StoreRating;;

public interface IStoreService {
	List<Store> getAllStores();
	Store one(int id);
	List<Product> getAllProductsByStoreId(int id);
	boolean rate(StoreRating storeRating);
	void save(Store store);
}
