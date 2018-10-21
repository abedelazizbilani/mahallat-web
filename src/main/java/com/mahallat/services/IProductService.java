package com.mahallat.services;

import java.util.List;
import java.util.Set;

import com.mahallat.entity.Product;
import com.mahallat.entity.ProductRating;

public interface IProductService {
	Product one(int id);
	List <Product> storeProducts(int id);
	void save(Product product);
	void update(Product product);
	Integer getProductLikesCount(Integer id);
	List<ProductRating> ratingExist(int userId , int productId);
	void rate(ProductRating productRating);
}
