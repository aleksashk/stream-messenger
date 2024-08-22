package com.flameksandr.streammessenger.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "message_status")
public class MessageStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "message_id", nullable = false)
    private Message message;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private MessageStatusEnum messageStatus;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public MessageStatus(Message message, MessageStatusEnum status) {
        this.message = message;
        this.messageStatus = status;
        this.updatedAt = LocalDateTime.now();
    }
}
