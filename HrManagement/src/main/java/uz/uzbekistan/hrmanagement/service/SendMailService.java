package uz.uzbekistan.hrmanagement.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SendMailService {
    private final JavaMailSender javaMailSender;

    @Autowired
    public SendMailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public boolean sendEmail(String email, String subject, String requestUrl) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("noreply@mail.com");
            mailMessage.setTo(email);
            mailMessage.setSubject(subject);
            mailMessage.setText(requestUrl);
            javaMailSender.send(mailMessage);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
