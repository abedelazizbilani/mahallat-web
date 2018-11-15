package com.mahallat.controllers.api;

import java.util.HashMap;
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
import org.springframework.web.bind.annotation.GetMapping;
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
	public ResponseEntity<HashMap> register(@RequestBody @Valid Register register) {

		User user = new User();
		user.setName(register.getUsername());
		user.setLastname(register.getLastname());
		user.setEmail(register.getEmail());
		user.setUsername(register.getUsername());
		user.setPassword(register.getPassword());

		if(userService.findUserByEmail(register.getEmail())!= null) {
			HashMap<String, String> response = new HashMap<>();
			response.put("status", "error");
			response.put("message", "user already exist");
			return new ResponseEntity<HashMap>(response, HttpStatus.CONFLICT);
		}
		
		userService.saveUser(user, "MOBILE");
		HashMap<String, String> response = new HashMap<>();
		response.put("status", "success");
		response.put("status", "user has been registered");

		return new ResponseEntity<HashMap>(response, HttpStatus.CREATED);
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		return ex.getBindingResult().getAllErrors().stream().map(ObjectError::getDefaultMessage)
				.collect(Collectors.toList());
	}
}
