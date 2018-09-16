package com.mahallat.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;
import com.mahallat.entity.Product;
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

	@Override
	public boolean ratingExist(int user_id, int product_id) {
		String hql = "From ProductRating as productRating where productRating.user_id = ? and productRating.product_id = ?";
		int count = entityManager.createQuery(hql).setParameter(1, user_id).setParameter(2, product_id).getResultList()
				.size();
		return count > 0 ? false : true;
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

}
