package com.mahallat.controllers.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.mahallat.services.IStoreService;
import com.mahallat.services.IUserService;
import com.mahallat.services.StoreService;
import com.mahallat.services.UserService;
import com.mahallat.entity.Store;
import com.mahallat.entity.User;

@Controller
public class DashboardController {
	@Autowired
	private IStoreService storeService;
	@Autowired
	private IUserService userService;

	@GetMapping(value = "/admin/dashboard")
	public ModelAndView home() {

		List<Store> stores = storeService.getAllStores();
		ArrayList<HashMap<String, Object>> storesDetails = new ArrayList<>();

		stores.forEach(store -> {
			HashMap<String, Object> storeData = new HashMap<>();
			storeData.put("storeName", store.getName());
			storeData.put("lat", store.getLatitude());
			storeData.put("lng", store.getLongitude());
			storeData.put("id", store.getId());
			storesDetails.add(storeData);
		});
		User user = userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

		boolean hasStore = storeService.userHasStore(user.getId());
		if (!hasStore) {
			// return to main dashboard if user tried to add a store and he already has one
			return new ModelAndView("redirect:/admin/dashboard/store/add");
		}
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("stores", storesDetails);
		//modelAndView.addObject("storeId", user.getStore().getId());
		modelAndView.setViewName("admin/dashboard");
		return modelAndView;
	}
}
