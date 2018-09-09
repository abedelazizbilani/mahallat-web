package com.mahallat.controllers.web;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mahallat.entity.Category;
import com.mahallat.entity.Store;
import com.mahallat.entity.User;
import com.mahallat.services.CategoryService;
import com.mahallat.services.StoreService;
import com.mahallat.services.UserService;

@Controller("web-store-controller")
public class StoreController {

	@Autowired
	StoreService storeService;
	@Autowired
	CategoryService categoryService;
	@Autowired
	UserService userService;

	@Value("${spring.file.uploads.path}")
	private String storeImagesPath;

	@GetMapping("/admin/dashboard/stores")
	public ModelAndView index() {
		ModelAndView modelAndView = new ModelAndView();
		List<Store> storesList = storeService.getAllStores();
		modelAndView.addObject("stores", storesList);
		modelAndView.setViewName("admin/store/index");
		return modelAndView;
	}

	@GetMapping("/admin/dashboard/store/add")
	public ModelAndView add() {
		ModelAndView modelAndView = new ModelAndView();

		User user = userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

		modelAndView.addObject("store", new Store());
		modelAndView.addObject("categories", categoryService.getAllCategories());
		modelAndView.addObject("user", user);

		modelAndView.setViewName("admin/store/add");
		return modelAndView;
	}

	@PostMapping(value = "/admin/dashboard/store/add")
	public ModelAndView add(Store store, BindingResult bindingResult, @RequestParam MultipartFile file,
			@RequestParam("user_id") Integer userId, @RequestParam("category") Integer categoryId) {
		ModelAndView modelAndView = new ModelAndView("redirect:/admin/dashboard/stores");
		if (bindingResult.hasErrors()) {
			List<Category> categoriesList = categoryService.getAllCategories();
			User user = userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
			modelAndView.addObject("user", user);
			modelAndView.addObject("categories", categoriesList);
			modelAndView.setViewName("admin/store/add");
		} else {
			try {
				String imagePath = storeImagesPath + file.getOriginalFilename();
				String type = file.getContentType().split("/")[1];
				String newImageName = UUID.randomUUID().toString() + "." + type;

				store.setCategory(categoryService.one((categoryId)));
				store.setUser(userService.findById(userId));
				// Get the file and save it somewhere
				byte[] bytes = file.getBytes();
				Path path = Paths.get(imagePath);

				Files.write(path.resolveSibling(newImageName), bytes);

				store.setImage("uploads/stores/" + newImageName);
			} catch (IOException e) {
				e.printStackTrace();
			}

			storeService.save(store);
		}
		return modelAndView;
	}

	@GetMapping(value = "admin/dashboard/store/edit/{id}")
	public ModelAndView edit(@PathVariable("id") int id) {
		ModelAndView modelAndView = new ModelAndView();
		Store store = storeService.one(id);
		if (store == null) {
			return new ModelAndView("redirect:/admin/dashboard/stores");
		}
		modelAndView.addObject("categories", categoryService.getAllCategories());
		modelAndView.addObject("user",
				userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName()));

		ClassLoader classLoader = getClass().getClassLoader();
		modelAndView.addObject("store", store);
		modelAndView.setViewName("admin/store/edit");
		return modelAndView;
	}

}
