package com.patika.bloghubservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class BlogSearchRequest extends BaseSearchRequest{

    private String title;
    private long likeCount;
}
