package org.example.tempmail.service;

import org.example.tempmail.model.EmailMessage;
import org.example.tempmail.model.TempEmail;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TempEmailService {


    private Map<String, TempEmail> tempEmails = new ConcurrentHashMap<>();


    public TempEmail createTempEmail() {
        String randomPart = UUID.randomUUID().toString().substring(0, 8);
        String emailAddress = randomPart + "@yourdomain.com";
        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(10);
        TempEmail tempEmail = new TempEmail(emailAddress, expiryTime);
        tempEmails.put(emailAddress, tempEmail);
        return tempEmail;
    }


    public TempEmail getTempEmail(String emailAddress) {
        return tempEmails.get(emailAddress);
    }

    public boolean addMessage(String emailAddress, EmailMessage message) {
        TempEmail tempEmail = tempEmails.get(emailAddress);
        if (tempEmail != null && tempEmail.getExpiryTime().isAfter(LocalDateTime.now())) {
            tempEmail.addMessage(message);
            return true;
        }
        return false;
    }

    @Scheduled(fixedRate = 60000)
    public void cleanupExpiredEmails() {
        LocalDateTime now = LocalDateTime.now();
        tempEmails.values().removeIf(email -> email.getExpiryTime().isBefore(now));
    }
}
