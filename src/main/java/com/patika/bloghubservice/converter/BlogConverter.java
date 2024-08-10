package com.patika.bloghubservice.converter;

import com.patika.bloghubservice.dto.response.BlogResponse;
import com.patika.bloghubservice.dto.response.BlogSearchResponse;
import com.patika.bloghubservice.model.Blog;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

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

    public static List<BlogResponse> toResponse(List<Blog> blogs) {
        return blogs
                .stream()
                .map(BlogConverter::toResponse)
                .toList();
    }

    public static BlogSearchResponse toResponse(Page<Blog> blogs) {
        BlogSearchResponse response = new BlogSearchResponse();

        response.setBlogResponses(toResponse(blogs.getContent()));
        response.setTotalPage(blogs.getTotalPages());
        response.setTotalElement(blogs.getTotalElements());
        return response;
    }
}
