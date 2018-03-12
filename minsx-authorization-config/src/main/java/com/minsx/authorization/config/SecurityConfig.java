package com.minsx.authorization.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	UserDetailsService userDetailsService;

	@Autowired
	LoginSuccessHandler loginSuccessHandler;

	@Autowired
	LoginFailHandler loginFailHandler;

	@Override
    protected void configure(HttpSecurity http) throws Exception {
		http
		.csrf().disable()
        .formLogin()
        	.loginPage("/login")
        	.successHandler(loginSuccessHandler)
        	.failureHandler(loginFailHandler)
        	.permitAll()
        	.and()
        .logout()
            .logoutUrl("/logout")
            .permitAll()
            .logoutSuccessUrl("/login")
            .and()
        .requestMatchers()
        	.antMatchers("/oauth/authorize", "/oauth/confirm_access").antMatchers()
			.antMatchers("/login","/system/**","/verify/**")
			.and()
        .authorizeRequests()
			.antMatchers("/login","/system/**","/verify/**")
        	.permitAll()
        	.anyRequest().authenticated().and();
    }

	@Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/static/**","/css/**","/js/**","/frame/**","/fonts/**","/images/**");
    }


    @Autowired
	public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}


}
