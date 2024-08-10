package com.patika.bloghubservice.dto.response;

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
public class BlogSearchResponse implements Serializable {

    private List<BlogResponse> blogResponses;
    private int totalPage;
    private long totalElement;

}
