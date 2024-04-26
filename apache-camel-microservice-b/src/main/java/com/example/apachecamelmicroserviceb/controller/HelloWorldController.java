package com.example.apachecamelmicroserviceb.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.apachecamelmicroserviceb.beans.MyTest;

@RestController
public class HelloWorldController {
	
	@GetMapping(path="/hello")
	public String helloWorld() {
		return "Hello World";
	}
	
	@GetMapping(path="/mytest")
	public MyTest myTest() {
		return new MyTest(100L,"hello world");
	}

}
