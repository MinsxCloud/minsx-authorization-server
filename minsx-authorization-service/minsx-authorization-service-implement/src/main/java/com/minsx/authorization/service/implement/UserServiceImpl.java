package com.minsx.authorization.service.implement;

import com.minsx.authorization.api.UserService;
import com.minsx.authorization.common.util.MailUtil;
import com.minsx.authorization.common.util.RequestUtil;
import com.minsx.authorization.entity.base.type.UserState;
import com.minsx.authorization.entity.system.DevUser;
import com.minsx.authorization.entity.system.User;
import com.minsx.authorization.plugin.geetest.GeeTestUtil;
import com.minsx.authorization.repository.developer.DevUserRepository;
import com.minsx.authorization.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final DevUserRepository devUserRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private final static String EMAIL_CODE = "EmailCode";

    @Autowired
    private MailUtil mailUtil;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, DevUserRepository devUserRepository) {
        this.userRepository = userRepository;
        this.devUserRepository = devUserRepository;
    }

    @Override
    public void verifyAuthBeforeGetToken(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String[] userNames = request.getParameterMap().get("username");
        String username = userNames == null ? null : userNames[0];
        String[] resources = request.getParameterMap().get("resource");
        String resource = resources == null ? null : resources[0];
        if ("web".equals(resource)) {
            int result = GeeTestUtil.verify(request);
            if (result == 1) {
                User user = getUserByUsernameOrEmailOrPhone(username);
                if (user == null) {
                    RequestUtil.responseJson(HttpStatus.NOT_FOUND, "用户不存在");
                    return;
                }
                chain.doFilter(request, response);
            } else {
                RequestUtil.responseJson(HttpStatus.BAD_REQUEST, "无效验证");
            }
        } else {
            DevUser devUser = devUserRepository.findByAccessKey(username);
            if (devUser == null) {
                RequestUtil.responseJson(HttpStatus.NOT_FOUND, "用户不存在");
                return;
            }
            chain.doFilter(request, response);
        }
    }

    @Override
    public ResponseEntity<?> register(HttpServletRequest request, String username, String email, String password) {
        int result = GeeTestUtil.verify(request);
        if (result == 1) {
            if (getUserByUsernameOrEmailOrPhone(username) != null) {
                return new ResponseEntity<>("该用户名已被注册", HttpStatus.BAD_REQUEST);
            }
            if (userRepository.findByEmail(email) != null) {
                return new ResponseEntity<>("该邮箱已被注册", HttpStatus.BAD_REQUEST);
            }
            if (StringUtils.isEmpty(username)) {
                return new ResponseEntity<>("用户名不能为空", HttpStatus.BAD_REQUEST);
            }
            if (StringUtils.isEmpty(email)) {
                return new ResponseEntity<>("邮箱不能为空", HttpStatus.BAD_REQUEST);
            }
            if (StringUtils.isEmpty(password)) {
                return new ResponseEntity<>("密码不能为空", HttpStatus.BAD_REQUEST);
            }
            if (username.length() < 2 || username.length() > 16) {
                return new ResponseEntity<>("用户名长度不合法", HttpStatus.BAD_REQUEST);
            }
            if (password.length() < 8 || password.length() > 16) {
                return new ResponseEntity<>("密码长度不合法", HttpStatus.BAD_REQUEST);
            }

            User user = new User();
            user.setUserName(username);
            user.setEmail(email);
            user.setPassWord(passwordEncoder.encode(password));
            user.setState(UserState.NORMAL);
            user.setRegisterIp(request.getRemoteAddr());
            user.setRegisterTime(LocalDateTime.now());
            userRepository.save(user);

            return new ResponseEntity<>("激活链接已发送到您的邮箱,请注意查收",HttpStatus.OK);
        } else {
            return new ResponseEntity<>("无效验证", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> getEmailCode(HttpServletRequest request, String username) {
        int result = GeeTestUtil.verify(request);
        if (result == 1) {
            User user = getUserByUsernameOrEmailOrPhone(username);
            if (user == null) {
                return new ResponseEntity<>("账号不存在", HttpStatus.BAD_REQUEST);
            }
            if (StringUtils.isEmpty(user.getEmail())) {
                return new ResponseEntity<>("该帐号未设置邮箱无法发送验证码", HttpStatus.BAD_REQUEST);
            }

            String code = String.valueOf(new Random().nextInt(999999));
            request.getSession().setAttribute(EMAIL_CODE, code);
            String content = String.format("您正在米斯云平台修改密码\n您的验证码是: %s\n温馨提示: 如非您本人操作,请忽略该邮件!", code);
            boolean isSuccess = mailUtil.sendMail(user.getEmail(), user.getUserNick(), "修改密码", content);
            if (isSuccess) {
                return new ResponseEntity<>("邮件发送成功", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("邮件发送失败", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("无效验证", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public User getUserByUsernameOrEmailOrPhone(String username) {
        User user = userRepository.findByUserName(username);
        if (user == null) {
            user = userRepository.findByEmail(username);
        }
        if (user == null) {
            user = userRepository.findByPhone(username);
        }
        return user;
    }

    @Override
    public ResponseEntity<?> changePass(HttpServletRequest request, String username, String password, String emailCode) {

        if (StringUtils.isEmpty(username)) {
            return new ResponseEntity<>("用户名不能为空", HttpStatus.BAD_REQUEST);
        }
        if (StringUtils.isEmpty(password)) {
            return new ResponseEntity<>("密码不能为空", HttpStatus.BAD_REQUEST);
        }
        if (StringUtils.isEmpty(emailCode)) {
            return new ResponseEntity<>("验证码不能为空", HttpStatus.BAD_REQUEST);
        }
        String sessionEmailCode = request.getSession().getAttribute(EMAIL_CODE).toString();
        if (sessionEmailCode != null && sessionEmailCode.equals(emailCode)) {
            User user = getUserByUsernameOrEmailOrPhone(username);
            if (user == null) {
                return new ResponseEntity<>("账号不存在", HttpStatus.BAD_REQUEST);
            }
            if (passwordEncoder.matches(password, user.getPassword())) {
                return new ResponseEntity<>("该密码与当前密码相同无需更改", HttpStatus.BAD_REQUEST);
            }
            user.setPassWord(passwordEncoder.encode(password));
            userRepository.save(user);
            request.getSession().removeAttribute(EMAIL_CODE);
        } else {
            return new ResponseEntity<>("验证码不正确", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
