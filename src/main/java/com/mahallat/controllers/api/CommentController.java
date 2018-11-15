package com.mahallat.controllers.api;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mahallat.entity.User;
import com.mahallat.models.CommentModel;
import com.mahallat.services.ICommentService;
import com.mahallat.services.IUserService;

@RestController("api-comment-controller")
@RequestMapping("api")
@CrossOrigin
public class CommentController {
	@Autowired
	private ICommentService commentService;
	@Autowired
	private IUserService userService;

	@PostMapping("comment/add")
	public ResponseEntity<HashMap> add(@Valid CommentModel commentModel) {
		HashMap<String, Object> response = new HashMap<>();
		User user = userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

		if (((commentModel.getProductId() > 0) && (commentModel.getStoreId() > 0))
				|| ((commentModel.getProductId() == 0) && (commentModel.getStoreId() == 0))) {
			response.put("error", "parameters error");
			return new ResponseEntity<HashMap>(response, HttpStatus.OK);
		}
		if (commentService.save(commentModel, user)== true) {
			response.put("success", "comment success");
			return new ResponseEntity<HashMap>(response, HttpStatus.OK);
		}

		response.put("error", "comment error");
		return new ResponseEntity<HashMap>(response, HttpStatus.OK);
		// check if user is logged in
		// check if adding a comment to a product and a store at the same time

	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		return ex.getBindingResult().getAllErrors().stream().map(ObjectError::getDefaultMessage)
				.collect(Collectors.toList());
	}

}
