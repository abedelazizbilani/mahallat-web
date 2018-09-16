package com.mahallat.controllers.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mahallat.entity.Product;
import com.mahallat.entity.ProductRating;
import com.mahallat.services.IProductService;


@RestController("api-product-controller")
@RequestMapping(value = "api")
@CrossOrigin
public class ProductController {
	@Autowired
	private IProductService productService;

	@GetMapping("product/{id}")
	@ResponseBody
	public ResponseEntity<Product> one(@PathVariable("id") Integer id) {
		Product product = productService.one(id);
		if (product == null) {
			//http://www.springboottutorial.com/spring-boot-exception-handling-for-rest-services
			//https://www.devglan.com/spring-security/jwt-role-based-authorization
			return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Product>(product, HttpStatus.OK);
	}

	@PostMapping("product/rate")
	public ResponseEntity<Void> rate(@RequestBody ProductRating productRating) {
		boolean flag = productService.rate(productRating);
		if (!flag) {
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
}
