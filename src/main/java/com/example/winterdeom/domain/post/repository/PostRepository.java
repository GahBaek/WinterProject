package com.example.winterdeom.domain.post.repository;

import com.example.winterdeom.domain.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {
}
