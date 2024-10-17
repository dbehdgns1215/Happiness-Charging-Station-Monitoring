package com.example.happyDream.RestController;

import com.example.happyDream.DTO.ChargerDTO;
import com.example.happyDream.DTO.ResponseDTO;
import com.example.happyDream.Service.ChargerServiceFacade;
import com.example.happyDream.Util.LocalDateAdapter;
import com.example.happyDream.Util.LocalDateTimeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Slf4j
@RestController //ResponseBody + Controller 어노테이션
@RequestMapping(path = "/api/v1", produces = "application/json")
@Tag(name = "Charger", description = "충전기 정보 API")
public class ChargerRestController {
    private final ChargerServiceFacade chargerServiceFacade;

    @Autowired
    public ChargerRestController(ChargerServiceFacade chargerServiceFacade) {
        this.chargerServiceFacade = chargerServiceFacade;
    }

    @GetMapping("/chargers")
    public String getAllChargers() {
        List<ChargerDTO> chargerDTOList = this.chargerServiceFacade.chargerSelectAll();
        ResponseDTO responseDto = ResponseDTO.builder()
                .apiVersion("v1")
                .status("success")
                .responseCode(HttpServletResponse.SC_OK)
                .message("success")
                .count(chargerDTOList.size())
                .data(Collections.singletonList(chargerDTOList))
                .build();

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();
        return gson.toJson(responseDto);
    }
}
