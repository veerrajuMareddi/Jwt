package com.jwt.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.config.CustomerUserDetailsService;
import com.jwt.helper.JwtUtil;
import com.jwt.model.JwtRequest;
import com.jwt.model.JwtResponse;

@RestController
public class jwt {
	
	@Autowired
	private AuthenticationManager manager  ;
		
		
	
	@Autowired
	private CustomerUserDetailsService customerUserDetailsService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@RequestMapping("/login")
	public ResponseEntity<?>generateToken(@RequestBody JwtRequest obj)
	{
		 try {
			 manager.authenticate(new UsernamePasswordAuthenticationToken(obj.getUsername(), obj.getPassword()));
		 }
		 catch (UsernameNotFoundException e) {
			// TODO: handle exception
		}
		 catch (Exception e) {
			throw new BadCredentialsException("unkown user");
		}
		 UserDetails userdetails=customerUserDetailsService.loadUserByUsername(obj.getUsername());
		 String token=jwtUtil.generateToken(userdetails);
		 
		 return ResponseEntity.ok(new JwtResponse(token));
	}
	

}
