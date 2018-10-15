package com.mahallat.dao;

import java.util.List;

import com.mahallat.entity.Product;
import com.mahallat.entity.ProductRating;

public interface IProductDao {
	Product one(int id);
	int ratingExist(int userId , int productId);
	void rate(ProductRating productRating);
	List <Product> all(int id);
	void save(Product product);
	void update (Product product);
	Integer productLikes(int id);
}
