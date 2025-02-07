package com.example.winterdeom.domain.post.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@AllArgsConstructor
@Getter
public class PostResponseDataList {
    private List<PostResponseData> postResponseDataList;

    public static PostResponseDataList from(List<PostResponseData> postResponseDataList) {

        return PostResponseDataList.builder()
                .postResponseDataList(postResponseDataList)
                .build();
    }
}
