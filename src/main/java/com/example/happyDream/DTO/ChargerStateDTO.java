package com.example.happyDream.DTO;

import com.example.happyDream.Entity.ChargerEntity;
import com.example.happyDream.Entity.ChargerStateEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ChargerStateDTO {
    //충전 상태 기본 데이터
    private ChargerEntity chargerId;
    private Boolean usingYn;
    private Boolean brokenYn;

    //관리 목적 데이터
    private LocalDateTime usingAt;
    private LocalDateTime brokenAt;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    @Builder
    public ChargerStateDTO(ChargerEntity chargerId, Boolean usingYn, Boolean brokenYn, LocalDateTime usingAt, LocalDateTime brokenAt, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.chargerId = chargerId;
        this.usingYn = usingYn;
        this.brokenYn = brokenYn;
        this.usingAt = usingAt;
        this.brokenAt = brokenAt;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
    
    public ChargerStateEntity toEntity() {
        return ChargerStateEntity.builder()
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
