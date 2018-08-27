package com.mahallat.services;

import java.sql.Timestamp;
import java.util.List;

import javax.validation.OverridesAttribute;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mahallat.dao.CategoryDao;
import com.mahallat.entity.Category;
import com.mahallat.entity.Store;

@Service
public class CategoryService implements ICategoryService {

	@Autowired
	private CategoryDao categoryDao;

	@Override
	public List<Category> getAllCategories() {
		return categoryDao.findAll();
	}

	@Override
	public Category one(int id) {
		return categoryDao.one(id);
	}

	@Override
	public void save(Category category) {
		category.setCreatedAt(new Timestamp(System.currentTimeMillis()));
		category.setCode(category.getName().replaceAll(" ", "_").toLowerCase());
		categoryDao.save(category);
	}
	@Override
	public void update(Category category) {
		categoryDao.update(category);
	}

}
