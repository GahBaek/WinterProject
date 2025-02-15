package com.example.winterdeom.domain.post.service;

import com.example.winterdeom.domain.common.error.ErrorCode;
import com.example.winterdeom.domain.common.exception.NotFoundException;
import com.example.winterdeom.domain.post.domain.Post;
import com.example.winterdeom.domain.post.dto.request.CreatePostDto;
import com.example.winterdeom.domain.post.dto.response.PostResponseData;
import com.example.winterdeom.domain.post.dto.response.PostResponseDataList;
import com.example.winterdeom.domain.post.repository.PostRepository;
import com.example.winterdeom.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@AllArgsConstructor
@Slf4j
public class PostService {
    private final PostRepository postRepository;
    /*
    게시글 생성
     */
    @Transactional
    public PostResponseData createPost (User user, CreatePostDto createPostDto){
        log.info("create post");
        Post post = Post.builder()
                .title(createPostDto.getTitle())
                .content(createPostDto.getContent())
                .user(user).build();

        postRepository.save(post);
        PostResponseData postResponseData = PostResponseData.from(post);
        return postResponseData;
    }

    /*
    모든 게시글 조회
     */
    @Transactional(readOnly = true)
    public List<PostResponseData> getAllPosts (User user){
        List<PostResponseData> postList = postRepository.findAll().stream()
                .map(PostResponseData::from)
                .toList();
        return postList;
    }

    /*
    특정 게시글 조회
     */
    @Transactional(readOnly = true)
    public PostResponseData getPostById (User user, UUID postId) {
        Post post = findPost(user, postId);
        PostResponseData postResponseData = PostResponseData.from(post);
        return postResponseData;
    }

    /*
    본인이 작성한 게시글들 조회
     */
    @Transactional(readOnly = true)
    public List<PostResponseData> getPostByUser(User user) {
        List<PostResponseData> postList = postRepository.findByUserId(user.getId()).stream()
                .map(PostResponseData::from)
                .toList();
        return postList;
    }

    /*
    게시글 수정
     */
    @Transactional
    public PostResponseData modifyPostById (User user, UUID postId, CreatePostDto newPostDto){
        Post oldPost = findPost(user, postId);

        oldPost.update(newPostDto.getTitle(), newPostDto.getContent());
        postRepository.save(oldPost);
        PostResponseData postResponseData = PostResponseData.from(oldPost);
        return postResponseData;
    }

    /*
    게시글 삭제
     */
    @Transactional
    public void deletePostById (User user, UUID postId){
        Post post = findPost(user, postId);
        postRepository.delete(post);
    }

    /*
    게시글 찾기
     */
    private Post findPost(User user, UUID postId){
        return postRepository.findByUserIdAndId(user.getId(), postId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.POST_NOT_FOUND, "Post not found"));
    }
}
