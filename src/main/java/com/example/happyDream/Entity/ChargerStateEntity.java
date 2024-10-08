package com.example.happyDream.Entity;

import com.example.happyDream.DTO.ChargerStateDto;
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
@Table(name = "charger_log")
@Getter //Setter 미사용
@NoArgsConstructor(access = AccessLevel.PROTECTED) //생성자 외부 접근 차단
@EntityListeners(AuditingEntityListener.class) //Auditing 사용 명시
public class ChargerStateEntity {
    @Id
    @OneToOne
    private ChargerEntity chargerId; //충전기 식별자

    @NotNull
    @Column(columnDefinition = "TINYINT(1) UNSIGNED DEFAULT 0")
    private Boolean usingYn; //충전 여부(Y/N)

    @NotNull
    @Column(columnDefinition = "TINYINT(1) UNSIGNED DEFAULT 0")
    private Boolean brokenYn; //고장 여부(Y/N)

    @CreatedDate
    @NotNull
    private LocalDateTime usingAt; //고장 상태 변경 시각

    @CreatedDate
    @NotNull
    @Column
    private LocalDateTime brokenAt; //고장 여부 변경 시각

    @CreatedDate
    @NotNull
    @Column(updatable = false)
    private LocalDateTime createdAt; //데이터 생성 시각

    @LastModifiedDate
    @Column
    private LocalDateTime modifiedAt; //데이터 수정 시각

    @Builder
    public ChargerStateEntity(ChargerEntity chargerId, Boolean usingYn, Boolean brokenYn, LocalDateTime usingAt, LocalDateTime brokenAt, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.chargerId = chargerId;
        this.usingYn = usingYn;
        this.brokenYn = brokenYn;
        this.usingAt = usingAt;
        this.brokenAt = brokenAt;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public ChargerStateDto toDto() {
        return ChargerStateDto.builder()
                .chargerId(chargerId)
                .usingYn(usingYn)
                .brokenYn(brokenYn)
                .usingAt(usingAt)
                .brokenAt(brokenAt)
                .createdAt(createdAt)
                .modifiedAt(modifiedAt)
                .build();
    }
}
