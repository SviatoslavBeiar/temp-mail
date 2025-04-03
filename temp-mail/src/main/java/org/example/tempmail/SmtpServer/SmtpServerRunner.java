package org.example.tempmail.SmtpServer;
import org.example.tempmail.service.TempEmailService;
import org.example.tempmail.model.EmailMessage;
import org.subethamail.smtp.MessageContext;
import org.subethamail.smtp.MessageHandler;
import org.subethamail.smtp.MessageHandlerFactory;
import org.subethamail.smtp.RejectException;
import org.subethamail.smtp.server.SMTPServer;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.stream.Collectors;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

@Component
public class SmtpServerRunner implements InitializingBean, DisposableBean {

    private SMTPServer smtpServer;

    @Autowired
    private TempEmailService tempEmailService;

    @Override
    public void afterPropertiesSet() throws Exception {

        MessageHandlerFactory factory = new MessageHandlerFactory() {
            @Override
            public MessageHandler create(MessageContext ctx) {
                return new MessageHandler() {
                    private String from;
                    private String recipient;

                    @Override
                    public void from(String from) throws RejectException {
                        this.from = from;
                    }

                    @Override
                    public void recipient(String recipient) throws RejectException {
                        this.recipient = recipient;
                    }

                    @Override
                    public void data(InputStream data) throws IOException, RejectException {

                        String body = new BufferedReader(new InputStreamReader(data))
                                .lines().collect(Collectors.joining("\n"));

                        EmailMessage message = new EmailMessage(from, "Subject not specified", body);

                        boolean added = tempEmailService.addMessage(recipient, message);
                        if (!added) {
                            throw new RejectException("Email not found or expired");
                        }
                    }

                    @Override
                    public void done() {

                    }
                };
            }
        };


        smtpServer = new SMTPServer(factory);
        smtpServer.setPort(2525);
        smtpServer.start();
        System.out.println("SMTP server running on port 2525");
    }

    @Override
    public void destroy() throws Exception {
        if (smtpServer != null) {
            smtpServer.stop();
        }
    }
}
