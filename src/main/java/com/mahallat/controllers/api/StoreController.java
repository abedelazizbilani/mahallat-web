package com.mahallat.controllers.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mahallat.entity.Store;
import com.mahallat.services.IStoreService;

@RestController
@RequestMapping(value = "api")
@CrossOrigin
public class StoreController {
	@Autowired
	private IStoreService storeService;

	@GetMapping("store/{id}")
	public ResponseEntity<Store> one(@PathVariable("id") Integer id) {
		Store store = storeService.one(id);
		return new ResponseEntity<Store>(store, HttpStatus.OK);
	}

	@GetMapping("stores")
	public ResponseEntity<List<Store>> getAllStores() {
		List<Store> list = storeService.getAllStores();
		return new ResponseEntity<List<Store>>(list, HttpStatus.OK);
	}
}
