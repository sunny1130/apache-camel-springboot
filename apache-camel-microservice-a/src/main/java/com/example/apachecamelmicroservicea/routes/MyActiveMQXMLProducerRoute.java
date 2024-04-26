package com.example.apachecamelmicroservicea.routes;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

//@Component
public class MyActiveMQXMLProducerRoute  extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		from("timer:active-mq-xml-timer?period=10000")
		.transform().constant(""
				+ "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n"
				+ "<root>\r\n"
				+ "   <id>1001</id>\r\n"
				+ "   <test>XML Message</test>\r\n"
				+ "</root>")
		.to("activemq:myXMLqueue");
		
	}
}
