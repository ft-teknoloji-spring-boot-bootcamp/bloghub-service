package com.patika.bloghubservice.client.email.dto.request;

import com.patika.bloghubservice.client.email.dto.request.enums.EmailTemplate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmailCreateRequest {

    private String to;
    private EmailTemplate emailTemplate;
}
