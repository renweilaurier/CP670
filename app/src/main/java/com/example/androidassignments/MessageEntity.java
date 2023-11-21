package com.example.androidassignments;

import java.io.Serializable;

public class MessageEntity implements Serializable {
    private long messageId;
    private String message;

    public MessageEntity(long messageId, String message) {
        this.messageId = messageId;
        this.message = message;
    }

    public long getMessageId() {
        return messageId;
    }

    public String getMessage() {
        return message;
    }
}
