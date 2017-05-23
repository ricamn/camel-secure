package com.ricamn;

import org.apache.camel.component.spring.security.SpringSecurityAuthorizationPolicy;
import org.apache.camel.util.IOHelper;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.ricamn.configuration.CamelConfig;
import com.ricamn.configuration.SecurityConfiguration;

public class SpringSecurityAuthorizationPolicyConfigTest extends Assert {

	private AnnotationConfigApplicationContext context;

	@Before
	public void setUp() {
		context = new AnnotationConfigApplicationContext(WebConfig.class, SecurityConfiguration.class,
				CamelConfig.class);
	}

	@After
	public void tearDown() {
		IOHelper.close(context);
	}

	@Test
	public void testAuthorizationPolicy() {

		SpringSecurityAuthorizationPolicy adminPolicy = context.getBean("admin",
				SpringSecurityAuthorizationPolicy.class);
		assertNotNull("We should get admin policy", adminPolicy);
		assertNotNull("The accessDecisionManager should not be null", adminPolicy.getAccessDecisionManager());
		assertNotNull("The authenticationManager should not be null", adminPolicy.getAuthenticationManager());
		assertNotNull("The springSecurityAccessPolicy should not be null", adminPolicy.getSpringSecurityAccessPolicy());

		SpringSecurityAuthorizationPolicy userPolicy = context.getBean("user", SpringSecurityAuthorizationPolicy.class);
		assertNotNull("We should get user policy", userPolicy);
		assertNotNull("The accessDecisionManager should not be null", userPolicy.getAccessDecisionManager());
		assertNotNull("The authenticationManager should not be null", userPolicy.getAuthenticationManager());
		assertNotNull("The springSecurityAccessPolicy should not be null", userPolicy.getSpringSecurityAccessPolicy());

		assertEquals("user policy and admin policy should have same accessDecisionManager",
				adminPolicy.getAccessDecisionManager(), userPolicy.getAccessDecisionManager());
		assertEquals("user policy and admin policy should have same authenticationManager",
				adminPolicy.getAuthenticationManager(), userPolicy.getAuthenticationManager());
	}

}
