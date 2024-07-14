package com.patika.bloghubservice.controller;

import com.patika.bloghubservice.dto.request.UserSaveRequest;
import com.patika.bloghubservice.dto.response.GenericResponse;
import com.patika.bloghubservice.dto.response.UserResponse;
import com.patika.bloghubservice.model.enums.StatusType;
import com.patika.bloghubservice.service.UserService;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping()
    public GenericResponse<UserResponse> createUser(@RequestBody UserSaveRequest request) {
        return GenericResponse.success(userService.saveUser(request), HttpStatus.CREATED);
    }

    @GetMapping("/{email}")
    public GenericResponse<UserResponse> getUserByEmail(@PathVariable String email) {
        return GenericResponse.success(userService.getUserByEmail(email), HttpStatus.OK);
    }

    @GetMapping
    public GenericResponse<List<UserResponse>> getAllUsers() {
        return GenericResponse.success(userService.getAllUsers(), HttpStatus.OK);
    }

    @PutMapping("/{email}")
    public void changeStatus(@PathVariable String email, @PathParam("statusType") StatusType statusType) {
        userService.changeStatus(email, statusType);
    }

    @PutMapping()
    public void changeStatus() {
        // userService.changeStatusBulk(); --ödev
    }

    //ödev şifre değiştirenn endpoint
}
