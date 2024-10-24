package com.example.happyDream.Controller;

import com.example.happyDream.DTO.ReviewDTO;
import com.example.happyDream.Service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ReviewController {
    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    //전체 리뷰 조회
    @GetMapping("/users")
    public String reviewSelectAll(Model model) {
        List<ReviewDTO> users = this.reviewService.reviewSelectAll();
        return " ";
    }
}
