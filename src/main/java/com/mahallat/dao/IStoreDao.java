package com.mahallat.dao;
import java.util.List;

import com.mahallat.base.BaseDao;
import com.mahallat.entity.Product;
import com.mahallat.entity.Store;
import com.mahallat.entity.StoreLike;
import com.mahallat.entity.StoreRating;;

public interface IStoreDao{
	List<Store> getAllStores();
	List<Store> getStoresByCategory(int id);
	Store one(int id);
	Store storeByUserId(int id);
	void save (Store store);
	void update (Store store);
	List<Product> getAllProductsByStoreId(int id);
	void rate(StoreRating storeRating);
	List<StoreRating> ratingExist(int userId, int storeId);
	boolean userHasStore(int id);
	List<StoreLike> getStoreLikes(int storeId);
	
	StoreLike likeExist(int userId,int storeId);
	void addLike(StoreLike store);
	void removeLike(StoreLike store);
	
}
