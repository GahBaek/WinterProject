package com.example.winterdeom.domain.post.controller;


import com.example.winterdeom.domain.common.ResponseDto;
import com.example.winterdeom.domain.post.dto.request.CreatePostDto;
import com.example.winterdeom.domain.post.dto.response.PostResponseData;
import com.example.winterdeom.domain.post.dto.response.PostResponseDataList;
import com.example.winterdeom.domain.post.service.PostService;
import com.example.winterdeom.domain.user.domain.User;
import com.example.winterdeom.global.auth.AuthenticatedUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/posts")
@AllArgsConstructor
@Tag(name = "게시글 API", description = "게시글 관련 API")
public class PostController {
    private final PostService postService;

    // 게시글 생성
    @Operation(summary = "게시글 생성", description = "새로운 게시글 작성")
    @PostMapping
    public ResponseEntity<ResponseDto<Void>> createPost (
            @Parameter(description = "게시글 작성자 정보") @AuthenticatedUser User user,
            @Valid @RequestBody CreatePostDto createPostDto){
        log.info("게시글 작성");
        this.postService.createPost(user, createPostDto);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "create POST successfully"), HttpStatus.CREATED);
    }

    // 게시글 조회
    @Operation(summary = "전체 게시글 조회", description = "모든 게시글 조회")
    @GetMapping
    public ResponseEntity<ResponseDto<PostResponseDataList>> getAllPosts (@Parameter(description = "조회하는 사용자 정보") @AuthenticatedUser User user){
        PostResponseDataList postResponseDataList = PostResponseDataList.from(this.postService.getAllPosts(user));
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "get all POSTs successfully", postResponseDataList), HttpStatus.OK);
    }

    // 특정 게시글 조회
    @Operation(summary = "특정 게시글 조회", description = "게시글 ID를 이용하여 특정 게시글 조회")
    @GetMapping("/{postId}")
    public ResponseEntity<ResponseDto<PostResponseData>> getPostById (@Parameter(description = "조회하는 사용자 정보") @AuthenticatedUser User user,
                                                                      @Parameter(description = "게시글 ID") @PathVariable UUID postId){
        PostResponseData postResponseData = this.postService.getPostById(user, postId);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "get POST successfully", postResponseData), HttpStatus.OK);
    }

    // 본인이 작성한 게시글만 조회하기
    @Operation(summary = "본인이 작성한 게시글 조회", description = "사용자 Id를 이용하여 게시글 조회")
    @GetMapping("/{postId}")
    public ResponseEntity<ResponseDto<PostResponseDataList>> getPostByUser (@Parameter(description = "조회하는 사용자 정보") @AuthenticatedUser User user){
        PostResponseDataList postResponseData = PostResponseDataList.from(this.postService.getPostByUser(user));
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "get my POST successfully", postResponseData), HttpStatus.OK);
    }

    // 게시글 수정
    @Operation(summary = "게시글 수정", description = "게시글 ID를 이용하여 내용 수정")
    @PutMapping("/{postId}")
    public ResponseEntity<ResponseDto<PostResponseData>> modifyPostById (@Parameter(description = "수정하는 사용자 정보") @AuthenticatedUser User user,
                                                                         @Parameter(description = "게시글 ID") @PathVariable UUID postId,
                                                                         @Valid @RequestBody CreatePostDto oldPost){
        PostResponseData newPostData = this.postService.modifyPostById(user, postId, oldPost);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "modify POST successfully", newPostData), HttpStatus.OK);
    }

    // 게시글 삭제
    @Operation(summary = "게시글 삭제", description = "게시글 ID를 이용하여 게시글 삭제")
    @DeleteMapping("/{postId}")
    public ResponseEntity<ResponseDto<PostResponseData>> deletePostById (@Parameter(description = "삭제하는 사용자 정보") @AuthenticatedUser User user,
                                                                         @Parameter(description = "게시글 ID") @PathVariable UUID postId){
        this.postService.deletePostById(user, postId);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "delete POST successfully"), HttpStatus.OK);
    }
}
