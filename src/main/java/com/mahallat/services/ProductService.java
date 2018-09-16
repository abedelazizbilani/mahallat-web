package com.mahallat.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mahallat.dao.IProductDao;
import com.mahallat.entity.Product;
import com.mahallat.entity.ProductRating;

@Service
public class ProductService implements IProductService {
	@Autowired
	private IProductDao productDao;

	@Override
	public Product one(int id) {
		return productDao.one(id);
	}

	@Override
	public boolean rate(ProductRating productRating) {
		if (productDao.ratingExist(productRating.getUser().getId(), productRating.getProduct().getId())) {
			return false;
		}
		productDao.rate(productRating);
		return true;
	}

	@Override
	public List<Product> storeProducts(int id) {
		return productDao.all(id);
	}
	
}
