package com.example.apachecamelmicroservicea.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class MyRESTRoute extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		
		restConfiguration().host("localhost").port(8081);
		
		from("timer:rest-timer?period=10000")
		.to("direct:consolelog")
		.to("rest:get:/hello")
		.to("direct:consolelog")
		.to("rest:get:/mytest")
		.to("direct:consolelog");
		
		from("direct:consolelog")
		.to("log:log");
		
	}

}
