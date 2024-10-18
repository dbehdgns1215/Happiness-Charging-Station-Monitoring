package com.example.happyDream.Service;

import com.example.happyDream.DTO.ChargerDTO;
import com.example.happyDream.DTO.ChargerLogDTO;
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
    private List<ChargerLogDTO> convertEntityListToDtoList(List<ChargerLogEntity> entityList) {
        if (entityList.isEmpty()) {
            log.warn("DTO list가 비어있음");
            throw new EntityNotFoundException();
        }
        return entityList.stream()
                .map(ChargerLogEntity::toDTO)
                .collect(Collectors.toList());
    }

    // ChargerLogDTO List → ChargerLogEntity List
    private List<ChargerLogEntity> convertDtoListToEntityList(List<ChargerLogDTO> dtoList) {
        if (dtoList.isEmpty()) {
            log.warn("Entity list가 비어있음");
            throw new EntityNotFoundException();
        }
        return dtoList.stream()
                .map(ChargerLogDTO::toEntity)
                .collect(Collectors.toList());
    }

    // 전체 충전 로그 조회
    public List<ChargerLogDTO> getAllChargerLog(Boolean join) {
        List<ChargerLogDTO> dtoList = convertEntityListToDtoList(this.chargerLogRepository.findAll());
        if (join == false) {
            for (ChargerLogDTO dto : dtoList) {
                ChargerDTO chargerDto = ChargerDTO.builder().id(dto.getChargerId().getId()).build();
                dto.setChargerId(chargerDto.toEntity());
            }
        }
        return dtoList;
    }

    // 전체 충전 로그 삭제
    public Long deleteAllChargerLog() {
        long count = chargerLogRepository.count();
        this.chargerLogRepository.deleteAll();
        count = count - chargerLogRepository.count();
        log.warn("전체 충전 로그 {}개가 삭제됨", count);
        return count;
    }

    // 특정 충전기의 전체 충전 로그 조회
    public List<ChargerLogDTO> getAllTargetChargerLog(ChargerDTO ChargerDto) {
        List<ChargerLogDTO> dtoList = convertEntityListToDtoList(this.chargerLogRepository.findAllByChargerId(ChargerDto.toEntity()));
        return dtoList;
    }

    // 특정 충전기 충전 로그 추가
    public void createTargetChargerLog(ChargerLogDTO chargerLogDto) {
        this.chargerLogRepository.save(chargerLogDto.toEntity());
    }
    // TODO - 충전기 하드웨어 설정 값 조회
    // TODO - 충전기 하드웨어 설정 값 변경
}
