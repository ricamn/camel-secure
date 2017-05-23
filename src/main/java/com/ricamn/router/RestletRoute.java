package com.ricamn.router;

import org.apache.camel.CamelAuthorizationException;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

/**
 * Route component that exposes a rest service
 * 
 * @author Ricardo
 *
 */
@Component
public class RestletRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {

		onException(CamelAuthorizationException.class).handled(true).transform()
				.simple("Access Denied with the Policy of ${exception.policyId} !");

		from("direct:start").policy("admin").to("mock:end");

		// Secure route accepts only ROLE_ADMIN
		from("restlet:/demo/{id}").id("com.ricamn.router.RestletRoute").policy("admin").log("${headers}").transform()
				.simple("Request type : ${header.CamelHttpMethod} and ID : ${header.id}");
	}

}
