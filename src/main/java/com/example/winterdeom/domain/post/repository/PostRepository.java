package com.example.winterdeom.domain.post.repository;

import com.example.winterdeom.domain.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {
    Optional<Post> findByUserIdAndId(UUID userId, UUID postId);

    List<Post> findByUserId(UUID userId);
}
