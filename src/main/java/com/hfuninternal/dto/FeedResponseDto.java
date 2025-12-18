package com.hfuninternal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class FeedResponseDto {
    private boolean success;
    private List<PostResponseDto> posts;
    private boolean hasMore;
}
