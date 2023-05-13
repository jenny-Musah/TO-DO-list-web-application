package com.example.todolist.services.mailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Async
    @Override public void send(String to, String email, String sub) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("Your to-do list");
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(sub);
        simpleMailMessage.setText("""
                Hi,
                This is to reminder you that you have not yet completed your task, Here are the task\n
                """ + email);
        javaMailSender.send(simpleMailMessage);

    }
}
