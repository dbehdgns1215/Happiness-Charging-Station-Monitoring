package com.example.happyDream.Controller;

import com.example.happyDream.DTO.ChargerDTO;
import com.example.happyDream.Entity.ChargerEntity;
import com.example.happyDream.Service.ChargerServiceFacade;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        List<ChargerDTO> chargers = this.chargerServiceFacade.chargerSelectAll();
        model.addAttribute("chargers", chargers);
        return "chargers";
    }

    // TODO 추후 chargers에 병합 예정
    @GetMapping("/chargersTest")
    public String chargerSelectAllTest(Model model) {
        List<ChargerDTO> chargers = this.chargerServiceFacade.chargerSelectAll();
        model.addAttribute("chargers", chargers);
        return "chargersTest";
    }

    //충전기 주소 조회
    @GetMapping("/chargers/address/{address}")
    public String chargerSelect(Model model, @PathVariable("address") String address) {
        List<ChargerDTO> chargers = this.chargerServiceFacade.chargerSelectByAddress(address);
        model.addAttribute("chargers", chargers);
        return "chargers";
    }
    @GetMapping("/api/chargers")
    @ResponseBody // 이 어노테이션은 이 메서드가 JSON 형식으로 응답함을 의미
    public List<ChargerDTO> getChargersApi() {
        return this.chargerServiceFacade.chargerSelectAll();
    }

    //주변 충전기 조회
    @GetMapping("/chargers/near/{latitude}/{longitude}")
    public String chargerSelectNear(Model model, @PathVariable("latitude") Double latitude, @PathVariable("longitude") Double longitude) {
        List<ChargerDTO> chargers = this.chargerServiceFacade.chagerSelectNear(latitude, longitude);
        model.addAttribute("chargers", chargers);
        return "chargers";
    }

    //전체 충전기 삭제
    @DeleteMapping("/chargers")
    public String chargerDeleteAll() {
        this.chargerServiceFacade.chargerDeleteAll();
        return "chargers";
    }

    //충전기 추가
    /*
    @PostMapping("/chargers")
    public String chargerInsert(@RequestParam(value="")) {

        return " ";
    }
    */

    //특정 충전기 조회
    @GetMapping("/chargers/id/{id}")
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
