package com.pet.reminder_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender emailSender;

    @Value("${spring.mail.username}")
    private String sender;

    public void sendSimpleMessage(String toAddress, String text) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(sender);
        message.setSubject("Ur Remind");
        message.setText(text);
        message.setTo(toAddress);

        emailSender.send(message);

    }
}
