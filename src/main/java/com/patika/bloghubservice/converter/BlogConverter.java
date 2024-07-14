package com.patika.bloghubservice.converter;

import com.patika.bloghubservice.dto.response.BlogResponse;
import com.patika.bloghubservice.model.Blog;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BlogConverter {

    public static BlogResponse toResponse(Blog blog) {
        return BlogResponse.builder()
                .title(blog.getTitle())
                .text(blog.getText())
                .blogStatus(blog.getBlogStatus())
                .likeCount(blog.getLikeCount())
                .createdDateTime(blog.getCreatedDate())
                .blogCommentList(BlogCommentConverter.toResponse(blog.getBlogCommentList()))
                .build();
    }
}
