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
        List<ChargerDTO> idleChargers = this.chargerServiceFacade.chargerSelectByUsingYn(false);
        List<ChargerDTO> brokenChargers = this.chargerServiceFacade.chargerSelectByBrokenYn(true);
        List<ChargerDTO> notRequestRecentlyCharger = this.chargerServiceFacade.chargerSelectWithoutLogInPeriod(30);
        model.addAttribute("totalChargerCount", chargers.size());
        model.addAttribute("usingChargerCount", usingChargers.size());
        model.addAttribute("idleChargerCount", idleChargers.size());
        model.addAttribute("brokenChargerCount", brokenChargers.size());
        model.addAttribute("inProgressReportCount", 2); // TODO - 추가 예쩡
        model.addAttribute("completedReportCount", 9); // TODO - 추가 예쩡
        model.addAttribute("notRequestRecentlyChargerCount", notRequestRecentlyCharger.size());
        model.addAttribute("notUsingTodayCharger", 0);

        return "dashboard_new";
    }
}
