package com.patika.bloghubservice.service;

import com.patika.bloghubservice.client.user.dto.response.UserResponse;
import com.patika.bloghubservice.client.user.service.UserClientService;
import com.patika.bloghubservice.converter.BlogConverter;
import com.patika.bloghubservice.dto.request.BlogSaveRequest;
import com.patika.bloghubservice.dto.response.BlogResponse;
import com.patika.bloghubservice.exception.BlogHubException;
import com.patika.bloghubservice.exception.ExceptionMessages;
import com.patika.bloghubservice.model.Blog;
import com.patika.bloghubservice.model.BlogComment;
import com.patika.bloghubservice.model.enums.BlogCommentType;
import com.patika.bloghubservice.model.enums.BlogStatus;
import com.patika.bloghubservice.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;

    private final UserClientService userClientService;

    public BlogResponse createBlog(BlogSaveRequest request) {

        //ödev: kullanıcı blog yayınlamak için approved bir statuye sahip olmalı

        UserResponse foundUser = userClientService.getUserByEmail(request.getEmail());

        if (foundUser == null) {
            throw new BlogHubException(ExceptionMessages.USER_NOT_FOUND);
        }

        Blog blog = prepareBlog(request, foundUser);

        blogRepository.save(blog);

        return BlogConverter.toResponse(blog);
    }

    private Blog prepareBlog(BlogSaveRequest request, UserResponse userResponse) {
        Blog blog = new Blog();

        blog.setText(request.getText());
        blog.setTitle(request.getTitle());
        blog.setUserId(userResponse.getUserId());
        blog.setCreatedDate(LocalDateTime.now());
        blog.setBlogStatus(BlogStatus.DRAFT);
        blog.setLikeCount(0L);
        return blog;
    }

    public Blog getBlogByTitle(String title) {
        return blogRepository.findByTitle(title)
                .orElseThrow(() -> new RuntimeException("blog bulunamadı"));
    }

    public void addComment(String title, String email, String comment) {

        Blog foundBlog = getBlogByTitle(title);

        UserResponse foundUser = userClientService.getUserByEmail(email);

        if (foundUser == null) {
            throw new BlogHubException(ExceptionMessages.USER_NOT_FOUND);
        }

        BlogComment blogComment = prepareBlogComment(comment, foundUser.getUserId());

        foundBlog.getBlogCommentList().add(blogComment);

        //blogRepository.addComment(title, foundBlog);

    }

    private BlogComment prepareBlogComment(String comment, Long userId) {
        BlogComment blogComment = new BlogComment();
        blogComment.setComment(comment);
        blogComment.setUserId(userId);
        blogComment.setCreatedDate(LocalDateTime.now());
        blogComment.setBlogCommentType(BlogCommentType.INITIAL);
        return blogComment;
    }

    public List<Blog> getBlogsFilterByStatus(BlogStatus blogStatus, String email) {

        UserResponse foundUser = userClientService.getUserByEmail(email);

        if (foundUser == null) {
            throw new BlogHubException(ExceptionMessages.USER_NOT_FOUND);
        }

        return List.of();
    }

    public void changeBlogStatus(BlogStatus blogStatus, String title) {

        Blog foundBlog = getBlogByTitle(title);

        if (foundBlog.getBlogStatus().equals(BlogStatus.PUBLISHED)) {
            throw new BlogHubException("statüsü PUBLISHED olan bir blog silinemez.");
        }

        foundBlog.setBlogStatus(blogStatus);

    }

    public List<Blog> getAll() {
        return blogRepository.findAll();
    }

    public void likeBlog(Long id, String email) {
        Optional<Blog> optionalBlog = blogRepository.findById(id);

        if (optionalBlog.isEmpty()) {
            throw new BlogHubException("blog bulunamadı");
        }

        Blog blog = optionalBlog.get();

        blog.setLikeCount(blog.getLikeCount() + 1);

        blogRepository.save(blog);

    }

    public Long getLikeCountByTitle(String title) {

        Blog blog = getBlogByTitle(title);

        return blog.getLikeCount();
    }
}
