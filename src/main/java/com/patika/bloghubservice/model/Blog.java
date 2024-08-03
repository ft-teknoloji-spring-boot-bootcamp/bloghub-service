package com.patika.bloghubservice.model;

import com.patika.bloghubservice.model.constant.BlogEntityConstants;
import com.patika.bloghubservice.model.enums.BlogStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "blog")
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = BlogEntityConstants.TITLE, nullable = false)
    private String title;

    @Column(name = BlogEntityConstants.CONTENT)
    private String text;

    @Column(name = BlogEntityConstants.CREATED_DATE)
    private LocalDateTime createdDate;

    @Column(name = BlogEntityConstants.USER_ID)
    private Long userId;

    @Enumerated(EnumType.STRING)
    private BlogStatus blogStatus;

    @Column(name = BlogEntityConstants.LIKE_COUNT)
    private Long likeCount;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BlogComment> blogCommentList = new ArrayList<>();


}
