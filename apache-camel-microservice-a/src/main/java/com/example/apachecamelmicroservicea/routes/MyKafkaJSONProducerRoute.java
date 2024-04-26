package com.example.apachecamelmicroservicea.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

//@Component
public class MyKafkaJSONProducerRoute extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		from("timer:kafka-json-timer")
		.transform().constant("{\"id\":\"100\",\"test\":\"JSON message\"}")
		.to("kafka:myJSONkafkatopic");
		
	}

}
