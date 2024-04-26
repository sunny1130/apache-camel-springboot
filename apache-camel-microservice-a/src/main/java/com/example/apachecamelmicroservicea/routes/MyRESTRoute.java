package com.example.apachecamelmicroservicea.routes;

import java.util.Map;

import org.apache.camel.Body;
import org.apache.camel.Headers;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyRESTRoute extends RouteBuilder{

	@Autowired DeciderBean deciderBean;
	@Override
	public void configure() throws Exception {
		
		restConfiguration().host("localhost").port(8081);
		
		from("timer:rest-timer?period=100000")
		.to("direct:consolelog")
		.choice()
			.when(method(deciderBean,"checknull"))
				.to("rest:get:/hello")
				.to("direct:consolelog")
				.to("rest:get:/mytest")
				.to("direct:consolelog")
			.otherwise()
				.log("Body is null")
		.end();
		
		from("direct:consolelog")
		.to("log:log");
		
	}

}

@Component
class DeciderBean{
	
	public boolean checknull(@Body String body, @Headers Map<String,String> header) {
		if(null != body)
			return true;
		else
			return false;
	}
}
