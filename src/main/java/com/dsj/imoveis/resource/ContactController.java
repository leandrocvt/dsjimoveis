package com.dsj.imoveis.resource;

import com.dsj.imoveis.lib.dto.ContactRequest;
import com.dsj.imoveis.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/contact")
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;

    @PostMapping
    public ResponseEntity<String> sendContactMessage(@RequestBody ContactRequest contactRequest) {
        contactService.sendContactMessage(contactRequest);
        return ResponseEntity.ok("Mensagem enviada com sucesso!");
    }
}
