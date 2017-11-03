package com.minsx.authorization.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@Order(-20)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private LoginSuccessHandler loginSuccessHandler;

	@Autowired
	private LoginFailHandler loginFailHandler;
	
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
        	.antMatchers("/login**","/oauth/authorize", "/oauth/confirm_access")
        	.and()
        .authorizeRequests()
        	.anyRequest().authenticated();
    }
	
	@Override
	 public void configure(WebSecurity web) throws Exception {
	        web.ignoring().antMatchers("/static/**","/css/**","/js/**","/font-awesome/**","/images/**");
	 }

}
