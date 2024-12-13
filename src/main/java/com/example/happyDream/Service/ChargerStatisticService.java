package com.example.happyDream.Service;

import com.example.happyDream.DTO.ChargerDTO;
import com.example.happyDream.DTO.ChargerLogDTO;
import com.example.happyDream.DTO.ChargerStatisticDTO;
import com.example.happyDream.Entity.ChargerLogEntity;
import com.example.happyDream.Entity.ChargerStatisticEntity;
import com.example.happyDream.Repository.ChargerStatisticRepository;
import com.example.happyDream.Util.Converter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ChargerStatisticService {
    private final ChargerStatisticRepository chargerStatisticRepository;

    @Autowired
    public ChargerStatisticService(ChargerStatisticRepository chargerStatisticRepository) {
        this.chargerStatisticRepository = chargerStatisticRepository;
    }

    // 전체 충전기 이용 내역 조회
    @Transactional(readOnly = true)
    public List<ChargerStatisticDTO> getAllChargerStatistic() {
        return Converter.EntityListToDtoList(this.chargerStatisticRepository.findAll(), ChargerStatisticEntity::toDTO);
    }

    // 전체 충전기 이용 내역 삭제
    @Transactional
    public void deleteAllChargerStatistic() {
        this.chargerStatisticRepository.deleteAll();
    }

    // 특정 충전기의 이용 내역 조회
    @Transactional(readOnly = true)
    public List<ChargerStatisticDTO> getTargetChargerStatistic(Integer chargerId, Boolean descYn) {
        List<ChargerStatisticDTO> dtoList;
        if (descYn) {
            dtoList = Converter.EntityListToDtoList(this.chargerStatisticRepository.findAllByChargerIdOrderByDesc(chargerId), ChargerStatisticEntity::toDTO);
        }
        else {
            dtoList = Converter.EntityListToDtoList(this.chargerStatisticRepository.findAllByChargerId(chargerId), ChargerStatisticEntity::toDTO);
        }
        return dtoList;
    }

    // 특정 충전기의 이용 시작 추가
    @Transactional
    public ChargerStatisticDTO startChargerUsing(ChargerDTO chargerDTO) {
        ChargerStatisticEntity chargerStatisticEntity = ChargerStatisticEntity.builder()
                .charger(chargerDTO.toEntity()).build();
        return this.chargerStatisticRepository.save(chargerStatisticEntity).toDTO();
    }

    // 특정 충전기의 이용 종료 추가
    @Transactional
    public ChargerStatisticDTO finishChargerUsing(ChargerDTO chargerDTO) {
        Optional<ChargerStatisticEntity> _result = this.chargerStatisticRepository.findTop1ByOrderByIdDesc();
        if (_result.isPresent()) {
            ChargerStatisticEntity chargerStatisticEntity = _result.get();
            if (!chargerStatisticEntity.getFinishedYn()) {
                long differenceSecond = ChronoUnit.SECONDS.between(chargerStatisticEntity.getStartedAt(), LocalDateTime.now());
                chargerStatisticEntity.setFinishedYn(true, (int) differenceSecond);

                return this.chargerStatisticRepository.findById(chargerStatisticEntity.getId()).get().toDTO();
            }
            else {
                log.warn("이미 사용 종료된 충전기의 사용을 종료함");
            }
        }
        else {
            log.warn("사용 내역이 없는 충전기의 사용을 종료함");
        }
        return null;
    }

}
