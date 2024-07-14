package com.patika.bloghubservice.service;

import com.patika.bloghubservice.converter.BlogConverter;
import com.patika.bloghubservice.dto.request.BlogSaveRequest;
import com.patika.bloghubservice.dto.response.BlogResponse;
import com.patika.bloghubservice.model.Blog;
import com.patika.bloghubservice.model.BlogComment;
import com.patika.bloghubservice.model.User;
import com.patika.bloghubservice.model.enums.BlogStatus;
import com.patika.bloghubservice.repository.BlogRepository;
import com.patika.bloghubservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;
    private final UserRepository userRepository;

    public BlogResponse createBlog(String email, BlogSaveRequest request) {

        //ödev: kullanıcı blog yayınlamak için approved bir statuye sahip olmalı

        Optional<User> foundUser = userRepository.findByEmail(email);

        Blog blog = new Blog(request.getTitle(), request.getText(), foundUser.get());

        blogRepository.save(blog);

        return BlogConverter.toResponse(blog);
    }

    public Blog getBlogByTitle(String title) {
        return blogRepository.findByTitle(title).orElseThrow(() -> new RuntimeException("blog bulunamadı"));
    }

    public void addComment(String title, String email, String comment) {

        Blog foundBlog = getBlogByTitle(title);

        Optional<User> user = userRepository.findByEmail(email);

        BlogComment blogComment = new BlogComment(user.get(), comment);

        foundBlog.getBlogCommentList().add(blogComment);

        blogRepository.addComment(title, foundBlog);

    }

    public List<Blog> getBlogsFilterByStatus(BlogStatus blogStatus, String email) {

        Optional<User> foundUser = userRepository.findByEmail(email);

        return foundUser.get().getBlogList().stream()
                .filter(blog -> blogStatus.equals(blog.getBlogStatus()))
                .toList();
    }

    public void changeBlogStatus(BlogStatus blogStatus, String title) {

        Blog foundBlog = getBlogByTitle(title);

        if (foundBlog.getBlogStatus().equals(BlogStatus.PUBLISHED)) {
            throw new RuntimeException("statüsü PUBLISHED olan bir blog silinemez.");
        }

        foundBlog.setBlogStatus(blogStatus);

    }

    public List<Blog> getAll() {
        return blogRepository.findAll();
    }

    public void likeBlog(String title, String email) {
        Blog blog = getBlogByTitle(title);

        blog.setLikeCount(blog.getLikeCount() + 1);

        blogRepository.likeBlog(title, blog);

    }

    public Long getLikeCountByTitle(String title) {

        Blog blog = getBlogByTitle(title);

        return blog.getLikeCount();
    }
}
