package com.example.apachecamelmicroservicea.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class EIPPatternRoute extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		from("timer:EIP?period=10000")
		.routeId("Multicast EIP")
		.transform().constant("MyConstant")
		.to("direct:routeMulticast1","direct:routeMulticast2")
		.multicast()
		.to("direct:routeMulticast3","direct:routeMulticast4","direct:routeMulticast5")
		.to("direct:consolelog");
		
		
		from("direct:routeMulticast1")
		.transform().constant("MyConstantRoute1")
		.to("direct:consolelog");
		
		from("direct:routeMulticast2")
		.transform().constant("MyConstantRoute2")
		.to("direct:consolelog");
		
		from("direct:routeMulticast3")
		.transform().constant("MyConstantRoute3")
		.to("direct:consolelog");
		
		from("direct:routeMulticast4")
		.transform().constant("MyConstantRoute4")
		.to("direct:consolelog");
		
		from("direct:routeMulticast5")
		.transform().constant("MyConstantRoute5")
		.to("direct:consolelog");
		
		
	}

}
