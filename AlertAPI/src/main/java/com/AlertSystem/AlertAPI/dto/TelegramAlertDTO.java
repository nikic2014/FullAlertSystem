package com.AlertSystem.AlertAPI.dto;

public class TelegramAlertDTO {
    String chatId;
    String message;

    public TelegramAlertDTO(String chatId, String message) {
        this.chatId = chatId;
        this.message = message;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
