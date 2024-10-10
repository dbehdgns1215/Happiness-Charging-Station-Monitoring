package com.example.happyDream.Service;

import com.example.happyDream.Entity.ChargerEntity;
import com.example.happyDream.Repository.ChargerRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.happyDream.DTO.ChargerDTO;

import java.io.FileReader;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChargerService {
    private final ChargerRepository chargerRepository;

    @Autowired
    public ChargerService(ChargerRepository chargerRepository) {
        this.chargerRepository = chargerRepository;
    }

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

    public List<ChargerDTO> convertEntityListToDtoList(List<ChargerEntity> entityList) {
        // Stream을 사용하여 Entity -> DTO 변환 후 리스트로 반환
        return entityList.stream()
                .map(ChargerEntity::toDTO) // 각 엔티티를 DTO로 변환
                .collect(Collectors.toList());  // 변환된 결과를 리스트로 수집
    }
    public List<ChargerEntity> convertDtoListToEntityList(List<ChargerDTO> dtoList) {
        // Stream을 사용하여 Entity -> DTO 변환 후 리스트로 반환
        return dtoList.stream()
                .map(ChargerDTO::toEntity) // 각 엔티티를 DTO로 변환
                .collect(Collectors.toList());  // 변환된 결과를 리스트로 수집
    }
    public List<ChargerDTO> chargerSelectAll(){
        List<ChargerDTO> dtoList = convertEntityListToDtoList(this.chargerRepository.findAll());
        System.out.println("가져온 충전소 수: " + dtoList.size());
        return dtoList;
    }

    // 전체 충전기 삭제
    public void chargerDeleteAll(){
        this.chargerRepository.deleteAll();
    }

    // 특정 충전기 조회
    public ChargerDTO chargerSelect(Integer id){
        Optional<ChargerEntity> entity = this.chargerRepository.findById(id);
        if(entity.isPresent()) {
            return entity.get().toDTO();
        } else {
            throw new EntityNotFoundException();
        }
    }

    // 특정 충전기 삭제
    public void chargerDelete(Integer id){
        this.chargerRepository.deleteById(id);
    }
}
