package com.example.happyDream.Controller;

import com.example.happyDream.DTO.ChargerDTO;
import com.example.happyDream.DTO.ChargerLogDTO;
import com.example.happyDream.Service.ChargerLogService;
import com.example.happyDream.Service.ChargerServiceFacade;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
public class ChargerLogController {
    private final ChargerServiceFacade chargerServiceFacade;

    @Autowired
    public ChargerLogController(ChargerServiceFacade chargerServiceFacade, ChargerLogService chargerLogService) {
        this.chargerServiceFacade = chargerServiceFacade;
    }

    //전체 충전 로그 조회
    @GetMapping("/chargers/logs")
    public String getAllChargerLog() {
        List<ChargerLogDTO> chargerLogDtoList = this.chargerServiceFacade.getAllChargerLog();
        return "";
    }

    //전체 충전 로그 삭제
    //TODO - 권한 검증 추가
    @DeleteMapping("/chargers/logs")
    public String deleteAllChargerLog() {
        this.chargerServiceFacade.deleteAllChargerLog();
        return "";
    }

    //특정 충전기의 전체 충전 로그 조회
    @GetMapping("/chargers/logs/{charger_id}")
    public String getChargerLog(@PathVariable("charger_id") Integer charger_id) {
        ChargerDTO chargerDto = chargerServiceFacade.chargerSelect(charger_id);
        List<ChargerLogDTO> chargerLogDtoList = chargerServiceFacade.getAllTargetChargerLog(chargerDto);
        return "";
    }

    //특정 충전기 충전 로그 추가
    @PostMapping("/chargers/logs/{charger_id}")
    public String createChargerLog(@PathVariable("charger_id") Integer charger_id) {
        try {
            ChargerDTO chargerDto = chargerServiceFacade.chargerSelect(charger_id);
            /*
            ChargerLogDto chargerLogDto = ChargerLogDto.builder()
                    .chargerId(chargerDto.toEntity())
                    .ampere(charger_id)
            chargerServiceFacade.createTargetChargerLog(chargerLogDto);
             */
        } catch (EntityNotFoundException ignored) {
            log.warn("존재하지 않는 충전기 id: {}", charger_id);
        }

        return "";
    }

    //충전기 하드웨어 설정값 조회
    @GetMapping("/chargers/logs/upgrade")
    public String getChargerLogSetting() {
        return "";
    }

    //충전기 하드웨어 설정값 변경
    @PostMapping("/chargers/logs/upgrade")
    public String createChargerLogSetting() {
        return "";
    }

}
