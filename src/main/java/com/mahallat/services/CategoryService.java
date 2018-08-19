package com.mahallat.services;

import java.util.List;

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
		return categoryDao.getAllCategories();
	}

	@Override
	public Category one(int id) {
		return categoryDao.one(id);
	}

}
