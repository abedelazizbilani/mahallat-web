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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mahallat.entity.FavoriteProduct;
import com.mahallat.entity.Product;
import com.mahallat.entity.ProductLike;
import com.mahallat.entity.ProductRating;
import com.mahallat.entity.User;
import com.mahallat.models.Favorite;
import com.mahallat.models.Rating;
import com.mahallat.services.IProductService;
import com.mahallat.services.IUserService;
import com.mahallat.services.ProductService;

@RestController("api-product-controller")
@RequestMapping(value = "api")
@CrossOrigin
public class ProductController {

	@Autowired
	private IProductService productService;
	@Autowired
	private IUserService userService;

	@GetMapping("product/{id}")
	@ResponseBody
	public ResponseEntity<HashMap> one(@PathVariable("id") Integer id) {
		Product product = productService.one(id);
		HashMap<String, Object> response = new HashMap<String, Object>();
		// check if product exist
		if (product == null) {
			response.put("status", "Data not found");
			return new ResponseEntity<HashMap>(response, HttpStatus.NOT_FOUND);
		}

		// check if user logged in
		User user = userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
		boolean previousRating = false;
		// get product like count
		product.likeCount = productService.getProductLikesCount(id);
		IntSummaryStatistics stats = product.getProductRatings().stream().mapToInt((x) -> x.getRate())
				.summaryStatistics();
		// if user is logged in set rated true or false , liked or not
		if (user != null) {
			List<ProductRating> ratings = productService.ratingExist(user.getId(), id);
			product.rate = (int) ratings.get(0).getRate();
			previousRating = ratings.size() > 0 ? true : false;
			product.rated = previousRating;
			product.liked = productService.getProductLikesCount(id) > 0 ? true : false;
			product.favorited= productService.favoriteExist(user.getId(), product.getId()) != null ? true : false;
		}
		product.likeCount = product.getProductLikes().size();
		product.averageRating = stats.getAverage();
		// set success
		response.put("status", "success");
		// set data
		response.put("data", product);

		return new ResponseEntity<HashMap>(response, HttpStatus.OK);
	}

	@PostMapping("product/rate/{id}")
	@Transactional
	public ResponseEntity<HashMap> rate(@RequestBody @Valid Rating rating, @PathVariable("id") Integer productId) {
		User user = userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
		HashMap<String, String> response = new HashMap<String, String>();
		boolean previousRating = productService.ratingExist(user.getId(), productId).size() > 0 ? true : false;

		if (!previousRating) {
			Product product = productService.one(productId);
			ProductRating prodcutRating = new ProductRating();
			prodcutRating.setRate(rating.getRating());
			prodcutRating.setUser(user);
			prodcutRating.setProduct(product);
			productService.rate(prodcutRating);
			response.put("success", "product rated");
			return new ResponseEntity<HashMap>(response, HttpStatus.CREATED);
		}
		response.put("error", "product already rated");
		return new ResponseEntity<HashMap>(response, HttpStatus.CREATED);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		return ex.getBindingResult().getAllErrors().stream().map(ObjectError::getDefaultMessage)
				.collect(Collectors.toList());
	}

	@PostMapping("product/add-favorite")
	@Transactional
	public ResponseEntity<HashMap> addToFavorite(@RequestBody @Valid Favorite favorite) {
		User user = userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
		HashMap<String, String> response = new HashMap<String, String>();
		Product product = productService.one(favorite.getProductId());

		if (user == null) {
			response.put("error", "uesr not logged in");
			return new ResponseEntity<HashMap>(response, HttpStatus.FAILED_DEPENDENCY);
		}

		if (product == null) {
			response.put("error", "product does not exist");
			return new ResponseEntity<HashMap>(response, HttpStatus.CREATED);
		}

		FavoriteProduct previousFavorite = productService.favoriteExist(user.getId(), favorite.getProductId());

		// check if already exist remove it else add to favorite
		if (previousFavorite != null) {
			productService.removeFavorite(previousFavorite);
			response.put("success", "product has been removed");
			return new ResponseEntity<HashMap>(response, HttpStatus.CREATED);
		}
		FavoriteProduct favoriteProduct = new FavoriteProduct();
		favoriteProduct.setProduct(product);

		favoriteProduct.setUser(user);
		productService.addFavorite(favoriteProduct);
		response.put("success", "product added to favorite");
		return new ResponseEntity<HashMap>(response, HttpStatus.CREATED);

	}

	@GetMapping("product/get-favorite")
	@Transactional
	public ResponseEntity<HashMap> getFavorites() {
		User user = userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
		HashMap<String, Object> response = new HashMap<String, Object>();
		List<FavoriteProduct> favoriteProducts = productService.favorites(user.getId());
		
		response.put("status", "success");
		response.put("data", favoriteProducts);
		return new ResponseEntity<HashMap>(response, HttpStatus.OK);

	}

	@PostMapping("product/like")
	@Transactional
	public ResponseEntity<HashMap> like(@RequestBody @Valid Favorite product) {
		User user = userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
		HashMap<String, String> response = new HashMap<String, String>();
		Product productModel = productService.one(product.getProductId());

		if (user == null) {
			response.put("error", "uesr not logged in");
			return new ResponseEntity<HashMap>(response, HttpStatus.FAILED_DEPENDENCY);
		}

		if (productModel == null) {
			response.put("error", "product does not exist");
			return new ResponseEntity<HashMap>(response, HttpStatus.EXPECTATION_FAILED);
		}

		ProductLike previousLike = productService.likeExist(user.getId(), product.getProductId());

		// check if already exist remove it else add to favorite
		if (previousLike != null) {
			productService.removeLike(previousLike);
			response.put("success", "like has been removed");
			return new ResponseEntity<HashMap>(response, HttpStatus.CREATED);
		}
		ProductLike productLike = new ProductLike();
		productLike.setProduct(productModel);

		productLike.setUser(user);
		productService.addLike(productLike);
		response.put("success", "product liked");
		return new ResponseEntity<HashMap>(response, HttpStatus.CREATED);

	}

}
