package com.example.happyDream.Controller;

import com.example.happyDream.Service.ReportService;
import com.example.happyDream.Service.ReviewService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReportController {
    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/reports")
    public String reviewSelectAll(Model model) {
        Map<String, List<String>> uniqueCities = reportService.reportAddress();
        model.addAttribute("cities", uniqueCities);
        return "reports";
    }
}
