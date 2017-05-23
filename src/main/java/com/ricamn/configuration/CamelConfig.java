package com.ricamn.configuration;

import org.apache.camel.component.spring.security.SpringSecurityAccessPolicy;
import org.apache.camel.component.spring.security.SpringSecurityAuthorizationPolicy;
import org.apache.camel.spring.javaconfig.CamelConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.authentication.AuthenticationManager;

/**
 * Camel java configuration. Refer to
 * http://camel.apache.org/spring-java-config.html
 * 
 * @author Ricardo
 *
 */
@Configuration
@ComponentScan("com.ricamn.router")
public class CamelConfig extends CamelConfiguration {

	/**
	 * Adds role-based authorization for camel routes. Refer to
	 * http://camel.apache.org/spring-security.html
	 * 
	 * @param authenticationManager
	 * @param accessDecisionManager
	 * @return
	 */
	@Bean(name = "admin")
	public SpringSecurityAuthorizationPolicy configureAdminCamelAuthPolicy(AuthenticationManager authenticationManager,
			AccessDecisionManager accessDecisionManager) {
		SpringSecurityAuthorizationPolicy camelAuthPolicy = new SpringSecurityAuthorizationPolicy();
		camelAuthPolicy.setId("admin");
		camelAuthPolicy.setAuthenticationManager(authenticationManager);
		camelAuthPolicy.setAccessDecisionManager(accessDecisionManager);
		camelAuthPolicy.setSpringSecurityAccessPolicy(new SpringSecurityAccessPolicy("ROLE_ADMIN"));
		return camelAuthPolicy;
	}

	@Bean(name = "user")
	public SpringSecurityAuthorizationPolicy configureUserCamelAuthPolicy(AuthenticationManager authenticationManager,
			AccessDecisionManager accessDecisionManager) {
		SpringSecurityAuthorizationPolicy camelAuthPolicy = new SpringSecurityAuthorizationPolicy();
		camelAuthPolicy.setId("user");
		camelAuthPolicy.setAuthenticationManager(authenticationManager);
		camelAuthPolicy.setAccessDecisionManager(accessDecisionManager);
		camelAuthPolicy.setSpringSecurityAccessPolicy(new SpringSecurityAccessPolicy("ROLE_USER"));
		return camelAuthPolicy;
	}
}
