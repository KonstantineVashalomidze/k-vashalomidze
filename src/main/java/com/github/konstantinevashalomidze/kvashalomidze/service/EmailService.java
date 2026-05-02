package com.github.konstantinevashalomidze.kvashalomidze.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    /* CONSTANTS START */
    @Value("${spring.application.customConfigs.constants.applicationEmail}")
    private String APPLICATION_EMAIL;
    /* CONSTANTS END */

    /* CUSTOM START */
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    /* CUSTOM END */

    public void sendMessage(String toEmail, String text) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(APPLICATION_EMAIL);
        message.setTo(toEmail);
        message.setSubject("Konstantine Vashalomidze");
        message.setText(
                text +
                """

                Contact:""" + APPLICATION_EMAIL
        );

        mailSender.send(message);
    }

}
