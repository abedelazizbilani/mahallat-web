package com.mahallat.controllers.web;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.ModelAndView;

import com.mahallat.entity.Role;
import com.mahallat.entity.User;
import com.mahallat.models.UserUpdate;
import com.mahallat.services.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping(value = "/admin/dashboard/users")
	public ModelAndView users() {

		ModelAndView modelAndView = new ModelAndView();
		List<User> usersList = userService.findStoreUsers();
		modelAndView.addObject("users", usersList);
		modelAndView.setViewName("admin/users/index");
		return modelAndView;
	}

	@GetMapping(value = "/admin/dashboard/user/edit/{id}")
	public ModelAndView edit(@PathVariable("id") Integer id) {
		ModelAndView modelAndView = new ModelAndView();
		User user = userService.findById(id);
		if (user == null) {
			return new ModelAndView("redirect:admin/dashboard/users");
		}

		modelAndView.addObject("user", user);
		modelAndView.setViewName("admin/users/edit");
		return modelAndView;
	}

	@PostMapping(value = "/admin/dashboard/user/edit")
	public ModelAndView update(@Valid UserUpdate user, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		User userExists = userService.findById(user.getId());
		if (userExists == null) {
			bindingResult.rejectValue("id", "error.user", "This user does not exist");
		}
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("admin/users/edit");
		} else {
			userExists.setName(user.getName());
			userExists.setUsername(user.getUsername());
			userExists.setLastname(user.getLastname());
			if (user.getImage() != "") {
				userExists.setImage(user.getImage());
			}
			userService.saveUser(userExists);
			modelAndView.addObject("users", userService.findStoreUsers());
			modelAndView.setViewName("admin/users/index");
		}
		return modelAndView;
	}

	@GetMapping(value = "/admin/dashboard/user/delete/{id}")
	public ModelAndView delete(@PathVariable("id") Integer id) {
		User user = userService.findById(id);
		if (user == null) {
			return new ModelAndView("redirect:/admin/dashboard/users");
		}

		for (Role role : user.getRoles()) {
			if (role.getName().equals("ADMIN")) {
				return new ModelAndView("redirect:/admin/dashboard/users");
			}
		}
		userService.deleteUser(user);
		return new ModelAndView("redirect:/admin/dashboard/users");
	}

}
