package com.mahallat.services;

import java.util.List;
import java.util.Set;

import com.mahallat.entity.FavoriteProduct;
import com.mahallat.entity.Product;
import com.mahallat.entity.ProductLike;
import com.mahallat.entity.ProductRating;

public interface IProductService {
	Product one(int id);
	List <Product> storeProducts(int id);
	void save(Product product);
	void update(Product product);
	Integer getProductLikesCount(Integer id);
	List<ProductRating> ratingExist(int userId , int productId);
	FavoriteProduct favoriteExist(int userId,int productId);
	void rate(ProductRating productRating);
	void removeFavorite(FavoriteProduct favoriteProduct);
	void addFavorite(FavoriteProduct favoriteProduct);
	List<FavoriteProduct> favorites(int userId);
	ProductLike likeExist(int userId , int productId);
	void removeLike(ProductLike product);
	void addLike(ProductLike product);

}
