package com.example.happyDream.Entity;

import com.example.happyDream.DTO.ChargerStateDTO;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "charger_state")
@Getter //Setter 미사용
@NoArgsConstructor(access = AccessLevel.PROTECTED) //생성자 외부 접근 차단
@EntityListeners(AuditingEntityListener.class) //Auditing 사용 명시
public class ChargerStateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 고유 식별자 추가
    private Integer id;

    @ManyToOne
    @JoinColumn(unique = true) //OneToOne 대체
    private ChargerEntity chargerId; // 충전기 식별자

    @ColumnDefault("0")
    @Column(columnDefinition = "TINYINT(1) UNSIGNED DEFAULT 0")
    private Boolean usingYn; //충전 여부(Y/N)

    @ColumnDefault("0")
    @Column(columnDefinition = "TINYINT(1) UNSIGNED DEFAULT 0")
    private Boolean brokenYn; //고장 여부(Y/N)

    @CreatedDate
    @Column
    private LocalDateTime usingAt; //고장 상태 변경 시각

    @CreatedDate
    @Column
    private LocalDateTime brokenAt; //고장 여부 변경 시각

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt; //데이터 생성 시각

    @LastModifiedDate
    @Column
    private LocalDateTime modifiedAt; //데이터 수정 시각

    @PrePersist
    public void checkYnNull() {
        if (this.usingYn == null) { this.usingYn = false; }
        if (this.brokenYn == null) { this.brokenYn = false; }
    }

    @Builder
    public ChargerStateEntity(Integer id, ChargerEntity chargerId, Boolean usingYn, Boolean brokenYn, LocalDateTime usingAt, LocalDateTime brokenAt, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.chargerId = chargerId;
        this.usingYn = usingYn;
        this.brokenYn = brokenYn;
        this.usingAt = usingAt;
        this.brokenAt = brokenAt;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public ChargerStateDTO toDTO() {
        return ChargerStateDTO.builder()
                .id(id)
                .chargerId(chargerId)
                .usingYn(usingYn)
                .brokenYn(brokenYn)
                .usingAt(usingAt)
                .brokenAt(brokenAt)
                .createdAt(createdAt)
                .modifiedAt(modifiedAt)
                .build();
    }

    public void changeUsingYn(Boolean value) {
        if (value != null) {
            this.usingYn = value;
            this.usingAt = LocalDateTime.now();
        }
    }

    public void changeBrokenYn(Boolean value) {
        if (value != null) {
            this.brokenYn = value;
            this.brokenAt = LocalDateTime.now();
        }
    }
}
