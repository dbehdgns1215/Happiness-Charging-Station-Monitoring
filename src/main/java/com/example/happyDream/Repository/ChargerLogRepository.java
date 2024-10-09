package com.example.happyDream.Repository;

import com.example.happyDream.Entity.ChargerEntity;
import com.example.happyDream.Entity.ChargerLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChargerLogRepository extends JpaRepository<ChargerLogEntity, Long> {
    public List<ChargerLogEntity> findAllByChargerId(ChargerEntity chargerEntity);
}
