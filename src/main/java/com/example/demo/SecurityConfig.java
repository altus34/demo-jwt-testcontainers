package com.example.demo;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import static org.springframework.http.HttpMethod.GET;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http.cors()
            .and()
                .authorizeRequests()
                .antMatchers(GET, "/users/me").permitAll()
                .antMatchers(GET, "/users/me2")
                .hasAuthority("SCOPE_ADMIN")
                .anyRequest()
                .authenticated()
            .and()
                .oauth2ResourceServer().jwt();
        // @formatter:on
    }
}
