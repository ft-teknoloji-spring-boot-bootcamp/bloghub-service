package com.patika.bloghubservice.dto.producer;

import com.patika.bloghubservice.client.email.dto.request.enums.EmailTemplate;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SendEmailMessage {

    private String to;
    private EmailTemplate emailTemplate;

}
