package com.mahallat.services;

import com.mahallat.entity.User;
import com.mahallat.models.CommentModel;

public interface ICommentService {
	public boolean save(CommentModel comment,User user);
}
