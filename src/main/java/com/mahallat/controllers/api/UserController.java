package com.mahallat.controllers.api;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mahallat.entity.User;
import com.mahallat.models.Register;
import com.mahallat.services.UserService;

@RestController("api-user-controller")
@RequestMapping(value = "api")
@CrossOrigin
public class UserController {

	@Autowired
	UserService userService;

	@PostMapping("user/register")
	@Transactional
	public ResponseEntity<String> register(@RequestBody @Valid Register register) {

		User user = new User();
		user.setName(register.getUsername());
		user.setLastname(register.getLastname());
		user.setEmail(register.getEmail());
		user.setUsername(register.getUsername());
		user.setPassword(register.getPassword());

		boolean flag = userService.saveUser(user, "MOBILE");

		if (!flag) {
			return new ResponseEntity<String>("there is an error",HttpStatus.CONFLICT);
		}
		return new ResponseEntity<String>("success",HttpStatus.CREATED);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		return ex.getBindingResult().getAllErrors().stream().map(ObjectError::getDefaultMessage)
				.collect(Collectors.toList());
	}
}
