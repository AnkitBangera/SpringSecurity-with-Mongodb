package com.SpringSecurity.Util;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.SpringSecurity.Filters.JwtRequestFilters;
import com.google.gson.Gson;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	UserDetailsService userDetailsService;

	@Autowired
	private JwtRequestFilters jwtRequestFilters;
	
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}

	protected void configure(HttpSecurity http) throws Exception {
		/*
		 * http.authorizeRequests() .antMatchers("/admin").hasRole("ADMIN")
		 * .antMatchers("/user").hasAnyRole("ADMIN","USER")
		 * .antMatchers("/").permitAll() .and().formLogin();
		 */
		http.csrf().disable().authorizeRequests().antMatchers("/authentication").permitAll().anyRequest()
				.authenticated().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(jwtRequestFilters, UsernamePasswordAuthenticationFilter.class);
	}

	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
}
