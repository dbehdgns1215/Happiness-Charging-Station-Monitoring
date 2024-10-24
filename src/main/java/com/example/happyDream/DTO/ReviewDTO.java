package com.example.happyDream.DTO;

import com.example.happyDream.Entity.ChargerEntity;
import com.example.happyDream.Entity.ReviewEntity;
import com.example.happyDream.Entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ReviewDTO {
    //리뷰 데이터
    private Integer id;
    private Integer chargerId;  // 수정: ChargerEntity 대신 Integer
    private Integer userId;     // 수정: UserEntity 대신 Integer
    private String reviewContent;
    private Byte rating;
    
    //관리 목적 데이터
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Boolean deletedYn;
    private LocalDateTime deletedAt;

    @Builder
    public ReviewDTO(Integer id, Integer chargerId, Integer userId, String reviewContent, Byte rating, LocalDateTime createdAt, LocalDateTime modifiedAt, Boolean deletedYn, LocalDateTime deletedAt) {
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

    /* // TODO - 앱에서 리뷰를 보낼 땐, 충전소와 유저의 값이 int로 넘어옴. 엔티티로 변환하는 로직 수정 필요
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

     */

    // Getters and Setters
    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public byte getRating() {
        return rating;
    }

    public void setRating(byte rating) {
        this.rating = rating;
    }
}
