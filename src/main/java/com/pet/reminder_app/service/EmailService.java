package com.pet.reminder_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender emailSender;

    public void sendSimpleMessage(String toAddress, String text){
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("Reminder_App");
        message.setSubject("Ur Remind");
        message.setText(text);
        message.setTo(toAddress);

        emailSender.send(message);
    }

}
