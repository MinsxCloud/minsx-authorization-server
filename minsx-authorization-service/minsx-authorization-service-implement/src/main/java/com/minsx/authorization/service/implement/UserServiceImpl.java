package com.minsx.authorization.service.implement;

import com.minsx.authorization.entity.base.type.UserState;
import com.minsx.authorization.service.util.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.minsx.authorization.entity.system.User;
import com.minsx.authorization.repository.user.UserRepository;
import com.minsx.authorization.api.UserService;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<?> register(String username, String password) {
        if (userRepository.findByUserName(username) != null) {
            return new ResponseEntity<>("用户名已存在", HttpStatus.BAD_REQUEST);
        }
        if (StringUtils.isEmpty(username)) {
            return new ResponseEntity<>("用户名不能为空", HttpStatus.BAD_REQUEST);
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
        user.setPassWord(passwordEncoder.encode(password));
        user.setState(UserState.NORMAL);
        user.setLastLoginIp("210.121.32.56");
        user.setRegisterIp("210.121.32.83");
        user.setLastLoginTime(LocalDateTime.now());
        user.setRegisterTime(LocalDateTime.now());
        userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getEmailCode(String username) {
        User user = userRepository.findByUserName(username);
        if (user == null) {
            return new ResponseEntity<>("账号不存在", HttpStatus.BAD_REQUEST);
        }
        if (StringUtils.isEmpty(user.getEmail())) {
            return new ResponseEntity<>("该帐号未设置邮箱无法发送验证码", HttpStatus.BAD_REQUEST);
        }

        String code = String.valueOf(new Random().nextInt(999999));
        boolean isSuccess = MailUtil.sendEmail(user.getEmail(), "米斯云平台-找回密码", String.format("您正在申请找回密码 验证码为 %s\n如果非您本人申请请忽略该邮件", code));
        if (isSuccess) {
            return new ResponseEntity<>("邮件发送成功", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("邮件发送失败", HttpStatus.BAD_REQUEST);
        }
    }


}
