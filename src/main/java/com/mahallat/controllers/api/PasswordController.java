package com.mahallat.controllers.api;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mahallat.entity.User;
import com.mahallat.models.Register;
import com.mahallat.models.ResetPassword;
import com.mahallat.services.EmailService;
import com.mahallat.services.IUserService;

@RestController("api-password-controller")
@RequestMapping(value = "api")
@CrossOrigin
public class PasswordController {
	@Autowired
	private IUserService userService;
	@Autowired
	private EmailService emailService;

	@PostMapping("forgot-password")
	@ResponseBody
	public ResponseEntity<HashMap> forgotPassword(@RequestBody @Valid ResetPassword reset, HttpServletRequest request) {

		// Lookup user in database by e-mail
		User optional = userService.findUserByEmail(reset.getEmail());
		HashMap<String, String> response = new HashMap<>();
		if (optional == null) {
			response.put("error", "user does not exist");
			return new ResponseEntity<HashMap>(response, HttpStatus.NOT_FOUND);
		} else {

			// Generate random 36-character string token for reset password
			User user = optional;
			user.setResetToken(UUID.randomUUID().toString());

			// Save token to database
			userService.save(user);

			String appUrl = request.getScheme() + "://" + request.getServerName();

			// Email message
			SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
			passwordResetEmail.setFrom("abed.bilani@gmail.com");
			passwordResetEmail.setTo(user.getEmail());
			passwordResetEmail.setSubject("Password Reset Request");
			passwordResetEmail.setText("To reset your password, click the link below:\n" + appUrl + "/reset?token="
					+ user.getResetToken());

			emailService.sendEmail(passwordResetEmail);
			response.put("success", "user reset password link sent");
			return new ResponseEntity<HashMap>(response, HttpStatus.OK);

		}

	}
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		return ex.getBindingResult().getAllErrors().stream().map(ObjectError::getDefaultMessage)
				.collect(Collectors.toList());
	}

}
