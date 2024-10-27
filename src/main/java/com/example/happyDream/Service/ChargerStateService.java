package com.example.happyDream.Service;

import com.example.happyDream.DTO.ChargerDTO;
import com.example.happyDream.DTO.ChargerStateDTO;
import com.example.happyDream.Entity.ChargerEntity;
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

    /* ===== PRIVATE ===== */

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

    // 특정 충전기 상태 조회(엔티티 직접 반환 - 값 변경 시 사용) [레거시]
    private ChargerStateEntity getTargetChargerStateLegacy(ChargerDTO chargerDto) {
        Optional<ChargerStateEntity> entity = this.chargerStateRepository.findByCharger(chargerDto.toEntity());
        if (entity.isPresent()) {
            return entity.get();
        }
        else {
            throw new EntityNotFoundException();
        }
    }

    /* ===== PUBLIC ===== */

    // 전체 충전기 상태 조회(읽기 전용)
    @Transactional(readOnly = true)
    public List<ChargerStateDTO> getAllChargerState() {
        List<ChargerStateDTO> dtoList = convertEntityListToDtoList(this.chargerStateRepository.findAll());

        if (dtoList.isEmpty()) {
            log.info("데이터가 비어있음");
        }

        return dtoList;
    }

    // 전체 충전기 상태 초기화
    @Transactional
    public void initAllChargerState() {
        List<ChargerStateEntity> entityList = this.chargerStateRepository.findAll();
        for (ChargerStateEntity entity : entityList) {
            entity.changeUsingYn(false);
            entity.changeBrokenYn(false);
        }
    }

    // 특정 충전기 상태 조회(읽기 전용)
    // 존재하는 경우 DTO 반환, 없는 경우 예외 발생
    @Transactional(readOnly = true)
    public ChargerStateDTO getTargetChargerState(ChargerDTO chargerDto) {
        try {
            ChargerStateEntity entity = getTargetChargerStateLegacy(chargerDto);
            return entity.toDTO();
        } catch (EntityNotFoundException e) {
            log.info("존재하지 않는 충전기의 상태 조회 - 충전기 id: {}", chargerDto.getId());
            throw new EntityNotFoundException();
        }
    }

    // 특정 충전기 상태 추가
    @Transactional
    public void createChargerState(ChargerDTO chargerDto) {
        // 필요성 검토중
//        try {
//            this.getTargetChargerState(chargerDto);
//            log.error("이미 존재하는 충전기 상태에 대한 추가 - 충전기 id: {}", chargerDto.getId());
//            //throw new EntityExistsException(); // 테스트용
//        } catch (EntityNotFoundException e) {
//            ChargerStateEntity entity = ChargerStateEntity.builder().chargerId(chargerDto.toEntity()).build();
//            this.chargerStateRepository.save(entity);
//        }
        ChargerStateEntity entity = ChargerStateEntity.builder().charger(chargerDto.toEntity()).build();
        this.chargerStateRepository.save(entity);
    }

    // 특정 충전기 상태 추가 [레거시] - 트랜잭션용
    @Transactional
    public void createChargerStateLegacy(ChargerEntity chargerEntity) {
        ChargerStateEntity entity = ChargerStateEntity.builder().charger(chargerEntity).build();
        this.chargerStateRepository.save(entity);
    }

    // 특정 충전기 상태 업데이트
    @Transactional
    public void changeTargetChargerState(ChargerStateDTO chargerStateDto) {
        Integer chargerId = chargerStateDto.getChargerId();
        ChargerDTO chargerDto = ChargerDTO.builder().id(chargerId).build();

        try {
            ChargerStateEntity entity = getTargetChargerStateLegacy(chargerDto);

            entity.changeUsingYn(chargerStateDto.getUsingYn());
            entity.changeBrokenYn(chargerStateDto.getBrokenYn());
        } catch (EntityNotFoundException e) {
            log.error("존재하지 않는 충전기에 대한 상태 업데이트 - 충전기 id: {}", chargerId);
            //throw new EntityNotFoundException(); // 테스트용
        }
    }
}