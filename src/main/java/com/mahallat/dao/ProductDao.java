package com.mahallat.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;

import com.mahallat.entity.FavoriteProduct;
import com.mahallat.entity.Product;
import com.mahallat.entity.ProductLike;
import com.mahallat.entity.ProductRating;

@Transactional(rollbackOn = Exception.class)
@Repository("productDao")
public class ProductDao implements IProductDao {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Product one(int id) {
		return entityManager.find(Product.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductRating> ratingExist(int userId, int productId) {
		String hql = "From ProductRating as productRating where productRating.user.id= ? and productRating.product.id = ?";
		return (List<ProductRating>) entityManager.createQuery(hql).setParameter(1, userId).setParameter(2, productId)
				.getResultList();

	}

	@Override
	public void rate(ProductRating productRating) {
		entityManager.persist(productRating);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Product> all(int id) {
		String hql = "From Product as product where product.store.id = ?";
		List<Product> products = (List<Product>) entityManager.createQuery(hql).setParameter(1, id).getResultList();
		return products;
	}

	@Override
	public void save(Product product) {
		entityManager.persist(product);
	}

	@Override
	public void update(Product product) {
		Product productExist = one(product.getId());
		productExist.setName(product.getName());
		productExist.setDescription(product.getDescription());
		productExist.setActive(product.getActive());
		productExist.setImage(product.getImage());
		productExist.setPrice(product.getPrice());
		entityManager.flush();
	}

	@Override
	public Integer productLikes(int id) {
		String hql = "select product.productLikes From Product as product where product.id= ?";
		return (int) entityManager.createQuery(hql).setParameter(1, id).getResultList().size();
	}

	@Override
	public FavoriteProduct favoriteExist(int userId, int productId) {
		String hql = "From FavoriteProduct as favoriteProduct where favoriteProduct.user.id= ? and favoriteProduct.product.id = ?";

		try {
			return (FavoriteProduct) entityManager.createQuery(hql).setParameter(1, userId).setParameter(2, productId)
					.getSingleResult();
		} catch (NoResultException ex) {
			return null;
		}
	}

	@Override
	public List<FavoriteProduct> favorites(int userId) {
		String hql = "From FavoriteProduct as favoriteProduct where favoriteProduct.user.id= ?";

		try {
			return (List<FavoriteProduct>) entityManager.createQuery(hql).setParameter(1, userId)
					.getResultList();
		} catch (NoResultException ex) {
			return null;
		}
	}
	
	@Override
	public void removeFavorite(FavoriteProduct favoriteProduct) {
		entityManager.remove(favoriteProduct);
	}

	@Override
	public void addFavorite(FavoriteProduct favoriteProduct) {
		entityManager.persist(favoriteProduct);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ProductLike likeExist(int userId, int productId) {
		String hql = "From ProductLike as productLike where productLike.user.id= ? and productLike.product.id = ?";
		try {
		return (ProductLike) entityManager.createQuery(hql).setParameter(1, userId).setParameter(2, productId)
				.getSingleResult();
		}catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public void removeLike(ProductLike product) {
		entityManager.remove(product);
	}

	@Override
	public void addLike(ProductLike product) {
		entityManager.persist(product);
	}
	
}
