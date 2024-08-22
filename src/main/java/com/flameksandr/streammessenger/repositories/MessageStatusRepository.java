package com.flameksandr.streammessenger.repositories;

import com.flameksandr.streammessenger.model.Message;
import com.flameksandr.streammessenger.model.MessageStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageStatusRepository extends JpaRepository<MessageStatus, Long> {
    List<MessageStatus> findAllByMessage(Message message);
}
