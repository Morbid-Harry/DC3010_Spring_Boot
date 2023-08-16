package com.dc3010.DC3010_Spring_Boot.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Handles the sending of an email marked as a service so spring can automatically detect and instantiate it 
 * @author Harry
 *
 */
@Service
public class EmailService {
	
	
    private final JavaMailSender mailSender;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    
    /**
     * Sends an email via outlooks email servers using the email project_finder_notifier@outlook.com
     * @param to the email of the user to send the email to
     * @param subject title of the email
     * @param body of the email
     */
    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("project_finder_notifier@outlook.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }
}
