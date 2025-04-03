package org.example.tempmail.controller;
import org.example.tempmail.service.TempEmailService;
import org.example.tempmail.model.EmailMessage;
import org.example.tempmail.model.TempEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/temp-email")
public class TempEmailController {

    @Autowired
    private TempEmailService tempEmailService;

    @GetMapping("/create")
    public ResponseEntity<TempEmail> createTempEmail() {
        TempEmail tempEmail = tempEmailService.createTempEmail();
        return ResponseEntity.ok(tempEmail);
    }

    @GetMapping("/{email}/inbox")
    public ResponseEntity<?> getInbox(@PathVariable("email") String emailAddress) {
        TempEmail tempEmail = tempEmailService.getTempEmail(emailAddress);
        if (tempEmail == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tempEmail.getMessages());
    }


    @PostMapping("/{email}/receive")
    public ResponseEntity<?> receiveEmail(@PathVariable("email") String emailAddress,
                                          @RequestBody EmailMessage message) {
        boolean added = tempEmailService.addMessage(emailAddress, message);
        if (added) {
            return ResponseEntity.ok("Message received");
        } else {
            return ResponseEntity.badRequest().body("Email not found or expired");
        }
    }
}
