package com.example.happyDream.Controller;

import com.example.happyDream.DTO.ChargerDTO;
import com.example.happyDream.DTO.ReportDTO;
import com.example.happyDream.DTO.ReviewDTO;
import com.example.happyDream.Service.ChargerServiceFacade;
import com.example.happyDream.Service.ReportService;
import com.example.happyDream.Service.ReviewService;
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
    private final ReviewService reviewService;
    private final ReportService reportService;

    @Autowired
    public DashboardController(ChargerServiceFacade chargerServiceFacade, ReviewService reviewService, ReportService reportService) {
        this.chargerServiceFacade = chargerServiceFacade;
        this.reviewService = reviewService;
        this.reportService = reportService;
    }
    
    @GetMapping("/")
    public String index(Model model) {
        List<ChargerDTO> chargers = this.chargerServiceFacade.chargerSelectAll();
        List<ChargerDTO> usingChargers = this.chargerServiceFacade.chargerSelectByUsingYn(true);
        List<ChargerDTO> idleChargers = this.chargerServiceFacade.chargerSelectByUsingYn(false);
        List<ChargerDTO> brokenChargers = this.chargerServiceFacade.chargerSelectByBrokenYn(true);
        List<ChargerDTO> notRequestRecentlyCharger = this.chargerServiceFacade.chargerSelectWithoutLogInPeriod(30);
        List<ReviewDTO> reviews = reviewService.reviewSelectAll();
        List<ReportDTO> reportsChecked = reportService.reportSelectByCheckedReport();
        List<ReportDTO> reportsRepaired = reportService.reportSelectByCheckedRepair();
        model.addAttribute("totalChargerCount", chargers.size());
        model.addAttribute("usingChargerCount", usingChargers.size());
        model.addAttribute("idleChargerCount", idleChargers.size());
        model.addAttribute("brokenChargerCount", brokenChargers.size());
        model.addAttribute("inProgressReportCount", reportsChecked.size()); // TODO - 단순히 고장 신고 들어온 것도 추가해야할듯
        model.addAttribute("completedReportCount", reportsRepaired.size());
        model.addAttribute("notRequestRecentlyChargerCount", notRequestRecentlyCharger.size());
        model.addAttribute("notUsingTodayCharger", 0);
        model.addAttribute("reviewCount", reviews.size());

        return "dashboard_V2";
    }
}
