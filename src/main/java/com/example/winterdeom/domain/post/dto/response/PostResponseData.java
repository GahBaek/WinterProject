package com.example.winterdeom.domain.post.dto.response;

import com.example.winterdeom.domain.post.domain.Post;
import com.example.winterdeom.domain.user.domain.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor
public class PostResponseData {
    private String title;
    private String content;
    private UUID postId;

    @JsonIgnore
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
