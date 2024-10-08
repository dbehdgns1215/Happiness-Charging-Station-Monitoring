package com.example.happyDream.Controller;

import com.example.happyDream.DTO.ChargerDto;
import com.example.happyDream.Entity.ChargerEntity;
import com.example.happyDream.Service.ChargerServiceFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ChargerController {
    private final ChargerServiceFacade chargerServiceFacade;

    @Autowired
    public ChargerController(ChargerServiceFacade chargerServiceFacade) {
        this.chargerServiceFacade = chargerServiceFacade;
    }

    //전체 충전기 조회
    @GetMapping("/chargers")
    public String chargerSelectAll() {
        List<ChargerDto> chargers = this.chargerServiceFacade.chargerSelectAll();
        return " ";
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
        ChargerDto charger = this.chargerServiceFacade.chargerSelect(id);
        return " ";
    }

    //특정 충전기 삭제
    @GetMapping("/chargers/{id}")
    public String chargerDelete(@PathVariable("id") Integer id) {
        this.chargerServiceFacade.chargerDelete(id);
        return " ";
    }
}
