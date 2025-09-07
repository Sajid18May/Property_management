package com.userservice.controller;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.userservice.dto.APIResponse;
import com.userservice.dto.AppUserDto;
import com.userservice.dto.LoginDto;
import com.userservice.service.AppUserService;
import com.userservice.service.JwtService;

@RestController
public class AppUserController {

	private AppUserService appUserService;
	private JwtService jwtService;
	private AuthenticationManager authenticationManager;

	public AppUserController(AppUserService appUserService, JwtService jwtService,
			AuthenticationManager authenticationManager) {
		this.appUserService = appUserService;
		this.jwtService = jwtService;
		this.authenticationManager = authenticationManager;
	}

	@GetMapping("/register")
	public ResponseEntity<APIResponse<String>> userRegistration(@RequestBody AppUserDto appUserDto) {
		APIResponse<String> response = appUserService.registerAppUser(appUserDto);
		return new ResponseEntity<APIResponse<String>>(HttpStatusCode.valueOf(response.getStatus()));
	}

	@GetMapping("/login")
	public ResponseEntity<APIResponse<String>> userLogin(@RequestBody LoginDto loginDto) {
		APIResponse<String> response = new APIResponse<>();
		UsernamePasswordAuthenticationToken UsernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				loginDto.getUsername(), loginDto.getPassword());
		Authentication authentication = authenticationManager.authenticate(UsernamePasswordAuthenticationToken);
		if (authentication.isAuthenticated()) {
			jwtService.generateJwtToken(loginDto.getUsername(),
					authentication.getAuthorities().iterator().next().getAuthority());
			response.setMessage("Login Successful");
			response.setStatus(200);
			response.setData("Welcome "+loginDto.getUsername());
			return new ResponseEntity<APIResponse<String>>(HttpStatusCode.valueOf(response.getStatus()));
		}
		response.setMessage("Login Failed");
		response.setStatus(401);
		response.setData("Wrong username or Password");
		return new ResponseEntity<APIResponse<String>>(HttpStatusCode.valueOf(response.getStatus()));
	}

}
