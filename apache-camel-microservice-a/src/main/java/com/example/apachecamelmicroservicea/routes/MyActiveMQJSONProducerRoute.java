package com.example.apachecamelmicroservicea.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class MyActiveMQJSONProducerRoute extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		from("timer:active-mq-json-timer?period=10000")
		.transform().constant("{\"id\":\"100\",\"test\":\"JSON message\"}")
		.to("activemq:myJSONqueue");
		
	}

}
