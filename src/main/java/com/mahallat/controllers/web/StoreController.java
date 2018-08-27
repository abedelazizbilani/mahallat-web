package com.mahallat.controllers.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.mahallat.entity.Store;
import com.mahallat.services.StoreService;

@Controller("web-store-controller")
public class StoreController {
	@Autowired
	StoreService storeService;
	
	
	@GetMapping("/admin/dashboard/store/add")
	public ModelAndView add() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("store",new Store());
		modelAndView.setViewName("admin/store/add");
		return modelAndView;
	}
	
}
