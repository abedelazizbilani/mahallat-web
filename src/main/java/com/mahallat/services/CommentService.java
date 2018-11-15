package com.mahallat.services;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mahallat.dao.CommentDao;
import com.mahallat.dao.ICommentDao;
import com.mahallat.entity.Comment;
import com.mahallat.entity.Product;
import com.mahallat.entity.Store;
import com.mahallat.entity.User;
import com.mahallat.models.CommentModel;

@Service
public class CommentService implements ICommentService {
	@Autowired
	private ICommentDao commentDao;
	@Autowired
	private IProductService productService;
	@Autowired
	private IStoreService storeService;

	@Override
	public boolean save(CommentModel commentModel, User user) {

		if (user == null) {
			return false;
		}

		Comment comment = new Comment();
		comment.setUser(user);
		if (commentModel.getProductId() > 0) {
			Product product = productService.one(commentModel.getProductId());
			if (product == null) {
				return false;
			}
			comment.setProduct(product);
		}

		if (commentModel.getStoreId() > 0) {
			Store store = storeService.one(commentModel.getStoreId());
			if (store == null) {
				return false;
			}
			comment.setStore(store);
		}

		// check if adding a comment to a store
		// check if store exist
		// check if adding a commnet to a product
		// check if product exist
		comment.setText(commentModel.getText());
		comment.setCreatedAt(new Timestamp(System.currentTimeMillis()));

		return commentDao.save(comment);
	}

}
