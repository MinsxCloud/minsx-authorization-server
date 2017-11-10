package com.minsx.authorization.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

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
        .authorizeRequests()
            .antMatchers("/**").permitAll()
        .and()
        .requestMatchers()
        	.antMatchers("/login**","/oauth/authorize", "/oauth/confirm_access")
        	.and()
        .authorizeRequests()
        	.anyRequest().authenticated();
    }
	
	@Override
	 public void configure(WebSecurity web) throws Exception {
	        web.ignoring().antMatchers("/static/**","/css/**","/js/**","/font/**","/images/**");
	 }

	/**
	 * 主要用于JS的跨域请求
	 */
	@Bean
	public FilterRegistrationBean corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOrigin("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		source.registerCorsConfiguration("/**", config);
		FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return bean;
	}

}
