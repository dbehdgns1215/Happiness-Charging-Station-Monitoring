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

    //전체 리뷰 삭제
    @DeleteMapping("/users")
    public String reviewDeleteAll() {
        this.reviewService.reviewDeleteAll();
        return " ";
    }

    //리뷰 추가
    @PostMapping("/users")
    public String reviewInsert(@RequestParam(value="charger_id") Integer chargerId,
                             @RequestParam(value="user_id") Integer userId,
                             @RequestParam(value="review_content") String email,
                             @RequestParam(value="rating") Byte userType,
                             @RequestParam(value="deleted_yn") Boolean deletedYn) {
        this.reviewService.reviewInsert(chargerId, userId, email, userType, deletedYn);
        return " ";
    }
}
