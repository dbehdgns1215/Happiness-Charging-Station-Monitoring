package com.example.happyDream.DTO;

import com.example.happyDream.Entity.ChargerEntity;
import com.example.happyDream.Entity.ReviewEntity;
import com.example.happyDream.Entity.UserEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ReviewDto {
    //리뷰 데이터
    private Integer id;
    private ChargerEntity chargerId;
    private UserEntity userId;
    private String reviewContent;
    private Byte rating;
    
    //관리 목적 데이터
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Boolean deletedYn;
    private LocalDateTime deletedAt;

    @Builder
    public ReviewDto(Integer id, ChargerEntity chargerId, UserEntity userId, String reviewContent, Byte rating, LocalDateTime createdAt, LocalDateTime modifiedAt, Boolean deletedYn, LocalDateTime deletedAt) {
        this.id = id;
        this.chargerId = chargerId;
        this.userId = userId;
        this.reviewContent = reviewContent;
        this.rating = rating;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.deletedYn = deletedYn;
        this.deletedAt = deletedAt;
    }
    
    public ReviewEntity toEntity() {
        return ReviewEntity.builder()
                .id(id)
                .chargerId(chargerId)
                .userId(userId)
                .reviewContent(reviewContent)
                .rating(rating)
                .createdAt(createdAt)
                .modifiedAt(modifiedAt)
                .deletedYn(deletedYn)
                .deletedAt(deletedAt)
                .build();
    }
}
