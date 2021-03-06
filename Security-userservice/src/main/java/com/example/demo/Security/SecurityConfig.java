package com.example.demo.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String User = "USER";
    private static final String Admin = "ADMIN";
    private static final String Artist = "ARTIST";
    private static final String Staff = "STAFF";


    @Autowired
    public DataSource dataSource;

    @Autowired
    public SecurityJwtFilters securityJwtFilters;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers("/Account/CheckToken").hasAnyRole(User, Admin, Artist, Staff)
                .antMatchers("/Account/CheckTokenWeb").hasAnyRole(User, Admin, Artist, Staff)
                .antMatchers("/password/change-password").hasAnyRole(User, Admin, Artist, Staff)
                .antMatchers("/Userdetails/**").hasAnyRole(User, Admin, Artist, Staff)
                .antMatchers("/Account/**", "/password/**").permitAll()
                .antMatchers("/actuator/info", "/actuator/**").permitAll()

                .anyRequest().authenticated().and().exceptionHandling()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(securityJwtFilters, UsernamePasswordAuthenticationFilter.class);

    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JdbcUserDetailsManager detailsManager() {
        System.out.println(dataSource);
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

}
