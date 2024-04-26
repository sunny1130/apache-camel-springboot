package com.example.apachecamelmicroservicea.routes;

import java.util.List;
import java.util.Map;

import org.apache.camel.Body;
import org.apache.camel.ExchangeProperties;
import org.apache.camel.Headers;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EIPPatternRoute extends RouteBuilder{

	
	@Autowired SplitterComponent splitterComponent;
	@Autowired DynamicRouterBean dynamicRouterBean;
	@Override
	public void configure() throws Exception {
		//Multicast EIP
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
		
		
		//Content Based Routing EIP
		from("file:files/input")
		.routeId("Files-Input-Route")
		.transform().body(String.class)
		.choice()
			.when(simple("${file:ext} == 'xml'"))
				.log("XML File")
			.otherwise()
				.log("Not XML File")
		.end()
		.to("file:files/output");
		
		//Splitter EIP
//		from("file:files/csv")
//		.to("log:split-csv")
//		.unmarshal().csv()
//		.to("log:split-csv")
//		.split(body())
//		.to("log:split-csv");
		
		//Splitter EIP
		from("file:files/csv")
		.to("log:split-csv")
		.convertBodyTo(String.class)
		.to("log:split-csv")
		.split(body())
		.to("log:split-csv");
		
		//Splitter EIP
//		from("file:files/csv")
//		.to("log:split-csv")
//		.convertBodyTo(String.class)
//		.to("log:split-csv")
//		.split(method(splitterComponent))
//		.to("log:split-csv");
		
		
		//Aggregate EIP
		
		
		
		//RoutingSlip EIP
		String endpointConfig = "direct:RoutingSlip1,direct:RoutingSlip3";
		from("timer:routing-slip-timer?period=10000")
		.routingSlip(simple(endpointConfig));
		
		from("direct:RoutingSlip1")
		.transform().constant("RoutingSlip1")
		.to("log:RoutingSlip1");
		
		from("direct:RoutingSlip2")
		.transform().constant("RoutingSlip2")
		.to("log:RoutingSlip2");
		
		from("direct:RoutingSlip3")
		.transform().constant("RoutingSlip3")
		.to("log:RoutingSlip3");
		
		//Dynamic Routing EIP
		from("timer:dynamic-routing-timer?period=2000")
		.dynamicRouter(method(dynamicRouterBean));
		
		from("direct:DynamicRouting1")
		.transform().constant("DynamicRouting1")
		.to("{{endpoint-camel-DynamicRouting1}}");
		
		from("direct:DynamicRouting2")
		.transform().constant("DynamicRouting2")
		.to("log:DynamicRouting2");
		
		from("direct:DynamicRouting3")
		.transform().constant("DynamicRouting3")
		.to("log:DynamicRouting3");
	}

}


@Component
class SplitterComponent{
	public List<String> split(@Body String body){
		return List.of("Abc","Def");
	}
}

@Component
class DynamicRouterBean{
	
	
	int invocations ;
	
	public String decideTheNextEndpoint(
				@ExchangeProperties Map<String, String> properties,
				@Headers Map<String, String> headers,
				@Body String body
			) {

		invocations++;
		
		if(invocations%3==0)
			return "direct:DynamicRouting1";
		
		if(invocations%3==1)
			return "direct:DynamicRouting2,direct:DynamicRouting3";
		
		return null;
			
		
	}
}
