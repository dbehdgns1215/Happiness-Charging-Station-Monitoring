package com.example.happyDream.Entity;

import com.example.happyDream.DTO.ChargerDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "charger")
@Getter //Setter 미사용
@NoArgsConstructor(access = AccessLevel.PROTECTED) //생성자 외부 접근 차단
@EntityListeners(AuditingEntityListener.class) //Auditing 사용 명시
public class ChargerEntity {
    //충전기 기본 데이터
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //id 자동 생성
    private Integer id; //충전기 식별자

    @NotNull
    @Column
    private String name; //충전기명

    @NotNull
    @Column
    private String city1; //시도명

    @NotNull
    @Column
    private String city2; //시군구명

    @NotNull
    @Column(columnDefinition = "SMALLINT UNSIGNED")
    private Integer city2Code; //시군구 코드(5자리, 60000번대 없음)

    @Column(length = 128)
    private String addressNew; //도로명 주소

    @Column(length = 128)
    private String addressOld; //지번 주소(구주소)

    @Column(length = 64)
    private String addressDetail; //상세 설치 위치

    @NotNull
    @Column
    private Double latitude; //위도

    @NotNull
    @Column
    private Double longitude; //경도

    //츙전기 운영 관련 데이터
    @Column
    private Time weekdayOpen; //평일 운영 시작 시각
    @Column
    private Time saturdayOpen; //토요일 운영 시작 시각
    @Column
    private Time holidayOpen; //공휴일 운영 시작 시각
    @Column
    private Time weekdayClose; //평일 운영 종료 시각
    @Column
    private Time saturdayClose; //토요일 운영 종료 시각
    @Column
    private Time holidayClose; //공휴일 운영 종료 시각

    @NotNull
    @Column(columnDefinition = "TINYINT UNSIGNED")
    private Integer chargerCount; //동시 충전 가능 대수

    @Column(columnDefinition = "TINYINT(1) UNSIGNED DEFAULT 0")
    private Boolean chargeAirYn; //공기 주입 가능 여부(Y/N)

    @Column(columnDefinition = "TINYINT(1) UNSIGNED DEFAULT 0")
    private Boolean chargePhoneYn; //휴대폰 충전 가능 여부(Y/N)

    @Column(length = 16)
    private String callNumber; //관리 기관 연락처

    //관리 목적 데이터
    @NotNull
    @Column
    private LocalDate updatedDate; //기준일자(원본 데이터 기준)

    @CreatedDate
    @Column(updatable = false) //업데이트 차단
    private LocalDateTime createdAt; //데이터 생성 시각

    @LastModifiedDate
    @Column
    private LocalDateTime modifiedAt; //데이터 수정 시각

    @Column(columnDefinition = "TINYINT(1) UNSIGNED DEFAULT 0")
    private Boolean deletedYn; //충전기 삭제 여부(Y/N)

    @Column(insertable = false)
    private LocalDateTime deletedAt; //충전기 삭제 시각

    @Builder //추후 객체 생성 로직, 검증 코드 필요할 수 있으므로 Builder 별도 생성
    public ChargerEntity(Integer id, String name, String city1, String city2, Integer city2Code, String addressNew, String addressOld, String addressDetail, Double latitude, Double longitude, Time weekdayOpen, Time saturdayOpen, Time holidayOpen, Time weekdayClose, Time saturdayClose, Time holidayClose, Integer chargerCount, Boolean chargeAirYn, Boolean chargePhoneYn, String callNumber, LocalDate updatedDate, LocalDateTime createdAt, LocalDateTime modifiedAt, Boolean deletedYn, LocalDateTime deletedAt) {
        this.id = id;
        this.name = name;
        this.city1 = city1;
        this.city2 = city2;
        this.city2Code = city2Code;
        this.addressNew = addressNew;
        this.addressOld = addressOld;
        this.addressDetail = addressDetail;
        this.latitude = latitude;
        this.longitude = longitude;
        this.weekdayOpen = weekdayOpen;
        this.saturdayOpen = saturdayOpen;
        this.holidayOpen = holidayOpen;
        this.weekdayClose = weekdayClose;
        this.saturdayClose = saturdayClose;
        this.holidayClose = holidayClose;
        this.chargerCount = chargerCount;
        this.chargeAirYn = chargeAirYn;
        this.chargePhoneYn = chargePhoneYn;
        this.callNumber = callNumber;
        this.updatedDate = updatedDate;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.deletedYn = deletedYn;
        this.deletedAt = deletedAt;
    }

    public ChargerDTO toDTO() {
        return ChargerDTO.builder()
                .id(id)
                .name(name)
                .city1(city1)
                .city2(city2)
                .city2Code(city2Code)
                .addressNew(addressNew)
                .addressOld(addressOld)
                .addressDetail(addressDetail)
                .latitude(latitude)
                .longitude(longitude)
                .weekdayOpen(weekdayOpen)
                .saturdayOpen(saturdayOpen)
                .holidayOpen(holidayOpen)
                .weekdayClose(weekdayClose)
                .saturdayClose(saturdayClose)
                .holidayClose(holidayClose)
                .chargerCount(chargerCount)
                .chargeAirYn(chargeAirYn)
                .chargePhoneYn(chargePhoneYn)
                .callNumber(callNumber)
                .updatedDate(updatedDate)
                .createdAt(createdAt)
                .modifiedAt(modifiedAt)
                .deletedYn(deletedYn)
                .deletedAt(deletedAt)
                .build();
    }
}