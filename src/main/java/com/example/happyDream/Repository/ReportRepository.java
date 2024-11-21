package com.example.happyDream.Repository;

import com.example.happyDream.Entity.ReportEntity;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<ReportEntity, Integer> {
    List<ReportEntity> findAll(Specification<ReportEntity> spec);
}
