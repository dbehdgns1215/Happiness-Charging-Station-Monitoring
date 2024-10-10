package com.example.happyDream.Controller;

import com.example.happyDream.Service.ChargerServiceFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class ChargerStateController {
    private final ChargerServiceFacade chargerServiceFacade;

    @Autowired
    public ChargerStateController(ChargerServiceFacade chargerServiceFacade) {
        this.chargerServiceFacade = chargerServiceFacade;
    }

//    @GetMapping("/api/v1/chargers/states")
//    @PutMapping("/api/v1/chargers/states")
//    @PostMapping("/api/v1/chargers/states/{id}") // chargerId
//    @GetMapping("/api/v1/chargers/states/{id}") // chargerId
//    @PutMapping("/api/v1/chargers/states/{id}") // chargerId
}