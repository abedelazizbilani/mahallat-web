package com.mahallat.controllers.web;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.mahallat.entity.Category;
import com.mahallat.entity.Product;
import com.mahallat.entity.Store;
import com.mahallat.entity.User;
import com.mahallat.services.CategoryService;
import com.mahallat.services.ProductService;
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
	@Autowired
	ProductService productService;

	@Value("${spring.file.uploads.path.products}")
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
		User user = userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
		// check if user has a store
		boolean hasStore = storeService.userHasStore(user.getId());
		if (hasStore) {
			// return to main dashboard if user tried to add a store and he already has one
			return new ModelAndView("redirect:/admin/dashboard");
		}
		// user don't have a store and he can add one
		ModelAndView modelAndView = new ModelAndView();
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
			// list categories
			List<Category> categoriesList = categoryService.getAllCategories();
			// get logged in user details and id
			User user = userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
			modelAndView.addObject("user", user);
			modelAndView.addObject("categories", categoriesList);
			modelAndView.setViewName("admin/store/add");
		} else {
			try {

				if (!file.isEmpty()) {
					// Get the file and save it somewhere
					String imagePath = storeImagesPath + file.getOriginalFilename();
					String type = file.getContentType().split("/")[1];
					String newImageName = UUID.randomUUID().toString() + "." + type;
					byte[] bytes = file.getBytes();
					Path path = Paths.get(imagePath);
					Files.write(path.resolveSibling(newImageName), bytes);
					store.setImage("uploads/stores/" + newImageName);

				}

				store.setActive(store.getActive());
				store.setCategory(categoryService.one((categoryId)));
				store.setUser(userService.findById(userId));

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
		modelAndView.addObject("store", store);
		modelAndView.setViewName("admin/store/edit");
		return modelAndView;
	}

	@PostMapping(value = "/admin/dashboard/store/edit")
	public ModelAndView update(@Valid Store store, BindingResult bindingResult, @RequestParam MultipartFile file,
			@RequestParam("user_id") Integer userId, @RequestParam("category") Integer categoryId) {
		ModelAndView modelAndView = new ModelAndView("redirect:/admin/dashboard/stores");

		if (bindingResult.hasErrors()) {
			modelAndView.addObject("categories", categoryService.getAllCategories());
			modelAndView.addObject("user",
					userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName()));
			modelAndView.addObject("store", store);
			modelAndView.setViewName("admin/category/edit");
		}

		Store storeExists = storeService.one(store.getId());
		if (storeExists == null) {
			return new ModelAndView("redirect:/admin/dashboard/stores");
		} else {
			store.setImage(storeExists.getImage());
			if (!file.isEmpty()) {
				try {
					String imagePath = storeImagesPath + file.getOriginalFilename();
					String type = file.getContentType().split("/")[1];
					String newImageName = UUID.randomUUID().toString() + "." + type;
					byte[] bytes = file.getBytes();
					Path path = Paths.get(imagePath);
					Files.write(path.resolveSibling(newImageName), bytes);
					store.setImage("uploads/stores/" + newImageName);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			store.setActive(store.getActive() == null ? false : true);
			store.setCategory(categoryService.one((categoryId)));
			storeService.update(store);
		}
		return modelAndView;
	}

	/**
	 * view store
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping(value = "admin/dashboard/store/{id}")
	public ModelAndView view(@PathVariable("id") int id) {
		ModelAndView modelAndView = new ModelAndView();
		Store store = storeService.one(id);
		if (store == null) {
			return new ModelAndView("redirect:/admin/dashboard/stores");
		}
		IntSummaryStatistics stats = store.getStoreRatings().stream().mapToInt((x) -> x.getRate()).summaryStatistics();
		store.likeCount = store.getStoreLikes().size();
		store.averageRating = stats.getAverage();
		
		List<Product> productList = productService.storeProducts(id);
		modelAndView.addObject("products", productList);
		modelAndView.addObject("store", store);
		modelAndView.setViewName("admin/store/view");
		return modelAndView;
	}

}
