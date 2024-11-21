package com.example.happyDream.Entity;

import com.example.happyDream.DTO.ReportDTO;
import com.example.happyDream.DTO.ReviewDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "report")
@Getter //Setter 미사용
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED) //생성자 외부 접근 차단
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class) //Auditing 사용 명시
public class ReportEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; //리뷰 식별자

    @Column(nullable = false)
    private String reportReason;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "charger_id")
    private ChargerEntity chargerId; //충전기 식별자

    @Column(length = 1024)
    private String reportContent; //리뷰 내용

    @CreatedDate
    @Column
    private LocalDateTime createdAt; //데이터 생성 시각

    @Column(nullable = false)
    private boolean checkedReport; // 리포트 확인 상태

    @Column(nullable = false)
    private boolean reportState; // 리포트 상태 (boolean)

    public void updateCheckedReport(boolean newState) {
        this.checkedReport = newState;
    }

    // 상태 변경 메서드 (필요에 따라 수정 가능)
    public void updateReportState(boolean newState) {
        this.reportState = newState;
    }

    public ReportDTO toDTO() {
        return ReportDTO.builder()
                .id(id)  // 리뷰 식별자
                .reportReason(reportReason)  // 고장 원인
                .chargerId(chargerId.getId())  // 충전소 ID
                .reportContent(reportContent)  // 리뷰 내용
                .createdAt(createdAt)  // 데이터 생성 시각
                .checkedReport(checkedReport)
                .reportState(reportState) // 리포트 상태 추가
                .build();
    }
}
