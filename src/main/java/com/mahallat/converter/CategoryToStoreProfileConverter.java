package com.mahallat.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.mahallat.entity.Category;
import com.mahallat.services.CategoryService;
@Component
public class CategoryToStoreProfileConverter implements Converter<Object, Category>{
	@Autowired
	CategoryService categoryService;

	/**
	 * Gets UserProfile by Id
	 * 
	 * @see org.springframework.core.convert.converter.Converter#convert(java.lang.Object)
	 */
	public Category convert(Object element) {
		Integer id = Integer.parseInt((String) element);
		Category category = categoryService.one(id);
		return category;
	}
}
