package com.mahallat.dao;

import java.util.List;

import com.mahallat.entity.Category;

public interface ICategoryDao {
	List<Category> findAll();
	Category one(int id);
	void save(Category category);
	void update(Category category);
}
