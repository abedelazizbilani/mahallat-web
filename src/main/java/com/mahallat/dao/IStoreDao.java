package com.mahallat.dao;
import java.util.List;

import com.mahallat.base.BaseDao;
import com.mahallat.entity.Product;
import com.mahallat.entity.Store;
import com.mahallat.entity.StoreRating;;

public interface IStoreDao{
	List<Store> getAllStores();
	Store one(int id);
	void save (Store store);
	List<Product> getAllProductsByStoreId(int id);
	void rate(StoreRating storeRating);
	boolean ratingExist(int user_id, int store_id);
}
