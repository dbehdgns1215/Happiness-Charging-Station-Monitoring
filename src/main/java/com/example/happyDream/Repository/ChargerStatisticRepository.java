package com.example.happyDream.Repository;

import com.example.happyDream.Entity.ChargerEntity;
import com.example.happyDream.Entity.ChargerStateEntity;
import com.example.happyDream.Entity.ChargerStatisticEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChargerStatisticRepository extends JpaRepository<ChargerStatisticEntity, Long> {
    @Query("SELECT cs FROM ChargerStatisticEntity cs WHERE cs.charger.id = :chargerId")
    List<ChargerStatisticEntity> findAllByChargerId(Integer chargerId);

    @Query("SELECT cs FROM ChargerStatisticEntity cs WHERE cs.charger.id = :chargerId ORDER BY cs.id DESC")
    List<ChargerStatisticEntity> findAllByChargerIdOrderByDesc(Integer chargerId);

    // 가장 마지막에 추가된 사용 내역 1개 반환
    Optional<ChargerStatisticEntity> findTop1ByOrderByIdDesc();
}
