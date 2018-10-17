package com.mahallat.controllers.api;

import java.util.HashMap;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mahallat.entity.Product;
import com.mahallat.entity.ProductRating;
import com.mahallat.entity.Store;
import com.mahallat.entity.StoreRating;
import com.mahallat.entity.User;
import com.mahallat.models.Rating;
import com.mahallat.services.IStoreService;
import com.mahallat.services.IUserService;

@RestController("api-store-controller")
@RequestMapping(value = "api")
@CrossOrigin
public class StoreController {
	@Autowired
	private IStoreService storeService;
	@Autowired
	private IUserService userService;
	
	@GetMapping("store/{id}")
	public ResponseEntity<Store> one(@PathVariable("id") Integer id) {
		Store store = storeService.one(id);
		return new ResponseEntity<Store>(store, HttpStatus.OK);
	}

	@GetMapping("stores")
	public ResponseEntity<List<Store>> getAllStores() {
		List<Store> list = storeService.getAllStores();
		return new ResponseEntity<List<Store>>(list, HttpStatus.OK);
	}

	@GetMapping("store/{id}/products")
	public ResponseEntity<List<Product>> getProductsByStoreId(@PathVariable("id") int id) {
		List<Product> list = storeService.getAllProductsByStoreId(id);
		return new ResponseEntity<List<Product>>(list, HttpStatus.OK);
	}

	@PostMapping("store/rate/{id}")
	public ResponseEntity<HashMap> rate(@RequestBody @Valid Rating rating
			,@PathVariable("id") Integer storeId) {
		User user = userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
		HashMap<String, String> response = new HashMap<String,String>();
		boolean previousRating = storeService.ratingExist(user.getId(), storeId);

		if (!previousRating) {
			Store store = storeService.one(storeId);
			StoreRating storeRating = new StoreRating();
			storeRating.setRate(rating.getRating());
			storeRating.setUser(user);
			storeRating.setStore(store);
			storeService.rate(storeRating);
			response.put("success", "store rated");
			return new ResponseEntity<HashMap>(response, HttpStatus.CREATED);
		}

		response.put("error", "product already rated");
		return new ResponseEntity<HashMap>(response, HttpStatus.CREATED);
	}
	
}
