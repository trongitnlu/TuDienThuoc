package com.tudien.tudienthuoc.chat;


public class ChatMessage {
    private long id;
    private boolean isme;
    private String message;
    private Long userId;
    private String dateTime;
    public int daXem;
    public ChatMessage(){}
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean getIsme() {
        return isme;
    }

    public void setMe(boolean isMe) {
        this.isme = isMe;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getDate() {
        return dateTime;
    }

    public void setDate(String dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "id=" + id +
                ", isMe=" + isme +
                ", message='" + message + '\'' +
                ", userId=" + userId +
                ", dateTime='" + dateTime + '\'' +
                '}';
    }
}