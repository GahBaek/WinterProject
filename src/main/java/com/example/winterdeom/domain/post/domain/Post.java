package com.example.winterdeom.domain.post.domain;

import com.example.winterdeom.domain.common.BaseEntity;
import com.example.winterdeom.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Entity(name = "post")
public class Post extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(length = 100, nullable = false)
    private String title;
    @Column(nullable = false)
    private String content;
}
