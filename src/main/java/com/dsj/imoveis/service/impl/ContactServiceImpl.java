package com.dsj.imoveis.service.impl;

import com.dsj.imoveis.lib.dto.ContactRequest;
import com.dsj.imoveis.lib.entities.MessageLog;
import com.dsj.imoveis.repository.MessageLogRepository;
import com.dsj.imoveis.service.ContactService;
import com.dsj.imoveis.service.exceptions.MessageAlreadySentException;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final JavaMailSender mailSender;
    private final MessageLogRepository messageLogRepository;

    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @Value("${twilio.phone.number}")
    private String fromPhoneNumber;

    @PostConstruct
    public void init() {
        Twilio.init(accountSid, authToken);
    }

    @Override
    public void sendContactMessage(ContactRequest contactRequest) {
        if (Objects.nonNull(contactRequest.email()) && !contactRequest.email().isEmpty()) {
            if (!isValidEmail(contactRequest.email())) {
                throw new IllegalArgumentException("Invalid email format");
            }
            String emailBody = buildEmailMessage(contactRequest);
            sendEmail(contactRequest.email(), "Informações sobre o Imóvel", emailBody);
        }

        if (Objects.nonNull(contactRequest.phoneNumber()) && !contactRequest.phoneNumber().isEmpty()) {
            if (!isValidPhoneNumber(contactRequest.phoneNumber())) {
                throw new IllegalArgumentException("Invalid phone number format");
            }

            LocalDateTime startOfDay = LocalDateTime.now().with(LocalTime.MIN);
            boolean alreadySent = messageLogRepository.hasMessageBeenSentToday(contactRequest.phoneNumber(), startOfDay);

            if (alreadySent) {
                throw new MessageAlreadySentException("Já foi enviada uma mensagem para este número hoje.");
            }

            String whatsAppMessage = buildWhatsAppMessage(contactRequest);
            sendWhatsAppMessage(contactRequest.phoneNumber(), whatsAppMessage);
            saveMessageLog(contactRequest.phoneNumber());
        }
    }

    private void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("no-reply@example.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    private void sendWhatsAppMessage(String toPhoneNumber, String messageBody) {
        Message.creator(
                new PhoneNumber("whatsapp:" + toPhoneNumber),
                new PhoneNumber(fromPhoneNumber),
                messageBody
        ).create();
    }

    private boolean isValidEmail(String email) {
        return StringUtils.hasText(email) && email.contains("@");
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("\\+55\\d{2}\\d{9}");
    }

    private String buildEmailMessage(ContactRequest contactRequest) {
        return "Olá " + contactRequest.name() + ",\n\n" +
                "Você mostrou interesse no imóvel #" + contactRequest.immobileId() + ".\n\n" +
                "Assim que possível será atendido para tirar quaisquer duvidas ou para agendar uma visita.\n\n" +
                "Atenciosamente,\n" +
                "DSJ Imóveis";
    }

    private String buildWhatsAppMessage(ContactRequest contactRequest) {
        return "Olá " + contactRequest.name() + ",\n\n" +
                "Você mostrou interesse no imóvel #" + contactRequest.immobileId() + ".\n\n" +
                "Assim que possível será atendido para tirar quaisquer duvidas ou para agendar uma visita.\n\n" +
                "Atenciosamente,\n" +
                "DSJ Imóveis";
    }

    private void saveMessageLog(String phoneNumber) {
        MessageLog messageLog = new MessageLog();
        messageLog.setPhoneNumber(phoneNumber);
        messageLog.setSentAt(LocalDateTime.now());
        messageLogRepository.save(messageLog);
    }
}
