package com.example.happyDream.Repository;

import com.example.happyDream.Entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Integer> {
}
