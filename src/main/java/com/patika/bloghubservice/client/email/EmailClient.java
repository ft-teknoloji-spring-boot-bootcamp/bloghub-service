package com.patika.bloghubservice.client.email;

import com.patika.bloghubservice.client.email.dto.request.EmailCreateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "email-service", url = "localhost:8081/api/v1/emails")
public interface EmailClient {

    @PostMapping
    void sendEmail(@RequestBody EmailCreateRequest request);
}
