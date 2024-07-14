package com.patika.bloghubservice.model;

import com.patika.bloghubservice.model.enums.BlogStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Blog {

    private String title;
    private String text;
    private LocalDateTime createdDate;
    private User user;
    private BlogStatus blogStatus;
    private Long likeCount;
    private List<BlogComment> blogCommentList = new ArrayList<>();

    public Blog(String title, String text, User user) {
        this.title = title;
        this.text = text;
        this.user = user;
        this.createdDate = LocalDateTime.now();
        this.blogStatus = BlogStatus.DRAFT;
        this.likeCount = 0L;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BlogStatus getBlogStatus() {
        return blogStatus;
    }

    public void setBlogStatus(BlogStatus blogStatus) {
        this.blogStatus = blogStatus;
    }

    public Long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Long likeCount) {
        this.likeCount = likeCount;
    }

    public List<BlogComment> getBlogCommentList() {
        return blogCommentList;
    }

    public void setBlogCommentList(List<BlogComment> blogCommentList) {
        this.blogCommentList = blogCommentList;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", createdDate=" + createdDate +
                ", user=" + user +
                ", blogStatus=" + blogStatus +
                '}';
    }
}
