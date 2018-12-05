package com.mahallat.services;

import java.util.List;

import com.mahallat.entity.Product;
import com.mahallat.entity.ProductLike;
import com.mahallat.entity.ProductRating;
import com.mahallat.entity.Store;
import com.mahallat.entity.StoreLike;
import com.mahallat.entity.StoreRating;;

public interface IStoreService {
	List<Store> getAllStores();

	List<Store> getStoresByCategory(int id);

	Store one(int id);

	Store storeByUserId(int id);

	List<Product> getAllProductsByStoreId(int id);

	void rate(StoreRating storeRating);

	boolean userHasStore(int id);

	void save(Store store);

	void update(Store store);

	List<StoreRating> ratingExist(int userId, int storeId);

	List<StoreLike> storeLikes(int storeId);

	StoreLike likeExist(int userId, int storeId);

	void addLike(StoreLike store);

	void removeLike(StoreLike store);
}
