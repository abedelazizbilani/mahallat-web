package com.mahallat.dao;

import java.util.List;

import com.mahallat.entity.Category;

public interface ICategoryDao {
	List<Category> getAllCategories();
	Category one(int id);
}
