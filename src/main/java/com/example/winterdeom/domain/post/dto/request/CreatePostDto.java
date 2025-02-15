package com.example.winterdeom.domain.post.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@NoArgsConstructor
@Schema(description = "게시글 생성 요청 DTO")
public class CreatePostDto {
    @NotNull
    @Schema(description = "게시글 제목", example = "이것은 제목이다.", required = true)
    String title;

    @NotBlank
    @Schema(description = "게시글 내용", example = "이것은 내용이다.", required = true)
    String content;

    public CreatePostDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
