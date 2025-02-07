package com.example.winterdeom.domain.post.dto.response;

import com.example.winterdeom.domain.post.domain.Post;
import com.example.winterdeom.domain.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PostResponseData {
    private String title;
    private String content;
    private User user;

    public static PostResponseData from(Post post) {

        return PostResponseData.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .user(post.getUser())
                .build();
    }
}
