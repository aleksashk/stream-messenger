package com.flameksandr.streammessenger.services;

import com.flameksandr.streammessenger.model.Message;
import com.flameksandr.streammessenger.model.MessageStatus;
import com.flameksandr.streammessenger.model.MessageStatusEnum;

import java.util.List;
import java.util.Optional;

public interface MessageService {
    Message createMessage(Message message);
    Optional<Message> getMessageById(Long id);
    List<Message> getAllMessages();
    MessageStatus updateMessageStatus(Long messageId, MessageStatusEnum status);
    List<MessageStatus> getMessageStatuses(Long messageId);
}
