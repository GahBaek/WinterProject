package com.example.winterdeom.domain.post.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreatePostDto {
    @NotNull
    String title;
    @NotBlank
    String content;

    public CreatePostDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
