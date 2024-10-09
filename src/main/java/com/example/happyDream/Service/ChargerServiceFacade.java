package com.example.happyDream.Service;

import com.example.happyDream.DTO.ChargerDto;
import com.example.happyDream.DTO.ChargerLogDto;
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
    public List<ChargerDto> chargerSelectAll(){
        return this.chargerService.chargerSelectAll();
    }

    // 전체 충전기 삭제
    public void chargerDeleteAll(){
        this.chargerService.chargerDeleteAll();
    }

    // 특정 충전기 조회
    public ChargerDto chargerSelect(Integer id){
        return this.chargerService.chargerSelect(id);
    }

    // 특정 충전기 삭제
    public void chargerDelete(Integer id){
        this.chargerService.chargerDelete(id);
    }

    // 전체 충전 로그 조회
    public List<ChargerLogDto> getAllChargerLog() {
        return this.chargerLogService.getAllChargerLog();
    }

    /* ===== ChargerLogService ===== */

    // 전체 충전 로그 삭제
    public void deleteAllChargerLog() {
        this.chargerLogService.deleteAllChargerLog();
    }

    // 특정 충전기의 전체 충전 로그 조회
    public List<ChargerLogDto> getAllTargetChargerLog(ChargerDto ChargerDto) {
        return this.chargerLogService.getAllTargetChargerLog(ChargerDto);
    }

    // 특정 충전기 충전 로그 추가
    public void createTargetChargerLog(ChargerLogDto chargerLogDto) {
        this.chargerLogService.createTargetChargerLog(chargerLogDto);
    }

    /* ===== ChargerStateService ===== */

}
