package org.example.tempmail.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TempEmail {
    private String emailAddress;
    private LocalDateTime expiryTime;
    private List<EmailMessage> messages = new ArrayList<>();

    public TempEmail(String emailAddress, LocalDateTime expiryTime) {
        this.emailAddress = emailAddress;
        this.expiryTime = expiryTime;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public LocalDateTime getExpiryTime() {
        return expiryTime;
    }

    public List<EmailMessage> getMessages() {
        return messages;
    }

    public void addMessage(EmailMessage message) {
        messages.add(message);
    }
}
