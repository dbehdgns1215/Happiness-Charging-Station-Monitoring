package com.example.happyDream.Entity;

import com.example.happyDream.DTO.ReviewDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "review")
@Getter //Setter 미사용
@NoArgsConstructor(access = AccessLevel.PROTECTED) //생성자 외부 접근 차단
@EntityListeners(AuditingEntityListener.class) //Auditing 사용 명시
public class ReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; //리뷰 식별자

    @OneToOne
    @JoinColumn
    private ChargerEntity chargerId; //충전기 식별자

    @ManyToOne
    @JoinColumn
    private UserEntity userId; //유저 식별자

    @Column(length = 1024)
    private String reviewContent; //리뷰 내용

    @NotNull
    @Column
    private Byte rating; //별점(1~5)

    @CreatedDate
    @NotNull
    @Column
    private LocalDateTime createdAt; //데이터 생성 시각

    @LastModifiedDate
    @NotNull
    @Column
    private LocalDateTime modifiedAt; //데이터 수정 시각

    @NotNull
    @Column(columnDefinition = "TINYINT(1) UNSIGNED DEFAULT 0")
    private Boolean deletedYn; //리뷰 삭제 여부

    @Column(insertable = false)
    private LocalDateTime deletedAt; //리뷰 삭제 시각

    @Builder
    public ReviewEntity(Integer id, ChargerEntity chargerId, UserEntity userId, String reviewContent, Byte rating, LocalDateTime createdAt, LocalDateTime modifiedAt, Boolean deletedYn, LocalDateTime deletedAt) {
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

    public ReviewDto toDto() {
        return ReviewDto.builder()
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
