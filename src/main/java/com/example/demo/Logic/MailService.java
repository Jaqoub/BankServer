package com.example.demo.Logic;

import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import java.io.File;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public class MailService {
    @Autowired
    private JavaMailSender javaMailSender;

    public MailService(JavaMailSender javaMailSender){
        this.javaMailSender = javaMailSender;
    }

    public void sendEmail(String Email, String message) throws MailException{
        Thread t = new Thread(()->{
            MimeMessage messageToClient = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(messageToClient, true);
            helper.setTo(Email);
            helper.setFrom("KeaSyndikatet@outlook.com");
            helper.setSubject("From KeaBank:");
            helper.setText(message);
            javaMailSender.send(messageToClient);
        } catch (javax.mail.MessagingException me){
            me.printStackTrace();
        }

        });
        t.start();
    }

}
