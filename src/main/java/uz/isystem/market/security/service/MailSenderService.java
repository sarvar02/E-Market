package uz.isystem.market.security.service;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class MailSenderService{

    private final MailSender mailSender;

    public MailSenderService(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendMail(String content, String email){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(email);
        simpleMailMessage.setText(content);
        simpleMailMessage.setSubject("Student Service Verification");
        mailSender.send(simpleMailMessage);
    }
}
