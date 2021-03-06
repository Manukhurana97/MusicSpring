package com.example.demo.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private static final String Admin = "ADMIN";
	private static final String Staff = "STAFF";
	private static final String User = "USER";
	private static final String Artist = "ARTIST";
	
	@Autowired
	public SecurityJwtFilters securityJwtFilters;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
//		.antMatchers("/Account/Account/CheckTokenWeb" ).hasAnyRole(Admin, Staff, User, Artist)
		.antMatchers("/AdminServer/**").hasAnyRole(Admin, Staff)
		.antMatchers("/Song/**").hasAnyRole(Admin, Staff, User, Artist)
		.antMatchers("/Playlist/**").hasAnyRole(Admin, Staff, User, Artist)
		.antMatchers("/Artist/**").hasAnyRole(Admin, Staff, User, Artist)
		.antMatchers("/Account/**",  "/actuator","/actuator/health").permitAll()
		.anyRequest().authenticated().and().exceptionHandling()
		.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(securityJwtFilters, UsernamePasswordAuthenticationFilter.class);
		
	}
}
