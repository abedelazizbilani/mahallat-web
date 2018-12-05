package com.mahallat.dao;

import java.util.List;
import java.util.Set;

import com.mahallat.entity.FavoriteProduct;
import com.mahallat.entity.Product;
import com.mahallat.entity.ProductLike;
import com.mahallat.entity.ProductRating;
import com.mahallat.models.Favorite;

public interface IProductDao {
	Product one(int id);
	List<ProductRating>  ratingExist(int userId , int productId);
	void rate(ProductRating productRating);
	List <Product> all(int id);
	void save(Product product);
	void update (Product product);
	Integer productLikes(int id);
	FavoriteProduct favoriteExist(int userId,int productId);
	void removeFavorite(FavoriteProduct product);
	void addFavorite(FavoriteProduct product);
	ProductLike likeExist(int userId , int productId);
	void removeLike(ProductLike product);
	void addLike(ProductLike product);
	List<FavoriteProduct> favorites(int userId);
}
