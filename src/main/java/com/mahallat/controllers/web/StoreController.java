package com.mahallat.controllers.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

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
	
	@GetMapping("/admin/dashboard/store/add")
	public ModelAndView add() {
		ModelAndView modelAndView = new ModelAndView();
		List<Category> categoriesList = categoryService.getAllCategories();
		
		User user = userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        
        
		modelAndView.addObject("store", new Store());
		modelAndView.addObject("categories", categoriesList);
		modelAndView.addObject("user", user);

		modelAndView.setViewName("admin/store/add");
		return modelAndView;
	}

	@GetMapping("/admin/dashboard/stores")
	public ModelAndView index() {
		ModelAndView modelAndView = new ModelAndView();
		List<Store> storesList = storeService.getAllStores();
		modelAndView.addObject("stores", storesList);
		modelAndView.setViewName("admin/store/index");
		return modelAndView;
	}

}
