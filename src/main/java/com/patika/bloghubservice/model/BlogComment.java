package com.patika.bloghubservice.model;

import com.patika.bloghubservice.model.enums.BlogCommentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "blog_comment")
public class BlogComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "userId")
    private Long userId;

    @Column(name = "comment")
    private String comment;

    @Column(name = "createdDate")
    private LocalDateTime createdDate;

    @Enumerated(EnumType.STRING)
    private BlogCommentType blogCommentType;

}
