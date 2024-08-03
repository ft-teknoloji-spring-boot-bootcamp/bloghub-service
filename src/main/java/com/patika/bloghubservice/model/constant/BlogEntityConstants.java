package com.patika.bloghubservice.model.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BlogEntityConstants {
    public static final String TITLE = "title";
    public static final String CONTENT = "content";
    public static final String CREATED_DATE = "createdDate";
    public static final String USER_ID = "userId";
    public static final String LIKE_COUNT = "likeCount";
}
