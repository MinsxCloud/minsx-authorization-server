package com.minsx.authorization.config;


import com.alibaba.fastjson.JSON;
import com.minsx.authorization.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.FilterConfig;
import java.io.IOException;

@Component
public class LoginFilter  implements Filter {

    @Autowired
    UserService userService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        userService.verifyAuthBeforeGetToken(request,response,chain);
    }


    @Override
    public void destroy() {
    }
}
