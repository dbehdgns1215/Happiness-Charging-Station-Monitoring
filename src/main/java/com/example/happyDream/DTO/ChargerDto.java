package com.example.happyDream.DTO;
import com.example.happyDream.Entity.ChargerEntity;
import lombok.*;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ChargerDto {
    //츙전기 기본 데이터
    private Integer id;
    private String name;
    private String city1;
    private String city2;
    private Integer city2Code;
    private String addressNew;
    private String addressOld;
    private String addressDetail;
    private Double latitude;
    private Double longitude;

    //츙전기 운영 관련 데이터
    private Time weekdayOpen;
    private Time saturdayOpen;
    private Time holidayOpen;
    private Time weekdayClose;
    private Time saturdayClose;
    private Time holidayClose;
    private Integer chargerCount;
    private Boolean chargeAirYn;
    private Boolean chargePhoneYn;
    private String callNumber;

    //관리 목적 데이터
    private LocalDate updatedDate;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Boolean deletedYn;
    private LocalDateTime deletedAt;

    @Builder
    public ChargerDto(Integer id, String name, String city1, String city2, Integer city2Code, String addressNew, String addressOld, String addressDetail, Double latitude, Double longitude, Time weekdayOpen, Time saturdayOpen, Time holidayOpen, Time weekdayClose, Time saturdayClose, Time holidayClose, Integer chargerCount, Boolean chargeAirYn, Boolean chargePhoneYn, String callNumber, LocalDate updatedDate, LocalDateTime createdAt, LocalDateTime modifiedAt, Boolean deletedYn, LocalDateTime deletedAt) {
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

    public ChargerEntity toEntity() {
        return ChargerEntity.builder()
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
