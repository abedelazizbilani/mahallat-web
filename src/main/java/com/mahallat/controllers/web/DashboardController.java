package com.mahallat.controllers.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DashboardController {

	@GetMapping(value = "/admin/dashboard")
	public ModelAndView home() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin/dashboard");
		return modelAndView;
	}
}
