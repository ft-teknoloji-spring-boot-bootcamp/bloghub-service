package com.patika.bloghubservice.model;

import com.patika.bloghubservice.model.enums.StatusType;
import com.patika.bloghubservice.model.enums.UserType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Data
public class User {

    private String email;
    private String password;
    private UserType userType;
    private String bio;
    private StatusType statusType;
    private Set<SocialMedia> socialMediaList;
    private List<Blog> blogList;
    private List<BlogTag> followedTagList = new ArrayList<>();

    public User(String email, String password) {
        this.userType = UserType.STANDARD;
        this.statusType = StatusType.WAITING_APPROVAL;
        this.email = email;
        this.password = password;
    }

}
