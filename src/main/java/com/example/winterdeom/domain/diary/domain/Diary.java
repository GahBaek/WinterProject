package com.example.winterdeom.domain.diary.domain;

import com.example.winterdeom.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Diary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diary_id", nullable = false, unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;  // 작성한 사용자

    @Enumerated(EnumType.STRING)
    private Emotion emotion;  // 감정

    @Column(columnDefinition = "TEXT")
    private String content;  // 일기 내용

    private String imageUrls; //이미지 파일

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public void update(Emotion emotion, String content, String imageUrls) {
        if(emotion != null){
            this.emotion = emotion;
        }
        if(content != null){
            this.content = content;
        }
        if(imageUrls != null){
            this.imageUrls = imageUrls;
        }
    }
}
