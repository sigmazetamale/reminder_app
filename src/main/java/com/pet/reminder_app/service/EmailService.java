package com.pet.reminder_app.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender emailSender;
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Value("${spring.mail.username}")
    private String sender;

    public void sendSimpleMessage(String toAddress, String text){

        logger.info("Sending email to: {}", toAddress);
        logger.info("Email text: {}", text);
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(sender);
        message.setSubject("Ur Remind");
        message.setText(text);
        message.setTo(toAddress);

        try {
            emailSender.send(message);
            logger.info("Email sent");
        } catch (Exception e) {
            logger.error("Failed to send email", e);
        }
    }

}
