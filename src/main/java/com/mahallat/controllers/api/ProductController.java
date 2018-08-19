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

import com.mahallat.entity.Product;
import com.mahallat.services.IProductService;

@RestController
@RequestMapping(value = "api")
@CrossOrigin
public class ProductController {
	@Autowired
	private IProductService productService;

	@GetMapping("product/{id}")
	public ResponseEntity<Product> one(@PathVariable("id") Integer id) {
		Product product = productService.one(id);
		if (product== null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Product>(product, HttpStatus.OK);
	}
}
