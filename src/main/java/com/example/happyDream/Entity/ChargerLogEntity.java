package com.example.happyDream.Entity;

import com.example.happyDream.DTO.ChargerLogDTO;
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
public class ChargerLogEntity {
    //충전 로그 데이터
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // https://netframework.tistory.com/entry/Hibernate-OneToOne
    // OneToOne은 어렵다!!!!
    @ManyToOne(fetch = FetchType.LAZY) //충전기 1대 당 로그 여러개
    @JoinColumn/*(name = "charger_id")*/ //키 매핑 에러 시 고려
    private ChargerEntity chargerId;

    @NotNull
    @Column
    private Float ampere;

    //관리 목적 데이터
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column
    private LocalDateTime modifiedAt;

    @Builder
    public ChargerLogEntity(Long id, ChargerEntity chargerId, Float ampere, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.chargerId = chargerId;
        this.ampere = ampere;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public ChargerLogDTO toDTO() {
        return ChargerLogDTO.builder()
                .id(id)
                .chargerId(chargerId)
                .ampere(ampere)
                .createdAt(createdAt)
                .modifiedAt(modifiedAt)
                .build();
    }
}
