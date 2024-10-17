package com.example.happyDream.DTO;

import com.example.happyDream.Entity.ChargerEntity;
import com.example.happyDream.Entity.ChargerLogEntity;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ChargerLogDTO {
    //충전 로그 데이터
    private Long id;
    private ChargerEntity chargerId;
    private Float ampere;

    //관리 목적 데이터
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    @Builder
    public ChargerLogDTO(Long id, ChargerEntity chargerId, Float ampere, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.chargerId = chargerId;
        this.ampere = ampere;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public ChargerLogEntity toEntity() {
        return ChargerLogEntity.builder()
                .id(id)
                .chargerId(chargerId)
                .ampere(ampere)
                .createdAt(createdAt)
                .modifiedAt(modifiedAt)
                .build();
    }
}
