package com.example.happyDream.Controller;

import com.example.happyDream.DTO.ChargerDto;
import com.example.happyDream.Entity.ChargerEntity;
import com.example.happyDream.Service.ChargerServiceFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ChargerController {
    private final ChargerServiceFacade chargerServiceFacade;

    @Autowired
    public ChargerController(ChargerServiceFacade chargerServiceFacade) {
        this.chargerServiceFacade = chargerServiceFacade;
    }

    @GetMapping("/chargers")
    public String chargerSelectAll() {
        List<ChargerDto> chargers = this.chargerServiceFacade.chargerSelectAll();
        return " ";
    }
}
