package com.example.happyDream.DTO;

import com.example.happyDream.Entity.ChargerEntity;
import com.example.happyDream.Entity.ChargerLogEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChargerLogDTO {
    //충전 로그 데이터
    private Long id;
    private Integer chargerId;
    private Float ampere;

    //관리 목적 데이터
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public ChargerLogEntity toEntity() {
        ChargerEntity chargerEntity = ChargerEntity.builder().id(chargerId).build();
        return ChargerLogEntity.builder()
                .id(id)
                .charger(chargerEntity)
                .ampere(ampere)
                .createdAt(createdAt)
                .modifiedAt(modifiedAt)
                .build();
    }
}
