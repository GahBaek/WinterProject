package com.example.winterdeom.domain.post.dto.response;

import com.example.winterdeom.domain.post.domain.Post;
import com.example.winterdeom.domain.user.domain.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor
@Schema(description = "게시글 응답 DTO")
public class PostResponseData {
    @Schema(description = "게시글 제목", example = "이것은 제목이다.")
    private String title;

    @Schema(description = "게시글 내용", example = "이것은 내용이다.")
    private String content;

    @Schema(description = "게시글 ID", example = "b70f5ed0-e4b2-4e28-a99e-914a2e11d58f")
    private UUID postId;

    @JsonIgnore
    @Schema(hidden = true)
    private User user;

    public static PostResponseData from(Post post) {
        return PostResponseData.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .postId(post.getId())
                .user(post.getUser())
                .build();
    }
}
