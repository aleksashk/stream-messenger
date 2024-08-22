package com.flameksandr.streammessenger.controllers;

import com.flameksandr.streammessenger.services.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    private final EmailSenderService emailSenderService;

    @Autowired
    public EmailController(EmailSenderService emailSenderService) {
        this.emailSenderService = emailSenderService;
    }

    @PostMapping("/send")
    public String sendEmail(@RequestParam String to, @RequestParam String subject, @RequestParam String content) {
        emailSenderService.sendEmail(to, subject, content);
        return "Email sent successfully!";
    }
}
