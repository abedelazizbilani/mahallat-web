package com.mahallat.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mahallat.dao.IProductDao;
import com.mahallat.entity.Product;

@Service
public class ProductService implements IProductService {
	@Autowired
	private IProductDao productDao;
	@Override
	public Product one(int id) {
		return productDao.one(id);
	}

}
