package com.example.happyDream.Controller;

import com.example.happyDream.DTO.ChargerDTO;
import com.example.happyDream.DTO.ResponseDTO;
import com.example.happyDream.Entity.ChargerEntity;
import com.example.happyDream.Service.ChargerServiceFacade;
import com.example.happyDream.Util.LocalDateAdapter;
import com.example.happyDream.Util.LocalDateTimeAdapter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Controller
public class ChargerController {
    private final ChargerServiceFacade chargerServiceFacade;
    private final ObjectMapper objectMapper;

    @Autowired
    public ChargerController(ChargerServiceFacade chargerServiceFacade, ObjectMapper objectMapper) {
        this.chargerServiceFacade = chargerServiceFacade;
        this.objectMapper = objectMapper;
    }

    //전체 충전기 조회
    @GetMapping("/chargers")
    public String chargerSelectAll(Model model) {
        try {
            List<ChargerDTO> chargers = this.chargerServiceFacade.chargerSelectAll();
            String chargersJson = objectMapper.writeValueAsString(chargers);
            model.addAttribute("chargers", chargersJson); // JSON 문자열로 추가
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            model.addAttribute("chargers", "[]"); // 빈 배열 처리
        }
        return "chargers"; // chargers.html로 이동
    }
    @GetMapping("/api/chargers")
    @ResponseBody // 이 어노테이션은 이 메서드가 JSON 형식으로 응답함을 의미
    public String getChargersApi() {
        List<ChargerDTO> chargerDTOList = this.chargerServiceFacade.chargerSelectAll();
        ResponseDTO responseDto = ResponseDTO.builder()
                .apiVersion("v1")
                .status("success")
                .responseCode("200")
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

    //전체 충전기 삭제
    @DeleteMapping("/chargers")
    public String chargerDeleteAll() {
        this.chargerServiceFacade.chargerDeleteAll();
        return " ";
    }

    //충전기 추가
    /*
    @PostMapping("/chargers")
    public String chargerInsert(@RequestParam(value="")) {

        return " ";
    }
    */

    //특정 충전기 조회
    @GetMapping("/chargers/{id}")
    public String chargerSelect(@PathVariable("id") Integer id) {
        ChargerDTO charger = this.chargerServiceFacade.chargerSelect(id);
        return " ";
    }

    //특정 충전기 삭제
    @DeleteMapping("/chargers/{id}")
    public String chargerDelete(@PathVariable("id") Integer id) {
        this.chargerServiceFacade.chargerDelete(id);
        return " ";
    }
}
