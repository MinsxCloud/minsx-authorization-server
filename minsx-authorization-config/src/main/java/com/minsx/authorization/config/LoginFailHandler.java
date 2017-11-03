package com.minsx.authorization.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component
public class LoginFailHandler extends SimpleUrlAuthenticationFailureHandler {

    private final Log logger = LogFactory.getLog(this.getClass());

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
    	Map<String, Object> result = new HashMap<>();
    	result.put("isSuccess", false);
        if (exception instanceof LockedException) {
            // 如果账户被锁定
            logger.info("LoginFailHandler the user is locked!");
            result.put("msg", "该用户被锁定！");
        } else if (exception instanceof UsernameNotFoundException) {
            logger.info("LoginSuccessHandler login fail!");
            result.put("msg", "该帐号不存在！");
        } else {
            logger.info("LoginFailHandler login fail!");
            result.put("msg", "用户名或密码不正确！");
        }
        HttpServletRequestUtil.responseJson(response, result);
    }


}
