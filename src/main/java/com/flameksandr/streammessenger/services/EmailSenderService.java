package com.flameksandr.streammessenger.services;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class EmailSenderService {

    public void sendEmail(String to, String subject, String content) {
        // Настройка почтового сервера с использованием Mailtrap:
        Properties props = new Properties();
        props.put("mail.smtp.host", "sandbox.smtp.mailtrap.io"); // Используйте хост из Mailtrap
        props.put("mail.smtp.port", "2525"); // Используйте порт из Mailtrap
        props.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("12cb77a1840121", "7eb3bf26dca6ef"); // Используйте логин и пароль из Mailtrap
            }
        });

        try {
            // Создание сообщения
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("aleksandrphilimonov@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(content);

            // Отправка сообщения
            Transport.send(message);

            System.out.println("Сообщение успешно отправлено");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
