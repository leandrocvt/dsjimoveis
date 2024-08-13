package com.dsj.imoveis.service;

import com.dsj.imoveis.lib.dto.ContactRequest;

public interface ContactService {

    void sendContactMessage(ContactRequest contactRequest);
}
