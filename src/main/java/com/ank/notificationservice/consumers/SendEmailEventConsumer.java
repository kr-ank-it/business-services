package com.ank.notificationservice.consumers;

import com.ank.notificationservice.dtos.SendEmailDto;
import com.ank.notificationservice.utils.EmailUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

@Service
public class SendEmailEventConsumer {

    @Autowired
    ObjectMapper objectMapper;
    @KafkaListener(topics = "sendWelcomeEmailEvent", groupId = "notification-service-group")
    public void consumeSendEmailEvent(String message) throws JsonProcessingException {
        SendEmailDto sendEmailDto = objectMapper.readValue(
                message,
                SendEmailDto.class);

        String email = sendEmailDto.getEmail();
        String subject = sendEmailDto.getSubject();
        String body = sendEmailDto.getMessage();

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.port", "587"); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

        //create Authenticator object to pass in Session.getInstance argument
        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                        "subokumar62@gmail.com",
                        "crfpuawoowcqsgsq");
            }
        };
        Session session = Session.getInstance(props, auth);

        EmailUtil.sendEmail(session, email,subject, body);

        // Here you would typically call an email service to send the email
    }

}
