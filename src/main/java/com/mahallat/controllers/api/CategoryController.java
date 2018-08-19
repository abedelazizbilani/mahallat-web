package com.mahallat.controllers.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mahallat.entity.Category;
import com.mahallat.entity.Product;
import com.mahallat.entity.Store;
import com.mahallat.services.ICategoryService;

@RestController
@RequestMapping("api")
@CrossOrigin
public class CategoryController {
	@Autowired
	private ICategoryService categoryService;
	
	@GetMapping("categories")
	public ResponseEntity<List<Category>> getAllCategories(){
		List<Category> list = categoryService.getAllCategories();
		if (list.isEmpty()) {
			return new ResponseEntity<List<Category>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Category>>(list, HttpStatus.OK);
	}
	
	@GetMapping("category/{id}")
	public ResponseEntity<Category> one(@PathVariable("id") int id){
		Category category = categoryService.one(id);
		if(category == null ) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Category>(category,HttpStatus.OK);
	}
}
