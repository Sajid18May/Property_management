package com.userservice.service;

import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.userservice.dto.APIResponse;
import com.userservice.dto.AppUserDto;
import com.userservice.entity.AppUser;
import com.userservice.repository.AppUserRepository;

@Service
public class AppUserService {
	
	private AppUserRepository appUserRepository;
	private PasswordEncoder passwordEncoder;
	
	
	public AppUserService(AppUserRepository appUserRepository,PasswordEncoder passwordEncoder) {
		this.appUserRepository = appUserRepository;
		this.passwordEncoder = passwordEncoder;
	}


	public APIResponse<String> registerAppUser(AppUserDto appUserDto){
		APIResponse<String> response=new APIResponse<>();
		if(appUserRepository.existsByUsername(appUserDto.getUsername())) {
			response.setMessage("Duplicate Username");
			response.setStatus(409);
			response.setData("Username Already Exists");
			return response;
		}
		if(appUserRepository.existsByEmail(appUserDto.getEmail())) {
			response.setMessage("Duplicate Email");
			response.setStatus(409);
			response.setData("Email Already Exists");
			return response;
		}
		AppUser appUser=new AppUser();
		BeanUtils.copyProperties(appUserDto, appUser);
		appUser.setPassword(passwordEncoder.encode(appUserDto.getPassword()));
		appUserRepository.save(appUser);
		response.setMessage("Success");
		response.setStatus(201);
		response.setData("User Registered Successfully");
		return response;
	}

}
