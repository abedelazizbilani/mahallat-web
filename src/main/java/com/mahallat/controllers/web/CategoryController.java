package com.mahallat.controllers.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.mahallat.entity.Category;
import com.mahallat.entity.User;
import com.mahallat.services.CategoryService;

@Controller("web-controller")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@GetMapping(value = "/admin/dashboard/categories")
	public ModelAndView categories() {
		ModelAndView modelAndView = new ModelAndView();
		List<Category> categoryList = categoryService.getAllCategories();
		modelAndView.addObject("categories", categoryList);
		modelAndView.setViewName("admin/category/index");
		return modelAndView;
	}

	@GetMapping("/admin/dashboard/category/edit/{id}")
	public ModelAndView update(@PathVariable("id") int id) {
		ModelAndView modelAndView = new ModelAndView();
		Category category = categoryService.one(id);
		if (category == null) {
			return new ModelAndView("redirect:/admin/dashboard/categories");
		}
		modelAndView.addObject("category", category);
		modelAndView.setViewName("admin/category/edit");
		return modelAndView;
	}

	@PostMapping("/admin/dashboard/category/edit")
	public ModelAndView update(@Valid Category category, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView("redirect:/admin/dashboard/categories");
		Category categoryExists = categoryService.one(category.getId());
		if (categoryExists == null) {
			return new ModelAndView("redirect:/admin/dashboard/categories");
		}
		if (bindingResult.hasErrors()) {
			modelAndView.addObject("category", category);
			modelAndView.setViewName("admin/category/edit");
		} else {
			categoryExists.setName(category.getName());
			categoryService.update(category);
		}
		return modelAndView;
	}

	@GetMapping(value = "/admin/dashboard/category/add")
	public ModelAndView add() {
		ModelAndView modelAndView = new ModelAndView();
		Category category = new Category();
		modelAndView.addObject("category", category);
		modelAndView.setViewName("admin/category/add");
		return modelAndView;
	}

	@PostMapping(value = "/admin/dashboard/category/add")
	public ModelAndView add(@Valid Category category, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView("redirec:/admin/dashboard/categories");
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("admin/category/add");
		} else {
			categoryService.save(category);
			modelAndView.addObject("successMessage", "Category has been registered successfully");
			modelAndView.addObject("category", new Category());
			modelAndView.setViewName("admin/category/add");
		}
		return modelAndView;
	}
}
