package com.minsx.authorization.api;

import com.minsx.authorization.entity.system.User;
import org.springframework.http.ResponseEntity;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public interface UserService {

    /**
     * 该方法用于登录之前对验证码及用户类型做相关校验
     */
    void verifyAuthBeforeGetToken(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException;

    ResponseEntity<?> register(HttpServletRequest request, String username, String email, String password);

    ResponseEntity<?> getEmailCode(HttpServletRequest request, String username);

    User getUserByUsernameOrEmailOrPhone(String username);

    ResponseEntity<?> changePass(HttpServletRequest request, String username, String password, String emailCode);
}
