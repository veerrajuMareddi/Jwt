package com.jwt.config;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomerUserDetailsService implements UserDetailsService 
{

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
	{

		if (username.equals("veerraj"))
		{
			return new User("veerraj", "2108", new ArrayList<>());
		}

		else
		{
			throw new UsernameNotFoundException("");
		}
	}

}
