package com.patika.bloghubservice.client.user.service;

import com.patika.bloghubservice.client.user.UserClient;
import com.patika.bloghubservice.client.user.dto.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserClientService {

    private final UserClient userClient;

    public UserResponse getUserByEmail(String email){
        return userClient.getUserByEmail(email).getData();
    }
}
