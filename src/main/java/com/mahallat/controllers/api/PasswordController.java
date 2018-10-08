package com.mahallat.controllers.api;

import java.util.HashMap;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mahallat.models.Register;

@RestController("api-password-controller")
@RequestMapping(value = "api")
@CrossOrigin
public class PasswordController {
	
//	@PostMapping("forgot-password")
//	@ResponseBody
//	public ResponseEntity<HashMap> forgotPassword(@RequestBody @Valid Register register) {
//		
//	}

}
