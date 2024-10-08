package com.example.happyDream.Controller;

import com.example.happyDream.DTO.ChargerDTO;
import com.example.happyDream.Service.ChargerServiceFacade;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public List<ChargerDTO> getChargersApi() {
        return this.chargerServiceFacade.chargerSelectAll();
    }


}



