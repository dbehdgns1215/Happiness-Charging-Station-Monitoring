package com.example.happyDream.Controller;

import com.example.happyDream.DTO.ChargerDTO;
import com.example.happyDream.Service.ChargerServiceFacade;
import groovy.util.logging.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Slf4j
@Controller
public class DashboardController {
    private final ChargerServiceFacade chargerServiceFacade;

    @Autowired
    public DashboardController(ChargerServiceFacade chargerServiceFacade) {
        this.chargerServiceFacade = chargerServiceFacade;
    }
    
    @GetMapping("/")
    public String index(Model model) {
        List<ChargerDTO> chargers = this.chargerServiceFacade.chargerSelectAll();
        List<ChargerDTO> usingChargers = this.chargerServiceFacade.chargerSelectByUsingYn(true);
        List<ChargerDTO> idelChargers = this.chargerServiceFacade.chargerSelectByUsingYn(false);
        List<ChargerDTO> brokenChargers = this.chargerServiceFacade.chargerSelectByBrokenYn(true);
        model.addAttribute("chargerCount", chargers.size());
        model.addAttribute("usingChargerCount", usingChargers.size());
        model.addAttribute("idleChargerCount", idelChargers.size());
        model.addAttribute("brokenChargerCount", brokenChargers.size());

        return "dashboard";
    }
}
