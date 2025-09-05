package com.userservice.service;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.userservice.entity.AppUser;
import com.userservice.repository.AppUserRepository;

@Service
public class LoginService implements UserDetailsService{
	
	private AppUserRepository appUserRepository;
	
	public LoginService(AppUserRepository appUserRepository) {
		super();
		this.appUserRepository = appUserRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		AppUser appUser=appUserRepository.findAppUserByUsername(username);
		return new User(username, appUser.getPassword(), Collections.singleton(new SimpleGrantedAuthority(appUser.getRole())));
	}

}
