package com.example.bank.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bank.entities.UsersEntity;
import com.example.bank.service.UserService;

@RestController
public class UsersController {

	@Autowired
	UserService userService;
	
	@GetMapping("/hello")
	public String Home() {
		return "hello dear";
	}
	
	
	@GetMapping("/profile")
	public UsersEntity getUserFromToken() {		
		return userService.getUserFromToken();
	}
	
	
	
}
