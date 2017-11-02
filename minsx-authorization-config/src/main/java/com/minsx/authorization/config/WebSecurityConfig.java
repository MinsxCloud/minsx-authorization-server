package com.minsx.authorization.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@Order(-20)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http
		.csrf().disable()
        .authorizeRequests()
                .antMatchers("/login","/oauth/authorize", "/oauth/confirm_access").permitAll()
                .anyRequest().authenticated()
                .and()
        .formLogin()
               .loginPage("/login")
               .permitAll()
               .and()
        .logout()
               .logoutUrl("/logout")
               .logoutSuccessUrl("/login");
    }
	
	@Override
	 public void configure(WebSecurity web) throws Exception {
	        web.ignoring().antMatchers("/static/**","/css/**","/js/**","/font-awesome/**","/images/**");
	 }

}
