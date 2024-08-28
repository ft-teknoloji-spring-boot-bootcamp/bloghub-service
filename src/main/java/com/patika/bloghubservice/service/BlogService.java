package com.patika.bloghubservice.service;

import com.patika.bloghubservice.client.user.dto.response.UserResponse;
import com.patika.bloghubservice.client.user.service.UserClientService;
import com.patika.bloghubservice.converter.BlogConverter;
import com.patika.bloghubservice.dto.request.BlogSaveRequest;
import com.patika.bloghubservice.dto.request.BlogSearchRequest;
import com.patika.bloghubservice.dto.response.BlogResponse;
import com.patika.bloghubservice.dto.response.BlogSearchResponse;
import com.patika.bloghubservice.exception.BlogHubException;
import com.patika.bloghubservice.exception.ExceptionMessages;
import com.patika.bloghubservice.model.Blog;
import com.patika.bloghubservice.model.BlogComment;
import com.patika.bloghubservice.model.enums.BlogCommentType;
import com.patika.bloghubservice.model.enums.BlogStatus;
import com.patika.bloghubservice.producer.KafkaProducer;
import com.patika.bloghubservice.repository.BlogRepository;
import com.patika.bloghubservice.repository.specification.BlogSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BlogService {

    private final BlogRepository blogRepository;

    private final UserClientService userClientService;

    private final KafkaProducer kafkaProducer;

    @CacheEvict(cacheNames = "blogs", allEntries = true)
    @Transactional(propagation = Propagation.REQUIRES_NEW, noRollbackForClassName = {"BlogHubException.class"}
            , rollbackFor = SQLException.class)
    public BlogResponse createBlog(BlogSaveRequest request) {

        UserResponse foundUser = userClientService.getUserByEmail(request.getEmail());

        if (foundUser == null) {
            throw new BlogHubException(ExceptionMessages.USER_NOT_FOUND);
        }

        Blog blog = prepareBlog(request, foundUser);

        blogRepository.save(blog);

        kafkaProducer.sendBlog(blog);

        return BlogConverter.toResponse(blog);
    }

    private Blog prepareBlog(BlogSaveRequest request, UserResponse userResponse) {
        Blog blog = new Blog();

        blog.setText(request.getText());
        blog.setTitle(request.getTitle());
        blog.setUserId(1L);
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

    @Cacheable(value = "blogs", cacheNames = "blogs")
    @Transactional(readOnly = true)
    public BlogSearchResponse getAll(BlogSearchRequest request) {

        PageRequest pageRequest = PageRequest.of(request.getPage(), request.getSize(),
                Sort.by(Sort.Direction.DESC, "likeCount")); //ödev bu parametreler kullanıcıdan alınacak şekle çevirin

        Page<Blog> blogs = blogRepository.findAll(BlogSpecification.initSpecification(request), pageRequest);
        log.info("blog'lar db'den getirildi.");
        return BlogConverter.toResponse(blogs);
    }

    public void likeBlog(Long id) {
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
