package com.example.happyDream.Entity;

import com.example.happyDream.DTO.ChargerStatisticDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "charger_statistic")
@Getter //Setter 미사용
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED) //생성자 외부 접근 차단
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class) //Auditing 사용 명시
public class ChargerStatisticEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 고유 식별자 추가
    private Long id;

    @ManyToOne
    @JoinColumn(name = "charger_id")
    private ChargerEntity charger;

    @Column(updatable = false)
    @NotNull
    private LocalDate date; // 통계 기준일

    @ColumnDefault("0")
    @Column
    @NotNull
    private Integer usingSecond; // 기준일 총 사용 시간

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt; // 데이터 생성 시각

    @LastModifiedDate
    @Column
    private LocalDateTime modifiedAt; // 데이터 수정 시각

    public ChargerStatisticDTO toDTO() {
        return ChargerStatisticDTO.builder()
                .id(id)
                .chargerId(charger.getId())
                .date(date)
                .usingSecond(usingSecond)
                .createdAt(createdAt)
                .modifiedAt(modifiedAt)
                .build();
    }
}
