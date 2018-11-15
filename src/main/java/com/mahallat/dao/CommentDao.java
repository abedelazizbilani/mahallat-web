package com.mahallat.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.mahallat.entity.Comment;

@Transactional(rollbackOn = Exception.class)
@Repository("commentDao")
public class CommentDao implements ICommentDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public boolean save(Comment comment) {

		try {
			entityManager.persist(comment);
			return true;
		} catch (Exception e) {
			System.out.println("Comment error log" + e);
			return false;
		}
	}
}
