package com.example.happyDream.Service;

import com.example.happyDream.DTO.ChargerDTO;
import com.example.happyDream.DTO.ChargerLogDTO;
import com.example.happyDream.DTO.ChargerStateDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ChargerServiceFacade {
    private final ChargerService chargerService;
    private final ChargerStateService chargerStateService;
    private final ChargerLogService chargerLogService;

    @Autowired
    public ChargerServiceFacade(ChargerService chargerService, ChargerStateService chargerStateService, ChargerLogService chargerLogService) {
        this.chargerService = chargerService;
        this.chargerStateService = chargerStateService;
        this.chargerLogService = chargerLogService;
    }

    /* ===== ChargerService ===== */
    // 전체 충전기 조회
    public List<ChargerDTO> chargerSelectAll(){
        return this.chargerService.chargerSelectAll();
    }

    // 전체 충전기 삭제
    public void chargerDeleteAll(){
        this.chargerService.chargerDeleteAll();
    }

    // 특정 충전기 조회
    public ChargerDTO chargerSelect(Integer id){
        return this.chargerService.chargerSelect(id);
    }

    // 특정 충전기 삭제
    public void chargerDelete(Integer id){
        this.chargerService.chargerDelete(id);
    }

    /* ===== ChargerLogService ===== */

    // 전체 충전 로그 조회
    public List<ChargerLogDTO> getAllChargerLog(Boolean join) {
        return this.chargerLogService.getAllChargerLog(join);
    }

    // 전체 충전 로그 삭제
    public void deleteAllChargerLog() {
        this.chargerLogService.deleteAllChargerLog();
    }

    // 특정 충전기의 전체 충전 로그 조회
    public List<ChargerLogDTO> getAllTargetChargerLog(ChargerDTO ChargerDto) {
        return this.chargerLogService.getAllTargetChargerLog(ChargerDto);
    }

    // 특정 충전기 충전 로그 추가
    public void createTargetChargerLog(ChargerLogDTO chargerLogDto) {
        // FK(ChargerId) 존재 여부 1차 확인 후 PK(ChargerLogId) 부존재 여부 2차 확인
        try {
            this.chargerService.chargerSelect(chargerLogDto.getChargerId().getId()); // 검증용 호출, 반환값 ignored
            this.chargerLogService.createTargetChargerLog(chargerLogDto);
        } catch (EntityNotFoundException ignored) { }
    }

    /* ===== ChargerStateService ===== */

    // 전체 충전기 상태 조회
    public List<ChargerStateDTO> getAllChargerState() {
        return this.chargerStateService.getAllChargerState();
    }

    // 전체 충전기 상태 초기화
    public void initAllChargerState() {
        this.chargerStateService.initAllChargerState();
    }

    // 특정 충전기 상태 조회
    public ChargerStateDTO getTargetChargerState(ChargerDTO chargerDto) {
        try {
            return this.chargerStateService.getTargetChargerState(chargerDto);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException();
        }
    }

    // 특정 충전기 상태 추가
    public void createTargetChargerState(ChargerDTO chargerDto) {
        this.chargerStateService.createChargerState(chargerDto);
    }

    // 특정 충전기 상태 업데이트
    public void changeTargetChargerState(ChargerStateDTO chargerStateDto) {
        this.chargerStateService.changeTargetChargerState(chargerStateDto);
    }

}
