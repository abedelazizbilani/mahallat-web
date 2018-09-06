package com.mahallat.controllers.web;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
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
	
	private static String UPLOADED_FOLDER = "C://Projects//mahallat-web//src//main//uploads//stores//";

	
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
	
	@PostMapping(value="/admin/dashboard/store/add")
	public ModelAndView add(@Valid Store store, BindingResult bindingResult , @RequestParam("image") MultipartFile file
           ) {
		ModelAndView modelAndView = new ModelAndView("redirect:/admin/dashboard/stores");
		if (bindingResult.hasErrors()) {
			User user = userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
			modelAndView.addObject("user", user);
			modelAndView.setViewName("admin/store/add");
		} else {
			try {

	            // Get the file and save it somewhere
	            byte[] bytes = file.getBytes();
	            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
	            Files.write(path, bytes);
	            store.setImage(path.toString());
			} catch (IOException e) {
	            e.printStackTrace();
	        }
			
			storeService.save(store);
		}
		return modelAndView;
	}

}
