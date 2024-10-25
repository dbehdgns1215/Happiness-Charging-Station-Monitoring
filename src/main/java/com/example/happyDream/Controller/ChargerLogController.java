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

@Slf4j
@Controller
public class ChargerLogController {
    private final ChargerServiceFacade chargerServiceFacade;

    @Autowired
    public ChargerLogController(ChargerServiceFacade chargerServiceFacade, ChargerLogService chargerLogService) {
        this.chargerServiceFacade = chargerServiceFacade;
    }

    // 전체 충전기 로그 조회
    @GetMapping("/chargers/logs")
    public String getAllChargerLog() {
        return "";
    }
    
    // 특정 충전기 로그 조회
    @GetMapping("/chargers/{id}/logs")
    public String getChargerLog(@PathVariable("id") Integer chargerId) {
        return "";
    }

    // TODO - 파라미터 웹에 맞게 수정 예정
    @PostMapping("/chargers/{id}/logs")
    public String createChargerLog(@PathVariable("id") Integer chargerId, Float ampere) {
        try {
            ChargerDTO chargerDto = chargerServiceFacade.chargerSelect(chargerId);

            ChargerLogDTO chargerLogDto = ChargerLogDTO.builder()
                    .chargerId(chargerDto.getId())
                    .ampere(ampere)
                    .build();

            chargerServiceFacade.createTargetChargerLog(chargerLogDto);

        } catch (EntityNotFoundException ignored) {
            log.warn("존재하지 않는 충전기 id: {}", chargerId);
        }

        return "";
    }
}