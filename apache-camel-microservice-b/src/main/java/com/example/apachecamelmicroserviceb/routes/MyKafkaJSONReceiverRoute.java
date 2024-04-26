package com.example.apachecamelmicroserviceb.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.apachecamelmicroserviceb.beans.MyTest;

//@Component
public class MyKafkaJSONReceiverRoute extends RouteBuilder{

	@Autowired Header header;
	@Override
	public void configure() throws Exception {
		from("kafka:myJSONkafkatopic")
		.log("${body}")
		.to("log:Camel")
		.unmarshal().json(JsonLibrary.Jackson,MyTest.class)
		.log("${body}")
		.to("log:Camel")
		.bean(header);
	}
}
