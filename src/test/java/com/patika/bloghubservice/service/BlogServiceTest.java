package com.patika.bloghubservice.service;

import com.patika.bloghubservice.client.user.dto.response.UserResponse;
import com.patika.bloghubservice.client.user.service.UserClientService;
import com.patika.bloghubservice.dto.request.BlogSaveRequest;
import com.patika.bloghubservice.dto.response.BlogResponse;
import com.patika.bloghubservice.exception.BlogHubException;
import com.patika.bloghubservice.exception.ExceptionMessages;
import com.patika.bloghubservice.model.Blog;
import com.patika.bloghubservice.model.enums.BlogStatus;
import com.patika.bloghubservice.producer.KafkaProducer;
import com.patika.bloghubservice.repository.BlogRepository;
import org.instancio.Instancio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.instancio.Select.field;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BlogServiceTest {

    @InjectMocks
    private BlogService blogService;

    @Mock
    private UserClientService userClientService;

    @Mock
    private BlogRepository blogRepository;

    @Mock
    private KafkaProducer kafkaProducer;

    @Test
    @DisplayName("blog başarılı olarak kaydedilmeli")
    void should_create_blog_successfully() {
        //given

        Mockito.when(userClientService.getUserByEmail("cem@gmail.com"))
                .thenReturn(prepareUserResponse());

        //when
        BlogResponse response = blogService.createBlog(prepareBlogSaveRequest());

        //then
        verify(blogRepository, Mockito.times(1)).save(Mockito.any(Blog.class));
        verify(kafkaProducer, Mockito.times(1)).sendBlog(Mockito.any(Blog.class));

        Assertions.assertNotNull(response);
        Assertions.assertEquals("text", response.getText());
        Assertions.assertEquals("title", response.getTitle());
        Assertions.assertEquals(BlogStatus.DRAFT, response.getBlogStatus());
        Assertions.assertEquals(0, response.getLikeCount());
    }

    private UserResponse prepareUserResponse() {
        UserResponse response = new UserResponse();
        response.setEmail("adsasd");
        response.setUserId(1L);
        response.setBio("bio");
        return response;
    }

    private BlogSaveRequest prepareBlogSaveRequest() {
        BlogSaveRequest request = new BlogSaveRequest();
        request.setEmail("cem@gmail.com");
        request.setText("text");
        request.setTitle("title");
        return request;
    }

    @Test
    void should_throw_blogException_when_user_not_found() {

        BlogHubException blogHubException = Assertions.assertThrows(BlogHubException.class, () -> blogService.createBlog(prepareBlogSaveRequest()));

        Assertions.assertEquals(ExceptionMessages.USER_NOT_FOUND, blogHubException.getMessage());

        verifyNoInteractions(blogRepository);

        verifyNoInteractions(kafkaProducer);
    }

    @Test
    void should_increase_like_count() {

        //given
        ArgumentCaptor<Blog> captor = ArgumentCaptor.forClass(Blog.class);

        Mockito.when(blogRepository.findById(Mockito.eq(1L)))
                .thenReturn(Optional.of(prepareBlog(3L)));

        //when
        blogService.likeBlog(1L);

        //then

        verify(blogRepository, times(1)).save(captor.capture());

        Blog captoredBlog = captor.getValue();

        Assertions.assertEquals(prepareBlog(3L).getLikeCount() + 1, captoredBlog.getLikeCount());
        Assertions.assertEquals(prepareBlog(3L).getText(), captoredBlog.getText());

    }

    private Blog prepareBlog(long count) {
        return Instancio.of(Blog.class)
                .set(field(Blog::getLikeCount), count)
                .set(field(Blog::getText), "blog text")
                .create();
    }

    @Test
    void should_throw_blogHubException_when_blog_not_found() {

        Mockito.when(blogRepository.findById(Mockito.eq(1L)))
                .thenReturn(Optional.empty());

        BlogHubException blogHubException = Assertions.assertThrows(BlogHubException.class, () -> blogService.likeBlog(1L));

        Assertions.assertEquals("blog bulunamadı", blogHubException.getMessage());

        verifyNoMoreInteractions(blogRepository);

    }

}