package com.example.happyDream.RestController;

import com.example.happyDream.DTO.ChargerDTO;
import com.example.happyDream.DTO.ChargerStateDTO;
import com.example.happyDream.DTO.ResponseDTO;
import com.example.happyDream.Interface.ChargerStateSwagger;
import com.example.happyDream.Service.ChargerServiceFacade;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1", produces = "application/json")
public class ChargerStateRestController implements ChargerStateSwagger {
    private final ChargerServiceFacade chargerServiceFacade;

    @Autowired
    public ChargerStateRestController(ChargerServiceFacade chargerServiceFacade) {
        this.chargerServiceFacade = chargerServiceFacade;
    }

    // 전체 충전기 상태 조회
    @GetMapping("/chargers/states")
    public ResponseDTO getAllChargerState() {
        ResponseDTO responseDto;

        List<ChargerStateDTO> chargerStateDtoList = this.chargerServiceFacade.getAllChargerState();
        if (chargerStateDtoList.isEmpty()) {
            responseDto = ResponseDTO.success("v1", HttpServletResponse.SC_NO_CONTENT, Collections.unmodifiableList(chargerStateDtoList));
        }
        else {
            responseDto = ResponseDTO.success("v1", HttpServletResponse.SC_OK, Collections.unmodifiableList(chargerStateDtoList));
        }

        return responseDto;
    }

    // 특정 충전기 상태 조회
    @GetMapping("/chargers/{id}/states")
    public ResponseDTO getTargetChargerState(@PathVariable("id") Integer chargerId) {
        ChargerDTO chargerDto = ChargerDTO.builder().id(chargerId).build();

        ResponseDTO responseDto;
        try {
            ChargerStateDTO chargerStateDto = this.chargerServiceFacade.getTargetChargerState(chargerDto);
            responseDto = ResponseDTO.success("v1", HttpServletResponse.SC_OK, (List<?>) chargerStateDto);
        } catch (EntityNotFoundException e) {
            log.error("ChargerState가 존재하지 않음 - 요청한 충전기 id: {}\n{}", chargerId, e.getStackTrace());
            responseDto = ResponseDTO.error("v1", HttpServletResponse.SC_NOT_FOUND, "ChargerState가 존재하지 않음");
        } catch (Exception e) {
            log.error("알 수 없는 오류 - 요청한 충전기 id: {}\n{}", chargerId, e.getStackTrace());
            responseDto = ResponseDTO.error("v1", HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "알 수 없는 오류");
        }
        
        return responseDto;
    }

    // 특정 충전기 상태 변경
    @PutMapping("/chargers/{id}/states")
    public ResponseDTO changeTargetChargerState(@PathVariable("id") Integer chargerId, Boolean usingUn, Boolean brokenYn) {
        ChargerDTO chargerDto = ChargerDTO.builder().id(chargerId).build();
        ChargerStateDTO chargerStateDTO = ChargerStateDTO.builder()
                .chargerId(chargerDto.toEntity())
                .usingYn(usingUn)
                .brokenYn(brokenYn)
                .build();
        this.chargerServiceFacade.changeTargetChargerState(chargerStateDTO); // TODO - 상태 반환
        return ResponseDTO.error("v1", HttpServletResponse.SC_NOT_IMPLEMENTED, "미구현");
    }

}