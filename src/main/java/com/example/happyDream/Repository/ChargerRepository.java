package com.example.happyDream.Repository;

import com.example.happyDream.DTO.ChargerDetailDTO;
import com.example.happyDream.Entity.ChargerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChargerRepository extends JpaRepository<ChargerEntity, Integer> {
    @Query("SELECT c " +
            "FROM ChargerEntity c " +
            "WHERE c.addressOld LIKE %:address% OR c.addressNew LIKE %:address% OR c.name LIKE %:address%")
    List<ChargerEntity> findChargersByAddress(@Param("address") String address);

    @Query("SELECT c " +
            "FROM ChargerEntity c " +
            "WHERE (6371*acos(cos(radians(:userLatitude))*cos(radians(c.latitude))*cos(radians(c.longitude) " +
            "-radians(:userLongitude))+sin(radians(:userLatitude))*sin(radians(c.latitude)))) <= 3")
    List<ChargerEntity> findChargersByNear(@Param("userLatitude") Double userLatitude, @Param("userLongitude") Double userLongitude);

//    @Query("SELECT new com.example.happyDream.DTO.ChargerDetailDTO(" +
//            "c.id as chargerId, c.name, c.city1, c.city2, c.city2Code, c.addressNew, c.addressOld, c.addressDetail, c.latitude, c.longitude, " +
//            "c.weekdayOpen, c.saturdayOpen, c.holidayOpen, c.weekdayClose, c.saturdayClose, c.holidayClose, c.chargerCount, c.chargeAirYn, c.chargePhoneYn, c.callNumber, " +
//            "c.updatedDate, c.createdAt as chargerCreatedAt, c.modifiedAt as chargerModifiedAt, c.deletedYn, c.deletedAt, " +
//            "cs.id as chargerStateId, cs.usingYn, cs.brokenYn, cs.usingAt, cs.brokenAt, " +
//            "cs.createdAt as chargerStateCreatedAt, cs.modifiedAt as chargerStateModifiedAt) " +
//            "FROM ChargerEntity c " +
//            "JOIN c.chargerState cs")
    @Query("SELECT new com.example.happyDream.DTO.ChargerDetailDTO(" +
            "c.id as chargerId, c.name as name, c.city1 as city1, c.city2 as city2, c.city2Code as city2Code, " +
            "c.addressNew as addressNew, c.addressOld as addressOld, c.addressDetail as addressDetail, " +
            "c.latitude as latitude, c.longitude as longitude, " +
            "c.weekdayOpen as weekdayOpen, c.saturdayOpen as saturdayOpen, c.holidayOpen as holidayOpen, " +
            "c.weekdayClose as weekdayClose, c.saturdayClose as saturdayClose, c.holidayClose as holidayClose, " +
            "c.chargerCount as chargerCount, c.chargeAirYn as chargeAirYn, c.chargePhoneYn as chargePhoneYn, " +
            "c.callNumber as callNumber, " +
            "c.updatedDate as updatedDate, c.createdAt as chargerCreatedAt, c.modifiedAt as chargerModifiedAt, " +
            "c.deletedYn as deletedYn, c.deletedAt as deletedAt, " +
            "cs.id as chargerStateId, cs.usingYn as usingYn, cs.brokenYn as brokenYn, " +
            "cs.usingAt as usingAt, cs.brokenAt as brokenAt, " +
            "cs.createdAt as chargerStateCreatedAt, cs.modifiedAt as chargerStateModifiedAt) " +
            "FROM ChargerEntity c " +
            "JOIN c.chargerState cs")
    List<ChargerDetailDTO> findAllChargerDetail();
}
