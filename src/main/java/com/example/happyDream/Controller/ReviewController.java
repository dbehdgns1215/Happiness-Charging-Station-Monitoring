package com.example.happyDream.Controller;

import com.example.happyDream.DTO.ChargerDTO;
import com.example.happyDream.DTO.ReviewDTO;
import com.example.happyDream.DTO.UserDTO;
import com.example.happyDream.Service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class ReviewController {
    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/reviews")
    public String reviewSelectAll(Model model) {
        List<Object[]> cities = reviewService.reviewAddress();
        Map<String, List<String>> uniqueCities = cities.stream()
                .filter(city -> city.length > 1 && city[0] instanceof String && city[1] instanceof String)
                .collect(Collectors.toMap(
                        city -> (String) city[0],
                        city -> new ArrayList<>(List.of((String) city[1])), // 값을 List로 저장
                        (existing, replacement) -> { // 중복된 key가 있을 때, 기존 리스트에 추가
                            existing.addAll(replacement);
                            return existing;
                        }
                )); // 첫 번째 요소를 String으로 변환
        model.addAttribute("cities", uniqueCities);
        return "reviews";
    }
}
