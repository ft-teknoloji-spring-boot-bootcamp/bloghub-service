package com.patika.bloghubservice.converter;

import com.patika.bloghubservice.dto.response.BlogCommentResponse;
import com.patika.bloghubservice.model.BlogComment;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BlogCommentConverter {

    public static List<BlogCommentResponse> toResponse(List<BlogComment> blogCommentList) {
        return blogCommentList.stream()
                .map(BlogCommentConverter::toResponse)
                .toList();
    }

    public static BlogCommentResponse toResponse(BlogComment blogComment) {
        return new BlogCommentResponse(blogComment.getComment());
    }
}
