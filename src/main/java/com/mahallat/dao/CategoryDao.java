package com.mahallat.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.mahallat.entity.Category;
import com.mahallat.entity.Store;

@Transactional(rollbackOn = Exception.class)
@Repository("categoryDao")
public class CategoryDao implements ICategoryDao{
@PersistenceContext
private EntityManager entityManager;
	
	@Override
	public List<Category> getAllCategories() {
		String hql = "From Category as category";
		return entityManager.createQuery(hql).getResultList();
	}
	
	@Override
	public Category one(int id) {
		Category category = null;
		try {
			category  = entityManager.find(Category.class,id);
		} catch (Exception e) {
			return null;
		}
		return category;
	}
}
