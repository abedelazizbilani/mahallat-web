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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.mahallat.entity.Product;
import com.mahallat.entity.User;
import com.mahallat.services.ProductService;
import com.mahallat.services.StoreService;
import com.mahallat.services.UserService;

@Controller("web-product-controller")
public class ProductController {

	@Autowired
	StoreService storeService;
	@Autowired
	ProductService productService;
	@Autowired
	UserService userService;

	@Value("${spring.file.uploads.path.products}")
	private String storeImagesPath;

	@GetMapping(value = "/admin/dashboard/{id}/products")
	public ModelAndView index(@PathVariable("id") int id) {
		ModelAndView modelAndView = new ModelAndView();
		List<Product> productList = productService.storeProducts(id);
		modelAndView.addObject("products", productList);
		modelAndView.setViewName("admin/product/index");
		return modelAndView;
	}

	@GetMapping(value = "/admin/dashboard/product/add")
	public ModelAndView add() {

		User user = userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
		// check if user has a store

		// user don't have a store and he can add one
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("product", new Product());
		modelAndView.addObject("store", storeService.storeByUserId(user.getId()));

		modelAndView.setViewName("admin/product/add");
		return modelAndView;
	}

	@PostMapping(value = "/admin/dashboard/product/add")
	public ModelAndView add(@Valid Product product, BindingResult bindingResult, @RequestParam MultipartFile file) {
		ModelAndView modelAndView = new ModelAndView("redirect:/admin/dashboard/product/add");
		if (bindingResult.hasErrors()) {
			User user = userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
			modelAndView.addObject("store", storeService.storeByUserId(user.getId()));
			modelAndView.setViewName("admin/product/add");
		} else {
			try {
				// Get the file and save it somewhere
				String imagePath = storeImagesPath + file.getOriginalFilename();
				String type = file.getContentType().split("/")[1];
				String newImageName = UUID.randomUUID().toString() + "." + type;
				byte[] bytes = file.getBytes();
				Path path = Paths.get(imagePath);
				Files.write(path.resolveSibling(newImageName), bytes);

				product.setImage("uploads/products/" + newImageName);

				product.setActive(product.getActive());
				//product.setStore(product.setStore(store));

			} catch (IOException e) {
				e.printStackTrace();
			}
			//productService.save(product);
		}
		return modelAndView;
	}
}
