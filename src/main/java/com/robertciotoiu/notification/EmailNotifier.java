package com.robertciotoiu.notification;

import com.robertciotoiu.Listing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Qualifier("email")
public class EmailNotifier implements Notifier {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${mail.to}")
    private String to;

    @Override
    public void notify(List<Listing> newListing) {
        var subject = newListing.size() + " new listings found!";
        var body = "Hey there, \n\n" +
                "I found " + newListing.size() + " new listings for you! \n\n" +
                "Here they are: \n\n" + newListing + "\n\n";
        String[] recipients = this.to.split(",");
        sendEmail(recipients, subject, body);
    }

    private void sendEmail(String[] to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        javaMailSender.send(message);
    }
}
