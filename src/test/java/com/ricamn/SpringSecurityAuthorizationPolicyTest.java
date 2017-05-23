package com.ricamn;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.Subject;

import org.apache.camel.Exchange;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.ricamn.configuration.CamelConfig;
import com.ricamn.configuration.SecurityConfiguration;
import com.ricamn.router.RestletRoute;

public class SpringSecurityAuthorizationPolicyTest extends CamelSpringTestSupport {

	@Test
	public void testAuthorizationPassed() throws Exception {
		MockEndpoint end = getMockEndpoint("mock:end");
		end.expectedBodiesReceived("hello world");
		sendMessageWithAuthentication("admin", "pwd", "ROLE_USER", "ROLE_ADMIN");
		end.assertIsSatisfied();
	}

	@Test
	public void testUserAuthorizationDenied() throws Exception {
		MockEndpoint end = getMockEndpoint("mock:end");
		end.expectedMessageCount(0);

		Authentication authToken = createAuthenticationToken("user", "pwd", "ROLE_USER");
		Subject subject = new Subject();
		subject.getPrincipals().add(authToken);

		template.sendBodyAndHeader("direct:start", "hello world", Exchange.AUTHENTICATION, subject);
		end.assertIsSatisfied();
	}

	private Authentication createAuthenticationToken(String username, String password, String... roles) {
		Authentication authToken;
		if (roles != null && roles.length > 0) {
			List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(roles.length);
			for (String role : roles) {
				authorities.add(new SimpleGrantedAuthority(role));
			}
			authToken = new UsernamePasswordAuthenticationToken(username, password, authorities);
		} else {
			authToken = new UsernamePasswordAuthenticationToken(username, password);
		}
		return authToken;
	}

	private void sendMessageWithAuthentication(String username, String password, String... roles) {

		Authentication authToken = createAuthenticationToken(username, password, roles);

		Subject subject = new Subject();
		subject.getPrincipals().add(authToken);

		template.sendBodyAndHeader("direct:start", "hello world", Exchange.AUTHENTICATION, subject);

	}

	@Override
	protected AbstractApplicationContext createApplicationContext() {
		return new AnnotationConfigApplicationContext(WebConfig.class, SecurityConfiguration.class, CamelConfig.class,
				RestletRoute.class);
	}

}
