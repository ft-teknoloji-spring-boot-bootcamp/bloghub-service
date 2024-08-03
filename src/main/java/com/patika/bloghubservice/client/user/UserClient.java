package com.patika.bloghubservice.client.user;

import com.patika.bloghubservice.client.user.dto.response.UserResponse;
import com.patika.bloghubservice.dto.response.GenericResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "payment-service", url = "localhost:8085/api/v1/users")
public interface UserClient {

    @GetMapping("/{email}")
    GenericResponse<UserResponse> getUserByEmail(@PathVariable String email);
}
