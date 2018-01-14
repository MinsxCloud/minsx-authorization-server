package com.minsx.authorization.config;

import com.minsx.util.mail.MailSender;
import com.minsx.util.mail.MailSenderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailConfig {

    @Bean
    public MailSender getMailSender() {
        return MailSenderFactory.getTencentEnterpriseSSLSender("support@minsx.com", "Minsx2018");
    }

}
