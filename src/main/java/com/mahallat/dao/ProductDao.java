package com.mahallat.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.mahallat.entity.Product;

@Transactional(rollbackOn = Exception.class)
@Repository("productDao")
public class ProductDao implements IProductDao {
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Product one(int id) {
		return entityManager.find(Product.class,id);
	}

}
