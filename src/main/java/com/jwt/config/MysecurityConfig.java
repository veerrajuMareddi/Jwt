package com.jwt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class MysecurityConfig extends WebSecurityConfigurerAdapter
{
	@Autowired
	private CustomerUserDetailsService customerUserDetailsService;
	
	@Autowired
	private JwtAuthenticationFilter jwtfilter;
	
	@Override
	protected void configure(HttpSecurity http)throws Exception{
		http
		.csrf()
		.disable()
		.cors()
		.disable()
		.authorizeRequests()
		.anyRequest().authenticated()
		.antMatchers("/login").permitAll()
		.and()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

	http.addFilterBefore(jwtfilter,UsernamePasswordAuthenticationFilter.class);
	
	}

	
	@Override
	protected void configure (AuthenticationManagerBuilder auth)throws Exception
	{
	 	auth.userDetailsService(customerUserDetailsService);
	}
	
	@Bean
	public PasswordEncoder paswoed()
	{
		return NoOpPasswordEncoder.getInstance();
	}
	
	@Bean
	public AuthenticationManager manager() throws Exception
	{
		return super.authenticationManager();
	}
	
	
	
	
}
