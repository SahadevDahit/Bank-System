package com.example.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.bank.entities.UsersEntity;
import com.example.bank.repositories.UsersRepository;

@Service
public class UserService {
	
	@Autowired
	private UsersRepository usersRepo;

	
	public UsersEntity getUserFromToken() {
		 try {
			 UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				UsersEntity user = usersRepo.findByEmail(userDetails.getUsername());
	           
	            if (user == null) {
	                throw new RuntimeException("User not found");
	            }
	            return user;
	        } catch (Exception e) {
	            throw new RuntimeException("Error retrieving user from token", e);
	        }

	}

}
