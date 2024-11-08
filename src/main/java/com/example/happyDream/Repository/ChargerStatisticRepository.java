package com.example.happyDream.Repository;

import com.example.happyDream.Entity.ChargerStateEntity;
import com.example.happyDream.Entity.ChargerStatisticEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChargerStatisticRepository extends JpaRepository<ChargerStatisticEntity, Long> {
}
