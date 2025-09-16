package com.userservice.controller;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
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

	@PostMapping("/register")
	public ResponseEntity<APIResponse<String>> userRegistration(@RequestBody AppUserDto appUserDto) {
		APIResponse<String> response = appUserService.registerAppUser(appUserDto);
		return new ResponseEntity<APIResponse<String>>(response,HttpStatusCode.valueOf(response.getStatus()));
	}

	@PostMapping("/login")
	public ResponseEntity<APIResponse<String>> userLogin(@RequestBody LoginDto loginDto) {
		APIResponse<String> response = new APIResponse<>();
		UsernamePasswordAuthenticationToken UsernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				loginDto.getUsername(), loginDto.getPassword());
		Authentication authentication = authenticationManager.authenticate(UsernamePasswordAuthenticationToken);
		if (authentication.isAuthenticated()) {
			String jwtToken = jwtService.generateJwtToken(loginDto.getUsername(),
					authentication.getAuthorities().iterator().next().getAuthority());
			response.setMessage("Welcome "+loginDto.getUsername());
			response.setStatus(200);
			response.setData(jwtToken);
			return new ResponseEntity<APIResponse<String>>(response,HttpStatusCode.valueOf(response.getStatus()));
		}
		response.setMessage("Login Failed");
		response.setStatus(401);
		response.setData("Wrong username or Password");
		return new ResponseEntity<APIResponse<String>>(response,HttpStatusCode.valueOf(response.getStatus()));
	}

}
