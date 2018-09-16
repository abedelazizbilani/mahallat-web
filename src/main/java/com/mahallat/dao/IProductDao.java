package com.mahallat.dao;

import java.util.List;

import com.mahallat.entity.Product;
import com.mahallat.entity.ProductRating;

public interface IProductDao {
	Product one(int id);
	boolean ratingExist(int user_id , int product_id);
	void rate(ProductRating productRating);
	List <Product> all(int id);
}
