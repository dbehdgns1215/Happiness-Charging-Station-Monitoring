package com.example.happyDream.Controller;

import com.example.happyDream.DTO.ChargerDTO;
import com.example.happyDream.DTO.ChargerLogDTO;
import com.example.happyDream.DTO.ChargerStateDTO;
import com.example.happyDream.Service.ChargerLogService;
import com.example.happyDream.Service.ChargerServiceFacade;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    // 전체 충전기 로그 조회
    @GetMapping("/chargers/logs")
    public String getAllChargerLog() {
        return "";
    }
    
    // 특정 충전기 로그 조회
    @GetMapping("/chargers/{id}/logs")
    public String getChargerLog(Model model,
                                @PathVariable("id") Integer chargerId,
                                @RequestParam(value = "descYn", defaultValue = "true") Boolean descYn) {
        // TODO - 충전기가 없거나 로그가 없는 경우 웹에서 오류 처리
        ChargerStateDTO chargerStateDto;
        try {
            chargerStateDto = this.chargerServiceFacade.getTargetChargerState(chargerId);
        } catch (EntityNotFoundException e) {
            chargerStateDto = null;
        }
        List<ChargerLogDTO> chargerLogDtoList = this.chargerServiceFacade.getAllTargetChargerLog(chargerId, descYn);
        model.addAttribute("logList", chargerLogDtoList);
        model.addAttribute("chargerState", chargerStateDto);
        return "chargerLog.html";
    }
}