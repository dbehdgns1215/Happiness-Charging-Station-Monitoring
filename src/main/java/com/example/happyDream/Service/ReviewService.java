package com.example.happyDream.Service;

import com.example.happyDream.DTO.ReviewDTO;
import com.example.happyDream.Entity.ChargerEntity;
import com.example.happyDream.Entity.ReviewEntity;
import com.example.happyDream.Entity.UserEntity;
import com.example.happyDream.Repository.ReviewRepository;
import com.example.happyDream.Util.Converter;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    public void reviewUpdate(Integer chargerId,
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

    public void reviewDelete(Integer id) {
        this.reviewRepository.deleteById(id);
    }

    private ReviewDTO createReviewDto(Integer chargerId,
                                      Integer userId,
                                      String content,
                                      Byte rating) {
        ReviewDTO reviewDTO = new ReviewDTO();
        ChargerEntity chargerEntity = chargerService.chargerSelect(chargerId).toEntity();
        UserEntity userEntity = userService.userSelect(userId).toEntity();
        reviewDTO.setChargerId(chargerEntity);
        reviewDTO.setUserId(userEntity);
        reviewDTO.setReviewContent(content);
        reviewDTO.setCreatedAt(LocalDateTime.now());
        reviewDTO.setModifiedAt(LocalDateTime.now());
        reviewDTO.setRating(rating);
        return reviewDTO;
    }
}
