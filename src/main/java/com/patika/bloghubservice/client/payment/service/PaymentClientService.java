package com.patika.bloghubservice.client.payment.service;

import com.patika.bloghubservice.client.payment.PaymentClient;
import com.patika.bloghubservice.client.payment.service.dto.request.PaymentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentClientService {

    private final PaymentClient paymentClient;

    public void createPayment(PaymentRequest request) {
        paymentClient.createPayment(request);
    }
}
