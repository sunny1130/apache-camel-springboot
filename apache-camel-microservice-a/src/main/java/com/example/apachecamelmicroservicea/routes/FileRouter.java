package com.example.apachecamelmicroservicea.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

//@Component
public class FileRouter extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		from("file:filesfrom")
		.log("${body}")
		.to("file:target/output");
		
	}

}
