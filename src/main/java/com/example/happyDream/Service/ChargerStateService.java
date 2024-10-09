package com.example.happyDream.Service;

import com.example.happyDream.DTO.ChargerDTO;
import com.example.happyDream.DTO.ChargerStateDTO;
import com.example.happyDream.Entity.ChargerStateEntity;
import com.example.happyDream.Repository.ChargerStateRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ChargerStateService {
    private final ChargerStateRepository chargerStateRepository;

    @Autowired
    public ChargerStateService(ChargerStateRepository chargerStateRepository) {
        this.chargerStateRepository = chargerStateRepository;
    }

    // ChargerStateEntity List → ChargerStateDTO List
    private List<ChargerStateDTO> convertEntityListToDtoList(List<ChargerStateEntity> entityList) {
        return entityList.stream()
                .map(ChargerStateEntity::toDTO)
                .collect(Collectors.toList());
    }

    // ChargerStateDTO List → ChargerStateEntity List
    private List<ChargerStateEntity> convertDtoListToEntityList(List<ChargerStateDTO> dtoList) {
        return dtoList.stream()
                .map(ChargerStateDTO::toEntity)
                .collect(Collectors.toList());
    }

    // 전체 충전소 충전 상태 조회
    @Transactional(readOnly = true)
    public List<ChargerStateDTO> getAllChargerState() {
        List<ChargerStateDTO> dtoList = convertEntityListToDtoList(this.chargerStateRepository.findAll());
        return dtoList;
    }

    // 전체 충전소 충전 상태 초기화
    @Transactional
    public void initAllChargerState() {
        List<ChargerStateEntity> entityList = this.chargerStateRepository.findAll();
        for (ChargerStateEntity entity : entityList) {
            entity.changeUsingYn(false);
        }
    }

    // 특정 충전소 충전 상태 추가
    @Transactional
    public void createChargerState(ChargerDTO chargerDTO) {
        try {
            this.getTargetChargerState(chargerDTO);
            log.error("존재하는 충전기에 대한 충전 상태 추가 - 충전기 id: {}", chargerDTO.getId());
        } catch (EntityNotFoundException e) {
            ChargerStateEntity entity = ChargerStateEntity.builder().chargerId(chargerDTO.toEntity()).build();
        }
    }

    // 특정 충전소 충전 상태 조회
    @Transactional(readOnly = true)
    public ChargerStateDTO getTargetChargerState(ChargerDTO chargerDto) {
        Optional<ChargerStateEntity> entity = this.chargerStateRepository.findByChargerId(chargerDto.toEntity());
        if (entity.isPresent()) {
            return entity.get().toDTO();
        }
        else {
            log.info("존재하지 않는 충전기의 충전 상태 조회 - 충전기 id: {}", chargerDto.getId());
            throw new EntityNotFoundException();
        }
    }

    // 특정 충전소 충전 상태 업데이트
    public void changeTargetChargerState(ChargerDTO chargerDto, Boolean usingYn) {
        Optional<ChargerStateEntity> entity = this.chargerStateRepository.findByChargerId(chargerDto.toEntity());
        if (entity.isPresent()) {
            entity.get().changeUsingYn(usingYn);
        }
        else {
            log.error("존재하지 않는 충전기에 대한 충전 상태 업데이트 - 충전기 id: {}", chargerDto.getId());
        }
    }
}
