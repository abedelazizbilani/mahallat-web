package com.mahallat.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

	@SuppressWarnings("unchecked")
	@Override
	public boolean ratingExist(int userId, int productId) {
		List<ProductRating> rating = new ArrayList<ProductRating>();
		String hql = "From ProductRating as productRating where productRating.user.id= ? and productRating.product.id = ?";
		int count = entityManager.createQuery(hql).setParameter(1, userId).setParameter(2, productId).getResultList()
				.size();
		return count > 0 ? true : false;
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

}
