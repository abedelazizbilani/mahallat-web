package com.mahallat.services;

import java.util.List;

import com.mahallat.entity.Product;
import com.mahallat.entity.ProductRating;

public interface IProductService {
	Product one(int id);
	boolean rate(ProductRating productRating);
	List <Product> storeProducts(int id);
}
