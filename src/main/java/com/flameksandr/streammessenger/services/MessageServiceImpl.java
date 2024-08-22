package com.flameksandr.streammessenger.services;

import com.flameksandr.streammessenger.exceptions.EntityNotFoundException;
import com.flameksandr.streammessenger.model.Message;
import com.flameksandr.streammessenger.model.MessageStatus;
import com.flameksandr.streammessenger.model.MessageStatusEnum;
import com.flameksandr.streammessenger.repositories.MessageRepository;
import com.flameksandr.streammessenger.repositories.MessageStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final MessageStatusRepository messageStatusRepository;
    private final EmailSenderService emailSenderService;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository,
                              MessageStatusRepository messageStatusRepository,
                              EmailSenderService emailSenderService) {
        this.messageRepository = messageRepository;
        this.messageStatusRepository = messageStatusRepository;
        this.emailSenderService = emailSenderService;
    }

    @Override
    @Transactional
    public Message createMessage(Message message) {
        message.setCreatedAt(java.time.LocalDateTime.now());
        Message savedMessage = messageRepository.save(message);

        MessageStatus status = new MessageStatus(savedMessage, MessageStatusEnum.CREATED);
        messageStatusRepository.save(status);

        // Параллельная отправка сообщений
        CompletableFuture.runAsync(() -> emailSenderService.sendEmail(
                message.getRecipient(),
                "Subject",
                message.getContent()
        ));

        return savedMessage;
    }

    @Override
    public Optional<Message> getMessageById(Long id) {
        return messageRepository.findById(id);
    }

    @Override
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    @Override
    @Transactional
    public MessageStatus updateMessageStatus(Long messageId, MessageStatusEnum status) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new EntityNotFoundException("Message not found"));

        MessageStatus messageStatus = new MessageStatus(message, status);
        return messageStatusRepository.save(messageStatus);
    }

    @Override
    public List<MessageStatus> getMessageStatuses(Long messageId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message not found"));

        return messageStatusRepository.findAllByMessage(message);
    }
}
