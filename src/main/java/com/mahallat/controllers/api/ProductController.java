package com.mahallat.controllers.api;

import java.util.HashMap;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mahallat.entity.Product;
import com.mahallat.entity.User;
import com.mahallat.models.ProductRating;
import com.mahallat.models.Register;
import com.mahallat.services.IProductService;
import com.mahallat.services.IUserService;

@RestController("api-product-controller")
@RequestMapping(value = "api")
@CrossOrigin
public class ProductController {
	@Autowired
	private IProductService productService;
	private IUserService userService;

	@GetMapping("product/{id}")
	@ResponseBody
	public ResponseEntity<HashMap> one(@PathVariable("id") Integer id) {
		Product product = productService.one(id);
		HashMap<String, Object> response = new HashMap<String, Object>();
		if (product == null) {
			response.put("status", "Data not found");
			return new ResponseEntity<HashMap>(response, HttpStatus.NOT_FOUND);
		}
		int ratingTotal = product.getProductRatings().size();
		IntSummaryStatistics stats = product.getProductRatings().stream().mapToInt((x) -> x.getRate())
				.summaryStatistics();
		System.out.println(stats);

		response.put("status", "success");
		response.put("data", product);

		return new ResponseEntity<HashMap>(response, HttpStatus.OK);
	}

	@GetMapping("product/likes/{id}")
	public ResponseEntity<HashMap> productLikesCount(@PathVariable("id") Integer id) {
		// get products like
		int productLikesCount = productService.getProductLikesCount(id);
		HashMap<String, String> response = new HashMap<String, String>();
		response.put("message", "success");
		response.put("data", Integer.toString(productLikesCount));
		return new ResponseEntity<HashMap>(response, HttpStatus.OK);
	}

	@PostMapping("product/rate/{id}")
	@Transactional
	public ResponseEntity<Boolean> rate(@RequestBody @Valid ProductRating rating,
			@PathVariable("id") Integer productId) {
		User user = userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

		int previousRating = productService.ratingExist(user.getId(), productId);
		return new ResponseEntity<Boolean>(true, HttpStatus.CREATED);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		return ex.getBindingResult().getAllErrors().stream().map(ObjectError::getDefaultMessage)
				.collect(Collectors.toList());
	}

}
