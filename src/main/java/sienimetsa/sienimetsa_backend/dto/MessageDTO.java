package sienimetsa.sienimetsa_backend.dto;

import java.time.LocalDateTime;

public class MessageDTO {
    private String username;
    private String text;
    private LocalDateTime timestamp;
    private String chatColor; // Add the chatColor field

    // Getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getChatColor() {
        return chatColor;
    }

    public void setChatColor(String chatColor) {
        this.chatColor = chatColor;
    }

    @Override
    public String toString() {
        return "MessageDTO{" +
                "username='" + username + '\'' +
                ", text='" + text + '\'' +
                ", timestamp=" + timestamp +
                ", chatColor='" + chatColor + '\'' + // Add chatColor to the toString method
                '}';
    }
}
