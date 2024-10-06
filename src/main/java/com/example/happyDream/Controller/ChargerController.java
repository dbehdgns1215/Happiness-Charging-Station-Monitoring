package com.example.happyDream.Controller;

import com.example.happyDream.Service.ChargerServiceFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ChargerController {
    private final ChargerServiceFacade chargerServiceFacade;

    @Autowired
    public ChargerController(ChargerServiceFacade chargerServiceFacade) {
        this.chargerServiceFacade = chargerServiceFacade;
    }
}
