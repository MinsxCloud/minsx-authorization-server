package com.minsx.authorization.common.util;

import com.minsx.util.mail.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class MailUtil {

    @Autowired
    MailSender mailSender;

    public Boolean sendMail(String to, String username, String title, String content) {
        Boolean isSuccess = true;
        Map<String, Object> params = new HashMap<>();
        params.put("headerLogo", "https://image.minsx.com/pic/minsx/logo/LogoOfWhite.png");
        params.put("footerLogo", "https://image.minsx.com/pic/minsx/logo/logoSuperGray.png");
        params.put("webSite", "https://www.minsx.com/");
        params.put("webName", "米斯云平台");
        params.put("welcome", String.format("尊敬的%s:", username));

        params.put("content", content);
        params.put("company", "米斯云平台");
        params.put("remind", "该邮件由米斯云平台系统发出,请勿直接回复!");
        params.put("copyRight", "如果您有任何问题，请及时联系我们：\nEmail: support@minsx.com\nCopyright © 2016-2017 minsx.com All rights reserved.");
        params.put("date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        try {
            mailSender.sendSysTemplateMail(to, title, params);
        } catch (Exception e) {
            e.printStackTrace();
            isSuccess = false;
        }
        return isSuccess;
    }

}
