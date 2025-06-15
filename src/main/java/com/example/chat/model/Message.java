package com.example.chat.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * チャットメッセージを表すモデルクラス
 */
public class Message {
    private String content;
    private String sender; // "user" または "assistant"
    private LocalDateTime timestamp;
    
    public Message(String content, String sender) {
        this.content = content;
        this.sender = sender;
        this.timestamp = LocalDateTime.now();
    }
    
    // Getter
    public String getContent() {
        return content;
    }
    
    public String getSender() {
        return sender;
    }
    
    public String getFormattedTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return timestamp.format(formatter);
    }
    
    // Setter
    public void setContent(String content) {
        this.content = content;
    }
    
    public void setSender(String sender) {
        this.sender = sender;
    }
}