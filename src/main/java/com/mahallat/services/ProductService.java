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
	public int ratingExist(int userId , int productId) {
		if (productDao.ratingExist(userId, productId)) {
			return false;
		}
		productDao.rate();
		return true;
	}

	@Override
	public List<Product> storeProducts(int id) {
		return productDao.all(id);
	}
	
	@Override
	public void save (Product product) {
		productDao.save(product);
	}
	
	@Override 
	public void update (Product product) {
		productDao.update(product);
	}
	
	@Override
	public Integer getProductLikesCount(Integer id)
	{
		return productDao.productLikes(id);
	}
}
