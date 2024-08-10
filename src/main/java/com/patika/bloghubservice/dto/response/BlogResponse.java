package com.patika.bloghubservice.dto.response;

import com.patika.bloghubservice.model.BlogComment;
import com.patika.bloghubservice.model.enums.BlogStatus;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BlogResponse implements Serializable {

    private String title;
    private String text;
    private LocalDateTime createdDateTime;
    private BlogStatus blogStatus;
    private Long likeCount;
    private List<BlogCommentResponse> blogCommentList = new ArrayList<>();

}
