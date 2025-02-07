package com.example.winterdeom.domain.post.dto.response;

import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

@Builder
public class PostResponseDataList {
    private List<PostResponseData> postResponseDataList;

    public static PostResponseDataList from(List<PostResponseData> postResponseDataList) {
        return PostResponseDataList.builder()
                .postResponseDataList(postResponseDataList)
                .build();
    }
}
