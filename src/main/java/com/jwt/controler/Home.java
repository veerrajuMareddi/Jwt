package com.jwt.controler;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Home {
	
	
	@RequestMapping("/access")
	public String welcome()
	{
		return "JWT provided your access";
		
	}

}
