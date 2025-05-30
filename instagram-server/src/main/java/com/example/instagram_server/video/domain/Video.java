package com.example.instagram_server.video.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false, unique = true)
    private String videoId;
    @Column(name="uploaded_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime uploadedAt;
}
