package com.mahallat.controllers.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mahallat.entity.Settings;
import com.mahallat.services.ISettingsService;

@RestController
@RequestMapping("api")
@CrossOrigin
public class SettingsController {
	@Autowired
	private ISettingsService settingService;
	
	@GetMapping("settings")
	public ResponseEntity<List<Settings>> getAllSettings(){
		List<Settings> list = settingService.getAllSettings();
		if (list.isEmpty()) {
			return new ResponseEntity<List<Settings>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Settings>>(list,HttpStatus.OK);
	}
}
