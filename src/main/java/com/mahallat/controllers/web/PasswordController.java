package com.mahallat.controllers.web;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mahallat.entity.User;
import com.mahallat.services.EmailService;
import com.mahallat.services.UserService;

@Controller("web-password-controller")
public class PasswordController {

	@Autowired
	private UserService userService;

	@Autowired
	private EmailService emailService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	// Display forgotPassword page
	@GetMapping(value = "/forgot")
	public ModelAndView displayForgotPasswordPage() {
		return new ModelAndView("forgot-password");
	}

	@PostMapping(value = "/forgot")
	public ModelAndView processForgotPasswordForm(ModelAndView modelAndView, @RequestParam("email") String email,
			HttpServletRequest request) {
		// Lookup user in database by e-mail
		User optional = userService.findUserByEmail(email);

		if (optional == null) {
			modelAndView.addObject("errorMessage", "We didn't find an account for that e-mail address.");
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

			// Add success message to view
			modelAndView.addObject("successMessage", "A password reset link has been sent to " + email);
		}

		modelAndView.setViewName("forgot-password");
		return modelAndView;
	}
	
	// Display form to reset password
	@GetMapping(value = "/reset")
	public ModelAndView displayResetPasswordPage(ModelAndView modelAndView, @RequestParam("token") String token) {
		
		Optional<User> user = userService.findUserByResetToken(token);

		if (user.isPresent()) { // Token found in DB
			modelAndView.addObject("token", token);
		} else { // Token not found in DB
			modelAndView.addObject("errorMessage", "Oops!  This is an invalid password reset link.");
		}

		modelAndView.setViewName("reset-password");
		return modelAndView;
	}

	// Process reset password form
	@PostMapping(value = "/reset")
	public ModelAndView setNewPassword(ModelAndView modelAndView, @RequestParam Map<String, String> requestParams, RedirectAttributes redir) {
		
		// Find the user associated with the reset token
		Optional<User> user = userService.findUserByResetToken(requestParams.get("token"));

		// This should always be non-null but we check just in case
		if (user.isPresent()) {
			
			User resetUser = user.get(); 
            
			// Set new password    
            resetUser.setPassword(bCryptPasswordEncoder.encode(requestParams.get("password")));
            
			// Set the reset token to null so it cannot be used again
			resetUser.setResetToken(null);

			// Save user
			userService.save(resetUser);

			// In order to set a model attribute on a redirect, we must use
			// RedirectAttributes
			redir.addFlashAttribute("successMessage", "You have successfully reset your password.  You may now login.");

			modelAndView.setViewName("redirect:login");
			return modelAndView;
			
		} else {
			modelAndView.addObject("errorMessage", "Oops!  This is an invalid password reset link.");
			modelAndView.setViewName("resetPassword");	
		}
		
		return modelAndView;
   }
   
    // Going to reset page without a token redirects to login page
//	@ExceptionHandler(MissingServletRequestParameterException.class)
//	public ModelAndView handleMissingParams(MissingServletRequestParameterException ex) {
//		return new ModelAndView("redirect:login");
//	}

}