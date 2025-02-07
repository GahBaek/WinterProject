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
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    // 게시글 생성
    public void createPost (User user, CreatePostDto createPostDto){
        Post post = new Post(user, createPostDto.getTitle(), createPostDto.getContent());

        postRepository.save(post);
    }

    // 게시글 조회
    public PostResponseDataList getAllPosts (User user){
        List<PostResponseData> postList = new ArrayList<>();

        for(Post post : postRepository.findAll()){
            PostResponseData postResponseData = PostResponseData.from(post);
            postList.add(postResponseData);
        }

        PostResponseDataList postResponseDataList = PostResponseDataList.from(postList);
        return postResponseDataList;
    }

    // 특정 게시글 조회
    public PostResponseData getPostById (User user, UUID postId) {
        Post post = findPost(user, postId);
        PostResponseData postResponseData = PostResponseData.from(post);
        return postResponseData;
    }

    // 게시글 수정
    public PostResponseData modifyPostById (User user, UUID postId, CreatePostDto newPostDto){
        Post oldPost = findPost(user, postId);

        oldPost.setTitle(newPostDto.getTitle());
        oldPost.setContent(newPostDto.getContent());
        postRepository.save(oldPost);
        PostResponseData postResponseData = PostResponseData.from(oldPost);
        return postResponseData;
    }

    // 게시글 삭제
    public void deletePostById (User user, UUID postId){
        Post post = findPost(user, postId);
        postRepository.delete(post);
    }

    // 게시글 찾기
    private Post findPost(User user, UUID postId){
        return postRepository.findByUserIdandId(user.getId(), postId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.POST_NOT_FOUND, "Post not found"));
    }
}
