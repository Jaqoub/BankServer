package com.example.demo.Logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Service
public class MailService {
    @Autowired
    private JavaMailSender javaMailSender;


    public MailService(JavaMailSender javaMailSender){
        this.javaMailSender= javaMailSender;

    }


    public void sendemail(String email,String message) throws MailException {
        Thread t= new Thread(()-> {

            MimeMessage messagetoclient = javaMailSender.createMimeMessage();


            try {
                MimeMessageHelper helper = new MimeMessageHelper(messagetoclient,true);
                helper.setTo(email);
                helper.setFrom("Kea.online@outlook.com");
                helper.setSubject("From keaBank:");
                helper.setText(message);
                javaMailSender.send(messagetoclient);

            } catch (MessagingException e) {
                e.printStackTrace();
            }



        });
        t.start();
    }

}
