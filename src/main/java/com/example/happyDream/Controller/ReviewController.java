package com.example.happyDream.Controller;

import com.example.happyDream.DTO.ReviewDTO;
import com.example.happyDream.DTO.UserDTO;
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
    @GetMapping("/reviews")
    public String reviewSelectAll(Model model) {
        List<ReviewDTO> reviews = this.reviewService.reviewSelectAll();
        return " ";
    }

    //전체 리뷰 삭제
    @DeleteMapping("/reviews")
    public String reviewDeleteAll() {
        this.reviewService.reviewDeleteAll();
        return " ";
    }

    //리뷰 추가
    @PostMapping("/reviews")
    public String reviewInsert(@RequestParam(value="charger_id") Integer chargerId,
                               @RequestParam(value="user_id") Integer userId,
                               @RequestParam(value="review_content") String content,
                               @RequestParam(value="rating") Byte rating,
                               @RequestParam(value="deleted_yn") Boolean deletedYn) {
        this.reviewService.reviewInsert(chargerId, userId, content, rating, deletedYn);
        return " ";
    }

    //특정 리뷰 수정
    @PostMapping("/reviews/{id}")
    public String reviewUpdate(@RequestParam(value="charger_id") Integer chargerId,
                               @RequestParam(value="user_id") Integer userId,
                               @RequestParam(value="review_content") String content,
                               @RequestParam(value="rating") Byte rating,
                               @RequestParam(value="deleted_yn") Boolean deletedYn) {
        this.reviewService.reviewUpdate(chargerId, userId, content, rating, deletedYn);
        return " ";
    }

    //특정 리뷰 조회
    @GetMapping("/reviews/{id}")
    public String reviewSelect(@PathVariable("id") Integer id) {
        ReviewDTO review = this.reviewService.reviewSelect(id);
        return " ";
    }

    //특정 사용자 삭제
    @DeleteMapping("/reviews/{id}")
    public String reviewDelete(@PathVariable("id") Integer id) {
        this.reviewService.reviewDelete(id);
        return " ";
    }
}
