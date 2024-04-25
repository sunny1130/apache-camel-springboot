package com.example.apachecamelmicroserviceb.routes;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.apachecamelmicroserviceb.beans.MyTest;

@Component
public class MyActiveMQJSONReceiverRoute extends RouteBuilder{

	@Autowired Header header;
	@Override
	public void configure() throws Exception {
		from("activemq:myJSONqueue")
		.log("${body}")
		.to("log:Camel")
		.unmarshal().json(JsonLibrary.Jackson,MyTest.class)
		.log("${body}")
		.to("log:Camel")
		.bean(header);
	}
}

@Component
class Header {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	public void addHeader(Exchange ex) {
		logger.info("{}",ex.getIn().getHeaders());
		ex.getIn().setHeader("Origin", "http://localhost:8080");
		logger.info("{}",ex.getIn().getHeaders());
	}
}