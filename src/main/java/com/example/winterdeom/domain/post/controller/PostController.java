package com.example.winterdeom.domain.post.controller;


import com.example.winterdeom.domain.common.ResponseDto;
import com.example.winterdeom.domain.post.dto.request.CreatePostDto;
import com.example.winterdeom.domain.post.dto.response.PostResponseData;
import com.example.winterdeom.domain.post.dto.response.PostResponseDataList;
import com.example.winterdeom.domain.post.service.PostService;
import com.example.winterdeom.domain.user.domain.User;
import com.example.winterdeom.global.auth.AuthenticatedUser;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@AllArgsConstructor
@Tag(name = "Post API", description = "게시글 관련 API")
public class PostController {
    private final PostService postService;

    // 게시글 생성
    public ResponseEntity<ResponseDto<Void>> createPost (@AuthenticatedUser User user, CreatePostDto createPostDto){
        this.postService.createPost(user, createPostDto);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "create POST successfully"), HttpStatus.CREATED);
    }

    // 게시글 조회
    public ResponseEntity<ResponseDto<PostResponseDataList>> getAllPosts (@AuthenticatedUser User user){
        PostResponseDataList postResponseDataList = this.postService.getAllPosts(user);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "get all POSTs successfully", postResponseDataList), HttpStatus.OK);
    }

    // 특정 게시글 조회
    public ResponseEntity<ResponseDto<PostResponseData>> getPostById (@AuthenticatedUser User user, UUID postId){
        PostResponseData postResponseData = this.postService.getPostById(user, postId);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "get POST successfully", postResponseData), HttpStatus.OK);
    }

    // 게시글 수정
    public ResponseEntity<ResponseDto<PostResponseData>> modifyPostById (@AuthenticatedUser User user, UUID postId, CreatePostDto oldPost){
        PostResponseData newPostData = this.postService.modifyPostById(user, postId, oldPost);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "modify POST successfully", newPostData), HttpStatus.OK);
    }

    // 게시글 삭제
    public ResponseEntity<ResponseDto<PostResponseData>> deletePostById (@AuthenticatedUser User user, UUID postId){
        this.postService.deletePostById(user, postId);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "delete POST successfully"), HttpStatus.OK);
    }
}
