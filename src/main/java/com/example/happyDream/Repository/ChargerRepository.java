package com.example.happyDream.Repository;

import com.example.happyDream.Entity.ChargerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChargerRepository extends JpaRepository<ChargerEntity, Integer> {
    @Query("SELECT c FROM ChargerEntity c WHERE c.addressOld LIKE %:address% OR c.addressNew LIKE %:address% OR c.name LIKE %:address%")
    List<ChargerEntity> findChargersByAddress(@Param("address") String address);
}
