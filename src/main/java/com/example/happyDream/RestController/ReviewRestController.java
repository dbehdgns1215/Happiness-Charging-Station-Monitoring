package com.example.happyDream.RestController;

import com.example.happyDream.DTO.ReviewDTO;
import com.example.happyDream.Entity.ChargerEntity;
import com.example.happyDream.Repository.ChargerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reviews")
public class ReviewRestController {

    private final ChargerRepository chargerRepository;

    public ReviewRestController(ChargerRepository chargerRepository) {
        this.chargerRepository = chargerRepository;
    }

    @PostMapping("/{chargerId}/{userId}")
    public ResponseEntity<String> addReview(
            @PathVariable Integer chargerId,
            @PathVariable Integer userId,
            @RequestBody ReviewDTO reviewDTO) {

        // ChargerEntity를 ID로 조회
        ChargerEntity charger = chargerRepository.findById(chargerId)
                .orElseThrow(() -> new RuntimeException("Charger not found"));

        // TODO - 나중에 User 관련 기능 개발 후 아래의 유저 조회 코드 추가 가능
        /*
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        */

        // 로그로 받은 리뷰 정보 출력
        System.out.println("Received Review:");
        System.out.println("ChargerId: " + chargerId);
        System.out.println("User ID: " + userId);
        System.out.println("Content: " + reviewDTO.getReviewContent());
        System.out.println("Rating: " + reviewDTO.getRating());
        // TODO - 앱에서는 userID와 chargerID가 int로 넘어올 수 밖에 없는 구조이기 때문에
        // TODO - chargerID와 userID를 기준으로 review 테이블에 저장해야 함

        // TODO - 특정 충전소의 리뷰 조회 -> chargerID로 조회
        // TODO - 특정 유저의 리뷰 조회 -> userID로 조회


        return ResponseEntity.ok("Review added successfully");
    }
}
