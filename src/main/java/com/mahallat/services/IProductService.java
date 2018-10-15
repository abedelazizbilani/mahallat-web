package com.mahallat.services;

import java.util.List;

import com.mahallat.entity.Product;
import com.mahallat.entity.ProductRating;

public interface IProductService {
	Product one(int id);
	List <Product> storeProducts(int id);
	void save(Product product);
	void update(Product product);
	Integer getProductLikesCount(Integer id);
	int ratingExist(int userId , int productId);
}
