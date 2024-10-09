package com.example.happyDream.Service;

import com.example.happyDream.DTO.ChargerDto;
import com.example.happyDream.DTO.ChargerLogDto;
import com.example.happyDream.Entity.ChargerEntity;
import com.example.happyDream.Entity.ChargerLogEntity;
import com.example.happyDream.Repository.ChargerLogRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ChargerLogService {
    private final ChargerLogRepository chargerLogRepository;

    @Autowired
    public ChargerLogService(ChargerLogRepository chargerLogRepository) {
        this.chargerLogRepository = chargerLogRepository;
    }

    // ChargerLogEntity List → ChargerLogDTO List
    private List<ChargerLogDto> convertEntityListToDtoList(List<ChargerLogEntity> entityList) {
        if (entityList.isEmpty()) {
            log.warn("DTO list가 비어있음");
            throw new EntityNotFoundException();
        }
        return entityList.stream()
                .map(ChargerLogEntity::toDto)
                .collect(Collectors.toList());
    }

    // ChargerLogDTO List → ChargerLogEntity List
    private List<ChargerLogEntity> convertDtoListToEntityList(List<ChargerLogDto> dtoList) {
        if (dtoList.isEmpty()) {
            log.warn("Entity list가 비어있음");
            throw new EntityNotFoundException();
        }
        return dtoList.stream()
                .map(ChargerLogDto::toEntity)
                .collect(Collectors.toList());
    }

    // 전체 충전 로그 조회
    public List<ChargerLogDto> getAllChargerLog() {
        List<ChargerLogDto> dtoList = convertEntityListToDtoList(this.chargerLogRepository.findAll());
        return dtoList;
    }

    // 전체 충전 로그 삭제
    public void deleteAllChargerLog() {
        int beforeCount = getAllChargerLog().size();
        this.chargerLogRepository.deleteAll();
        int afterCount = beforeCount - getAllChargerLog().size();
        log.warn("전체 충전 로그 {}개가 삭제됨", afterCount);
    }

    // 특정 충전기의 전체 충전 로그 조회
    public List<ChargerLogDto> getAllTargetChargerLog(ChargerDto ChargerDto) {
        List<ChargerLogDto> dtoList = convertEntityListToDtoList(this.chargerLogRepository.findAllByChargerId(ChargerDto.toEntity()));
        return dtoList;
    }

    // 특정 충전기 충전 로그 추가
    public void createTargetChargerLog(ChargerLogDto chargerLogDto) {
        this.chargerLogRepository.save(chargerLogDto.toEntity());
    }
    // TODO - 충전기 하드웨어 설정 값 조회
    // TODO - 충전기 하드웨어 설정 값 변경
}
