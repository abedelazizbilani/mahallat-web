package com.mahallat.controllers.web;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.mahallat.entity.Role;
import com.mahallat.entity.User;
import com.mahallat.models.UserUpdate;
import com.mahallat.services.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@Value("${spring.file.uploads.path.users}")
	private String usersImagesPath;

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
	public ModelAndView update(@Valid UserUpdate user, BindingResult bindingResult, @RequestParam MultipartFile file) {
		ModelAndView modelAndView = new ModelAndView();
		User userExists = userService.findById(user.getId());
		if (userExists == null) {
			bindingResult.rejectValue("id", "error.user", "This user does not exist");
		}
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("admin/users/edit");
		} else {
			try {
				if (!file.isEmpty() == true) {

					// Get the file and save it somewhere
					String imagePath = usersImagesPath + file.getOriginalFilename();
					String type = file.getContentType().split("/")[1];
					String newImageName = UUID.randomUUID().toString() + "." + type;
					byte[] bytes = file.getBytes();
					Path path = Paths.get(imagePath);
					Files.write(path.resolveSibling(newImageName), bytes);

					userExists.setImage("uploads/products/" + newImageName);
				} else {
					userExists.setImage("/default.png");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			userExists.setName(user.getName());
			userExists.setUsername(user.getUsername());
			userExists.setLastname(user.getLastname());
			String userRole = "";
			for (Role role : userExists.getRoles()) {
				userRole = role.getName();
			}

			userService.saveUser(userExists, userRole);
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

	@GetMapping("/admin/dashboard/user/activate/{id}")
	public ModelAndView activate(@PathVariable("id") Integer id) {
		User user = userService.findById(id);
		if (user == null) {
			return new ModelAndView("redirect:/admin/dashboard/users");
		}

		if (user.getActive() == true) {
			user.setActive(false);
		} else {
			user.setActive(true);
		}

		userService.activate(user);
		return new ModelAndView("redirect:/admin/dashboard/users");

	}

}
