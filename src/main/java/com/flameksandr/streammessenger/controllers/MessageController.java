package com.flameksandr.streammessenger.controllers;

import com.flameksandr.streammessenger.model.Message;
import com.flameksandr.streammessenger.model.MessageStatus;
import com.flameksandr.streammessenger.model.MessageStatusEnum;
import com.flameksandr.streammessenger.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        Message createdMessage = messageService.createMessage(message);
        return ResponseEntity.ok(createdMessage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> getMessageById(@PathVariable Long id) {
        Optional<Message> message = messageService.getMessageById(id);
        return message.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> messages = messageService.getAllMessages();
        return ResponseEntity.ok(messages);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<MessageStatus> updateMessageStatus(
            @PathVariable Long id,
            @RequestParam MessageStatusEnum status) {
        MessageStatus updatedStatus = messageService.updateMessageStatus(id, status);
        return ResponseEntity.ok(updatedStatus);
    }

    @GetMapping("/{id}/statuses")
    public ResponseEntity<List<MessageStatus>> getMessageStatuses(@PathVariable Long id) {
        List<MessageStatus> statuses = messageService.getMessageStatuses(id);
        return ResponseEntity.ok(statuses);
    }
}
