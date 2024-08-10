package com.patika.bloghubservice.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class BlogSearchRequest extends BaseSearchRequest{

    private String title;
    private long likeCount;
}
