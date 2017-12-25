package com.minsx.authorization.service.util;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * MailUtil
 * Created by Joker on 2017/10/8.
 */

public class MailUtil {

    public static JavaMailSenderImpl createMailSender(String host, int port, String username, String password, int timeout) {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost(host);
        sender.setPort(port);
        sender.setUsername(username);
        sender.setPassword(password);
        sender.setDefaultEncoding("Utf-8");
        Properties p = new Properties();
        p.setProperty("mail.smtp.timeout", timeout + "");
        p.setProperty("mail.smtp.auth", "true");
        sender.setJavaMailProperties(p);
        return sender;
    }

    public static JavaMailSenderImpl getSupportMailSender() {
        return createMailSender("", 25, "", "", 60000);
    }

    public static boolean sendEmail(String to, String title, String content) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("support@fangliaoyun.com");
            message.setTo(to);
            message.setSubject(title);
            message.setText(content);
            JavaMailSender mailSender = getSupportMailSender();
            mailSender.send(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
