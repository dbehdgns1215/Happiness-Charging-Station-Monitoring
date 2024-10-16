package com.example.happyDream.RestController;

import com.example.happyDream.DTO.ChargerDTO;
import com.example.happyDream.DTO.ChargerStateDTO;
import com.example.happyDream.Service.ChargerServiceFacade;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class ChargerStateRestController {
    private final ChargerServiceFacade chargerServiceFacade;

    @Autowired
    public ChargerStateRestController(ChargerServiceFacade chargerServiceFacade) {
        this.chargerServiceFacade = chargerServiceFacade;
    }

    // 전체 충전기 상태 조회
    @GetMapping("/chargers/states")
    public String getAllChargerState() {
        List<ChargerStateDTO> dtoList = this.chargerServiceFacade.getAllChargerState(); // 빈 리스트 반환 주의
        return "";
    }

    // 특정 충전기 상태 조회
    @GetMapping("/chargers/{id}/states")
    public String getTargetChargerState(@PathVariable("id") Integer chargerId) {
        ChargerDTO chargerDto = ChargerDTO.builder().id(chargerId).build();
        // TODO - 예외 처리 및 그에 따른 적절한 반환 처리 필요
        try {
            ChargerStateDTO chargerStateDto = this.chargerServiceFacade.getTargetChargerState(chargerDto); // 예외 반환 주의
        } catch (EntityNotFoundException ignored) {}
        return "";
    }

    // 특정 충전기 상태 변경
    @PutMapping("/chargers/{id}/states")
    public String changeTargetChargerState(@PathVariable("id") Integer chargerId, Boolean usingUn, Boolean brokenYn) {
        ChargerDTO chargerDto = ChargerDTO.builder().id(chargerId).build();
        ChargerStateDTO chargerStateDTO = ChargerStateDTO.builder()
                .chargerId(chargerDto.toEntity())
                .usingYn(usingUn)
                .brokenYn(brokenYn)
                .build();
        this.chargerServiceFacade.changeTargetChargerState(chargerStateDTO); // void
        return "";
    }

}