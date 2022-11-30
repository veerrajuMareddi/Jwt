package com.jwt.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.jwt.helper.JwtUtil;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	
	@Autowired
	private JwtUtil jwtutil;
	
	@Autowired
	private CustomerUserDetailsService customerUserDetailsService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		//get jwt header
		//Bearer
		//validate
		
  String header=  request.getHeader("Authorization")	;	
  String username=null;
  String jwtToken=null;
  if(header!=null && header.startsWith("Bearer "))
  {
	  jwtToken=header.substring(7);
	  try
	  {
		 username=jwtutil.getUsernameFromToken(jwtToken);
	  }
	   catch (Exception e) {
		   e.printStackTrace();
	}
	  UserDetails userdetails= customerUserDetailsService.loadUserByUsername(username);
	  
	  if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null)
	  {
		  UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken  =new UsernamePasswordAuthenticationToken(userdetails,null,userdetails.getAuthorities()); 
		  usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		  SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
	  }
	  else {
		  System.out.println("token not validated");
	  }
  }
  filterChain.doFilter(request, response);

  
	}
	

}
