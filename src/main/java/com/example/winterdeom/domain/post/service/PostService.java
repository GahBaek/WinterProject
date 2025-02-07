package com.example.winterdeom.domain.post.service;

import com.example.winterdeom.domain.post.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PostService {
    private final PostRepository postRepository;
}
