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
}
