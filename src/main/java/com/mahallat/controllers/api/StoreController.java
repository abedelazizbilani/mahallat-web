package com.mahallat.controllers.api;

import java.util.HashMap;
import java.util.IntSummaryStatistics;
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
import com.mahallat.entity.StoreLike;
import com.mahallat.entity.StoreRating;
import com.mahallat.entity.User;
import com.mahallat.models.Rating;
import com.mahallat.models.StoreRequest;
import com.mahallat.services.IProductService;
import com.mahallat.services.IStoreService;
import com.mahallat.services.IUserService;

@RestController("api-store-controller")
@RequestMapping(value = "api")
@CrossOrigin
public class StoreController {
	@Autowired
	private IStoreService storeService;
	@Autowired
	private IProductService productService;
	@Autowired
	private IUserService userService;

	@GetMapping("store/{id}")
	public ResponseEntity<HashMap> one(@PathVariable("id") Integer id) {
		Store store = storeService.one(id);
		HashMap<String, Object> response = new HashMap<String, Object>();
		if (store == null) {
			response.put("status", "Data not found");
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		// check if user logged in
		User user = userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
		// get product like count
		store.likeCount = storeService.storeLikes(id).size();
		store.rated = false;
		IntSummaryStatistics stats = store.getStoreRatings().stream().mapToInt((x) -> x.getRate()).summaryStatistics();
		// if user is logged in set rated true or false , liked or not
		if (user != null) {
			List<StoreRating> ratings = storeService.ratingExist(user.getId(), id);
			store.rate = ratings.isEmpty() ? 0 : ratings.get(0).getRate();
			store.rated = ratings.size() > 0 ? true : false;
			store.liked = storeService.storeLikes(id).size() > 0 ? true : false;
		}
		store.likeCount = store.getStoreLikes().size();
		store.averageRating = stats.getAverage();
		// set success
		response.put("status", "success");
		// set data
		response.put("data", store);

		return new ResponseEntity<HashMap>(response, HttpStatus.OK);
	}

	@GetMapping("stores")
	public ResponseEntity<List<Store>> getAllStores() {
		List<Store> list = storeService.getAllStores();
		
		User user = userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
		list.forEach(store -> {
			IntSummaryStatistics stats = store.getStoreRatings().stream().mapToInt((x) -> x.getRate())
					.summaryStatistics();

			if (user != null) {
				List<StoreRating> storeRatings = storeService.ratingExist(user.getId(), store.getId());
				store.rated = storeRatings.size() > 0 ? true : false;
				store.rate = storeRatings.isEmpty() ? 0 : storeRatings.get(0).getRate();
				store.liked = storeService.storeLikes(store.getId()).size() > 0 ? true : false;
				
			}
			store.likeCount = store.getStoreLikes().size();
			store.averageRating = stats.getAverage();
		});
		
		return new ResponseEntity<List<Store>>(list, HttpStatus.OK);
	}

	@GetMapping("category/{id}/stores")
	public ResponseEntity<HashMap> getStoresByCategory(@PathVariable("id") int id) {
		List<Store> list = storeService.getStoresByCategory(id);
		HashMap<String, Object> response = new HashMap<String, Object>();
		response.put("data",list );
		response.put("success","store list");
		return new ResponseEntity<HashMap>(response, HttpStatus.OK);
	}
	
	@GetMapping("store/{id}/products")
	public ResponseEntity<HashMap> getProductsByStoreId(@PathVariable("id") int id) {
		Store store = storeService.one(id);
		HashMap<String, Object> response = new HashMap<String, Object>();
		if (store == null) {
			response.put("status", "Store does not exist");
			return new ResponseEntity<HashMap>(response, HttpStatus.OK);
		}

		List<Product> products = storeService.getAllProductsByStoreId(id);
		// check if user logged in
		User user = userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
		products.forEach(product -> {
			IntSummaryStatistics stats = product.getProductRatings().stream().mapToInt((x) -> x.getRate())
					.summaryStatistics();

			if (user != null) {
				List<ProductRating> productRatings = productService.ratingExist(user.getId(), product.getId());
				product.rated = productRatings.size() > 0 ? true : false;
				product.rate = productRatings.isEmpty() ? 0 : productRatings.get(0).getRate();
				product.liked = productService.getProductLikesCount(product.getId()) > 0 ? true : false;
				product.favorited= productService.favoriteExist(user.getId(), product.getId()) != null ? true : false;
			}
			product.likeCount = product.getProductLikes().size();
			product.averageRating = stats.getAverage();
		});
		response.put("status", "success");
		response.put("data", products);
		return new ResponseEntity<HashMap>(response, HttpStatus.OK);
	}

	@PostMapping("store/rate/{id}")
	public ResponseEntity<HashMap> rateStore(@RequestBody @Valid Rating rating, @PathVariable("id") Integer storeId) {
		User user = userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
		HashMap<String, String> response = new HashMap<String, String>();
		boolean previousRating = storeService.ratingExist(user.getId(), storeId).size() > 0 ? true : false;

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
	
	@PostMapping("store/like")
	@Transactional
	public ResponseEntity<HashMap> like(@RequestBody @Valid StoreRequest store) {
		User user = userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
		HashMap<String, String> response = new HashMap<String, String>();
		Store storeModel = storeService.one(store.getStoreId());

		if (user == null) {
			response.put("error", "uesr not logged in");
			return new ResponseEntity<HashMap>(response, HttpStatus.FAILED_DEPENDENCY);
		}

		if (storeModel == null) {
			response.put("error", "store does not exist");
			return new ResponseEntity<HashMap>(response, HttpStatus.EXPECTATION_FAILED);
		}

		StoreLike previousLike = storeService.likeExist(user.getId(), store.getStoreId());

		// check if already exist remove it else add to favorite
		if (previousLike != null) {
			storeService.removeLike(previousLike);
			response.put("success", "like has been removed");
			return new ResponseEntity<HashMap>(response, HttpStatus.CREATED);
		}
		StoreLike storeLike = new StoreLike();
		storeLike.setStore(storeModel);

		storeLike.setUser(user);
		storeService.addLike(storeLike);
		response.put("success", "store liked");
		return new ResponseEntity<HashMap>(response, HttpStatus.CREATED);

	}

}
