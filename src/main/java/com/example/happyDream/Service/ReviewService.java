package com.example.happyDream.Service;

import com.example.happyDream.DTO.ReviewDTO;
import com.example.happyDream.Entity.ReviewEntity;
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
    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
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
                             Byte rating,
                             Boolean deletedYn) {
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setChargerId(chargerId);
        reviewDTO.setUserId(userId);
        reviewDTO.setReviewContent(content);
        reviewDTO.setCreatedAt(LocalDateTime.now());
        reviewDTO.setModifiedAt(LocalDateTime.now());
        reviewDTO.setRating(rating);
        reviewDTO.setDeletedYn(deletedYn);
        this.reviewRepository.save(reviewDTO.toEntity());
    }

    public void reviewUpdate(Integer chargerId,
                             Integer userId,
                             String content,
                             Byte rating,
                             Boolean deletedYn) {
        ReviewDTO reviewDTO = new ReviewDTO();
        ReviewEntity reviewEntity = charger
        reviewDTO.setChargerId(chargerId);
        reviewDTO.setUserId(userId);
        reviewDTO.setReviewContent(content);
        reviewDTO.setCreatedAt(LocalDateTime.now());
        reviewDTO.setModifiedAt(LocalDateTime.now());
        reviewDTO.setRating(rating);
        reviewDTO.setDeletedYn(deletedYn);
        this.reviewRepository.save(reviewDTO.toEntity());
    }
}
