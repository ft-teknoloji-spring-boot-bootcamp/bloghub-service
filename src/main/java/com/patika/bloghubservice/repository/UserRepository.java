package com.patika.bloghubservice.repository;

import com.patika.bloghubservice.model.User;
import com.patika.bloghubservice.model.enums.StatusType;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserRepository {

    private Map<String, User> userList = new HashMap<>();

    public void save(User user) {
        userList.put(user.getEmail(), user);
    }

    public Optional<User> findByEmail(String email) {
        return userList.values()
                .stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }

    public List<User> findAll() {
        return userList.
                values()
                .stream()
                .filter(user -> user.getStatusType().equals(StatusType.APPROVED))
                .toList();
    }

    public void changeStatus(String email, User user) {
        userList.remove(email);
        userList.put(email, user);
    }
}
