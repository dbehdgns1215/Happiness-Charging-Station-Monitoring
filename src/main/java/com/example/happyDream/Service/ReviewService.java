package com.example.happyDream.Service;

import com.example.happyDream.DTO.ReviewDTO;
import com.example.happyDream.Entity.ChargerEntity;
import com.example.happyDream.Entity.ReviewEntity;
import com.example.happyDream.Entity.UserEntity;
import com.example.happyDream.Repository.ReviewRepository;
import com.example.happyDream.Util.Converter;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
public class ReviewService {
    private final UserService userService;
    private final ChargerService chargerService;
    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(UserService userService, ChargerService chargerService, ReviewRepository reviewRepository) {
        this.userService = userService;
        this.chargerService = chargerService;
        this.reviewRepository = reviewRepository;
    }

    public List<ReviewDTO> reviewSelectAll() {
        List<ReviewEntity> entityList = this.reviewRepository.findAll();
        return Converter.EntityListToDtoList(entityList, ReviewEntity::toDTO);
    }

    public void reviewDeleteAll() {
        this.reviewRepository.deleteAll();
    }

    public void reviewInsert(Integer chargerId,
                             Integer userId,
                             String content,
                             Byte rating) {
        ReviewDTO reviewDTO = createReviewDto(chargerId, userId, content, rating);
        this.reviewRepository.save(reviewDTO.toEntity());
    }

    public ReviewDTO reviewSelect(Integer id) {
        Optional<ReviewEntity> entity = this.reviewRepository.findById(id);
        if(entity.isEmpty()){
            throw new EntityNotFoundException();
        }
        return entity.get().toDTO();

    }


    @Transactional
    public void reviewUpdate(Integer chargerId, ReviewDTO reviewDTO) {
        ReviewEntity reviewEntity = reviewRepository.findByChargerIdAndUserId(
                chargerId, reviewDTO.getUserId()
        ).orElseThrow(() -> new IllegalArgumentException("해당 리뷰가 존재하지 않습니다."));
        /*
        reviewEntity.setReviewContent(reviewDTO.getReviewContent());
        reviewEntity.setRating(reviewDTO.getRating());
        reviewEntity.setModifiedAt(LocalDateTime.now());
         */
        // 엔티티에 Setter 미사용시 수정 로직을 어떻게 처리할 것인지 논의 필요
    }

    public void reviewDelete(Integer id) {
        this.reviewRepository.deleteById(id);
    }

    public List<Object[]> reviewAddress() {
        return this.chargerService.selectChargerAddress();
    }

    private ReviewDTO createReviewDto(Integer chargerId,
                                      Integer userId,
                                      String content,
                                      Byte rating) {
        ChargerEntity chargerEntity = chargerService.chargerSelect(chargerId).toEntity();
        UserEntity userEntity = userService.userSelect(userId).toEntity();

        return ReviewDTO.builder()
                .chargerId(chargerEntity.getId())
                .userId(userEntity.getId())
                .reviewContent(content)
                .rating(rating)
                .build();
    }
}
