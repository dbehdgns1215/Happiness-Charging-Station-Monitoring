package com.example.happyDream.Service;

import com.example.happyDream.Entity.ChargerEntity;
import com.example.happyDream.Repository.ChargerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Service
public class ChargerService {
    @Autowired
    private ChargerRepository chargerRepository;

    public void saveChargersFromJson(String filePath) {
        // 데이터베이스에 저장된 충전기 수 확인
        long count = chargerRepository.count(); // 레코드 수 확인

        if (count == 0) { // 데이터베이스가 비어있으면만 수행
            try (FileReader reader = new FileReader(filePath)) {
                Gson gson = new Gson();
                Map<String, Object> jsonMap = gson.fromJson(reader, new TypeToken<Map<String, Object>>() {}.getType());
                List<Map<String, String>> records = (List<Map<String, String>>) jsonMap.get("records");

                LocalDateTime now = LocalDateTime.now(); // 현재 시간

                for (Map<String, String> record : records) {
                    ChargerEntity charger = ChargerEntity.builder()
                            .name(record.get("시설명"))
                            .city1(record.get("시도명"))
                            .city2(record.get("시군구명"))
                            .city2Code(Integer.valueOf(record.get("시군구코드")))
                            .addressNew(record.get("소재지도로명주소"))
                            .addressOld(record.get("소재지지번주소"))
                            .latitude(Double.valueOf(record.get("위도")))
                            .longitude(Double.valueOf(record.get("경도")))
                            .weekdayOpen(Time.valueOf(record.get("평일운영시작시각") + ":00"))
                            .weekdayClose(Time.valueOf(record.get("평일운영종료시각") + ":00"))
                            .saturdayOpen(Time.valueOf(record.get("토요일운영시작시각") + ":00"))
                            .saturdayClose(Time.valueOf(record.get("토요일운영종료시각") + ":00"))
                            .holidayOpen(Time.valueOf(record.get("공휴일운영시작시각") + ":00"))
                            .holidayClose(Time.valueOf(record.get("공휴일운영종료시각") + ":00"))
                            .chargerCount(Integer.valueOf(record.get("동시사용가능대수")))
                            .chargeAirYn(record.get("공기주입가능여부").equals("Y"))
                            .chargePhoneYn(record.get("휴대전화충전가능여부").equals("Y"))
                            .callNumber(record.get("관리기관전화번호"))
                            .updatedDate(LocalDate.parse(record.get("데이터기준일자")))
                            .createdAt(now) // 현재 시간 설정
                            .modifiedAt(now) // 현재 시간 설정
                            .deletedYn(false) // 기본값으로 false 설정
                            .build();

                    chargerRepository.save(charger);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
