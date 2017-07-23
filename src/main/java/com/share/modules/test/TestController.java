package com.share.modules.test;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "test")
public class TestController {

	@RequestMapping(value = "test")
	public String test(){
		System.out.println("成功进入");
		return "成功进入con层";
		
	}
	
	
}
