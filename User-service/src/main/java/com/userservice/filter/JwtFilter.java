package com.userservice.filter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.userservice.service.JwtService;
import com.userservice.service.LoginService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter{
	
	private JwtService jwtService;
	private LoginService loginService;

	public JwtFilter(JwtService jwtService, LoginService loginService) {
		this.jwtService = jwtService;
		this.loginService = loginService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authHeader=request.getHeader("Authorisation");
		if(authHeader!=null && authHeader.startsWith("Bearer ")) {
			String jwtToken=authHeader.substring(7);
			String username=jwtService.findSubjectFromJwtToken(jwtToken);
			if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
				var user=loginService.loadUserByUsername(username);
				var authToken=new UsernamePasswordAuthenticationToken(username, null, user.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
			
		}
		filterChain.doFilter(request, response);
	}

}
