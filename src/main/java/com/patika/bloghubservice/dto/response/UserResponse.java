package com.patika.bloghubservice.dto.response;

import com.patika.bloghubservice.model.SocialMedia;
import com.patika.bloghubservice.model.enums.UserType;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {

    private String email;
    private UserType userType;
    private String bio;
    private Set<SocialMedia> socialMediaList;
}
