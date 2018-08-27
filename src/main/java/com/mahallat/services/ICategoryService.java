package com.mahallat.services;

import java.util.List;
import com.mahallat.entity.Category;
import com.mahallat.entity.Store;

public interface ICategoryService {
	List<Category> getAllCategories();
	Category one(int id);
	void save(Category category);
	void update(Category category);
}
