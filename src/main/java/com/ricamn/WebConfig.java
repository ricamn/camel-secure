package com.ricamn;

import org.restlet.Application;
import org.restlet.Component;
import org.restlet.ext.spring.SpringServerServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {

	@Bean(name = "RestletComponent")
	public Component restletComponent() {
		return new Component();
	}

	@Bean(name = "RestletApplication")
	public Application restletApplication() {
		return new Application();
	}

	/**
	 * Adds SpringServerServlet to make possible to expose restlet within webapp
	 * Refer to http://camel.apache.org/restlet.html
	 * 
	 * @return
	 */
	@Bean
	public ServletRegistrationBean restletServletRegistration() {
		ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new SpringServerServlet(),
				"/rs/*");
		servletRegistrationBean.setName("RestletServlet");
		servletRegistrationBean.addInitParameter("org.restlet.application", "RestletApplication");
		servletRegistrationBean.addInitParameter("org.restlet.component", "RestletComponent");
		return servletRegistrationBean;
	}
}
