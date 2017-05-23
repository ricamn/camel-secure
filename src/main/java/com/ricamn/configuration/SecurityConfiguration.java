package com.ricamn.configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@EnableWebSecurity
public class SecurityConfiguration {

	@Bean
	public AccessDecisionManager accessDecisionManager() {
		AffirmativeBased affirmativeBased = new AffirmativeBased(decisionVoters());
		return affirmativeBased;
	}

	public List<AccessDecisionVoter<? extends Object>> decisionVoters() {
		List<AccessDecisionVoter<? extends Object>> decisionVoters = new ArrayList<AccessDecisionVoter<? extends Object>>();
		decisionVoters.add(new RoleVoter());
		return decisionVoters;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationProvider userServiceProvider) {
		List<AuthenticationProvider> providers = new ArrayList<>();
		providers.add(userServiceProvider);
		ProviderManager providerManager = new ProviderManager(providers);
		return providerManager;
	}

	@Bean
	public AuthenticationProvider userServiceProvider(UserDetailsService userDetailsService) {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		return provider;
	}

	@Bean
	public UserDetailsService userDetailsService() {
		List<UserDetails> users = new ArrayList<>(1);
		users.add(new User("user", "pwd", Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"))));
		users.add(new User("admin", "pwd",
				Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"), new SimpleGrantedAuthority("ROLE_ADMIN"))));
		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager(users);
		return manager;
	}

}
