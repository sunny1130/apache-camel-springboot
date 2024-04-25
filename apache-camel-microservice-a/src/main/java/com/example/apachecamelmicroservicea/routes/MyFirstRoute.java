package com.example.apachecamelmicroservicea.routes;

import java.time.LocalDateTime;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Component
public class MyFirstRoute extends RouteBuilder{

	@Autowired
	private GetCurrentTimeBean getCurrentTimeBean;
	
	@Autowired
	private SimpleLoggingBean simpleLoggingBean;
	@Override
	public void configure() throws Exception {
		from("timer:first-timer")
		.transform().constant("MyConstant") // Body of message is affected
		.bean(getCurrentTimeBean,"getTime") // Body of message is affected
		.process(new SimpleLogging()) // Body of message is NOT affected
		.bean(simpleLoggingBean) // Body of message is NOT affected
		.to("log:getTime");
		
	}

}


@Component
class GetCurrentTimeBean{
	public LocalDateTime getTime() {
		return LocalDateTime.now();
	}
}


@Component
class SimpleLoggingBean{
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	public void process(String message) {
		logger.info(message);
	}
}

class SimpleLogging implements Processor{
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public void process(Exchange exchange) throws Exception {
		logger.info("{}",exchange.getContext());
		
	}
}

