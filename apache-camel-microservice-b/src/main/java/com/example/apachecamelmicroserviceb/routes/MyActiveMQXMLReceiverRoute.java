package com.example.apachecamelmicroserviceb.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.apachecamelmicroserviceb.beans.MyTest;

@Component
public class MyActiveMQXMLReceiverRoute extends RouteBuilder {
	
	@Autowired Header header;
	@Override
	public void configure() throws Exception {
		from("activemq:myXMLqueue")
		.log("${body}")
		.to("log:Camel")
		.unmarshal().jacksonXml(MyTest.class)
		.log("${body}")
		.to("log:Camel")
		.bean(header);
	}
}