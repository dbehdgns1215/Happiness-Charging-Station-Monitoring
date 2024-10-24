package com.example.happyDream.RestController;

import com.example.happyDream.DTO.ResponseDTO;
import com.example.happyDream.DTO.ReviewDTO;
import com.example.happyDream.Entity.ChargerEntity;
import com.example.happyDream.Repository.ChargerRepository;
import com.example.happyDream.Service.ReviewService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewRestController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewRestController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // TODO - Review 전체 예외 처리
    // 전체 리뷰 조회
    @GetMapping("/reviews")
    public ResponseDTO reviewSelectAll(Model model) {
        List<ReviewDTO> reviews = this.reviewService.reviewSelectAll();
        return ResponseDTO.success("v1", HttpServletResponse.SC_OK, reviews);
    }

    // 리뷰 추가
    @PostMapping("/reviews")
    public ResponseDTO reviewInsert(@RequestParam(value="charger_id") Integer chargerId,
                               @RequestParam(value="user_id") Integer userId,
                               @RequestParam(value="review_content") String content,
                               @RequestParam(value="rating") Byte rating) {
        this.reviewService.reviewInsert(chargerId, userId, content, rating);
        return ResponseDTO.success("v1", HttpServletResponse.SC_OK);
    }

    // 특정 리뷰 수정
    @PostMapping("/reviews/{id}")
    public ResponseDTO reviewUpdate(@RequestParam(value="charger_id") Integer chargerId,
                               @RequestParam(value="user_id") Integer userId,
                               @RequestParam(value="review_content") String content,
                               @RequestParam(value="rating") Byte rating) {
        this.reviewService.reviewUpdate(chargerId, userId, content, rating);
        return ResponseDTO.success("v1", HttpServletResponse.SC_OK);
    }

    // 전체 리뷰 삭제
    @DeleteMapping("/reviews")
    public ResponseDTO reviewDeleteAll() {
        this.reviewService.reviewDeleteAll();
        return ResponseDTO.success("v1", HttpServletResponse.SC_OK);
    }

    // 특정 리뷰 조회
    @GetMapping("/reviews/{id}")
    public ResponseDTO reviewSelect(@PathVariable("id") Integer id) {
        ReviewDTO review = this.reviewService.reviewSelect(id);
        return ResponseDTO.success("v1", HttpServletResponse.SC_OK, Collections.singletonList(review));
    }

    // 특정 리뷰 삭제
    @DeleteMapping("/reviews/{id}")
    public ResponseDTO reviewDelete(@PathVariable("id") Integer id) {
        this.reviewService.reviewDelete(id);
        return ResponseDTO.success("v1", HttpServletResponse.SC_OK);
    }
}
