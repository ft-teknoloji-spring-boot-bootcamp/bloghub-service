package com.patika.bloghubservice.client.email.service;

import com.patika.bloghubservice.client.email.EmailClient;
import com.patika.bloghubservice.client.email.dto.request.EmailCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailClientService {

    private final EmailClient emailClient;

    public void sendEmail(EmailCreateRequest emailCreateRequest) {
        emailClient.sendEmail(emailCreateRequest);
    }
}
