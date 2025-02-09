package com.example.winterdeom.domain.post.controller;


import com.example.winterdeom.domain.common.ResponseDto;
import com.example.winterdeom.domain.common.exception.dto.ErrorResponseDto;
import com.example.winterdeom.domain.post.dto.request.CreatePostDto;
import com.example.winterdeom.domain.post.dto.response.PostResponseData;
import com.example.winterdeom.domain.post.dto.response.PostResponseDataList;
import com.example.winterdeom.domain.post.service.PostService;
import com.example.winterdeom.domain.user.domain.User;
import com.example.winterdeom.global.auth.AuthenticatedUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

/*
     게시글 생성
*/
    @Operation(summary = "게시글 생성", description = "새로운 게시글 작성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 생성 성공", content = @Content(schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PostMapping
    public ResponseEntity<ResponseDto<Void>> createPost (
            @Parameter(description = "게시글 작성자 정보") @AuthenticatedUser User user,
            @Valid @RequestBody CreatePostDto createPostDto){
        log.info("게시글 작성");
        postService.createPost(user, createPostDto);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "create POST successfully"), HttpStatus.CREATED);
    }

/*
     게시글 조회
*/
    @Operation(summary = "전체 게시글 조회", description = "모든 게시글 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 조회 성공", content = @Content(schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "9001", description = "필수값 누락", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/all")
    public ResponseEntity<ResponseDto<PostResponseDataList>> getAllPosts (@Parameter(description = "조회하는 사용자 정보") @AuthenticatedUser User user){
        PostResponseDataList postResponseDataList = PostResponseDataList.from(postService.getAllPosts(user));
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "get all POSTs successfully", postResponseDataList), HttpStatus.OK);
    }

/*
     특정 게시글 조회
*/
    @Operation(summary = "특정 게시글 조회", description = "게시글 ID를 이용하여 특정 게시글 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "특정 게시글 조회 성공", content = @Content(schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "4030", description = "접근 권한 없음", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/{postId}")
    public ResponseEntity<ResponseDto<PostResponseData>> getPostById (@Parameter(description = "조회하는 사용자 정보") @AuthenticatedUser User user,
                                                                      @Parameter(description = "게시글 ID") @PathVariable UUID postId){
        PostResponseData postResponseData = postService.getPostById(user, postId);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "get POST successfully", postResponseData), HttpStatus.OK);
    }

/*
     본인이 작성한 게시글만 조회하기
*/
    @Operation(summary = "본인이 작성한 게시글 조회", description = "사용자 Id를 이용하여 게시글 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사용자 게시글 조회 성공", content = @Content(schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "4030", description = "접근 권한 없음", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping()
    public ResponseEntity<ResponseDto<PostResponseDataList>> getPostByUser (@Parameter(description = "조회하는 사용자 정보") @AuthenticatedUser User user){
        PostResponseDataList postResponseData = PostResponseDataList.from(postService.getPostByUser(user));
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "get my POST successfully", postResponseData), HttpStatus.OK);
    }

/*
     게시글 수정
*/
    @Operation(summary = "게시글 수정", description = "게시글 ID를 이용하여 내용 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 수정 성공", content = @Content(schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "4030", description = "접근 권한 없음", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PutMapping("/{postId}")
    public ResponseEntity<ResponseDto<PostResponseData>> modifyPostById (@Parameter(description = "수정하는 사용자 정보") @AuthenticatedUser User user,
                                                                         @Parameter(description = "게시글 ID") @PathVariable UUID postId,
                                                                         @Valid @RequestBody CreatePostDto newPost){
        PostResponseData newPostData = postService.modifyPostById(user, postId, newPost);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "modify POST successfully", newPostData), HttpStatus.OK);
    }

/*
     게시글 삭제
*/
    @Operation(summary = "게시글 삭제", description = "게시글 ID를 이용하여 게시글 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 삭제 성공", content = @Content(schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "4030", description = "접근 권한 없음", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @DeleteMapping("/{postId}")
    public ResponseEntity<ResponseDto<PostResponseData>> deletePostById (@Parameter(description = "삭제하는 사용자 정보") @AuthenticatedUser User user,
                                                                         @Parameter(description = "게시글 ID") @PathVariable UUID postId){
        postService.deletePostById(user, postId);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "delete POST successfully"), HttpStatus.OK);
    }
}
