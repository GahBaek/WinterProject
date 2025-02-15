package com.example.winterdeom.domain.post.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Builder
@AllArgsConstructor
@Getter
@Schema(description = "게시글 목록 응답 DTO")
public class PostResponseDataList {
    @Schema(description = "게시글 응답 데이터 리스트",
            implementation = PostResponseData.class,
            example = "[{\"postId\": \"b70f5ed0-e4b2-4e28-a99e-914a2e11d58f\"," +
                    " \"title\": \"Post 1\", \"content\": \"Content of post 1\"}," +
                    " {\"postId\": \"c3f0b3f3-34b0-4a6e-993d-033fc0c504d7\", \"title\":" +
                    " \"Post 2\", \"content\": \"Content of post 2\"}]")
    private List<PostResponseData> postResponseDataList;

    public static PostResponseDataList from(List<PostResponseData> postResponseDataList) {

        return PostResponseDataList.builder()
                .postResponseDataList(postResponseDataList)
                .build();
    }
}
