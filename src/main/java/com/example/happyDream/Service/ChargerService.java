package com.example.happyDream.Service;

import com.example.happyDream.DTO.ChargerDTO;
import com.example.happyDream.Entity.ChargerEntity;
import com.example.happyDream.Repository.ChargerRepository;
import com.example.happyDream.Util.Converter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.happyDream.DTO.ChargerDTO;

import java.io.FileReader;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ChargerService {
    private final ChargerRepository chargerRepository;

    @Autowired
    public ChargerService(ChargerRepository chargerRepository) {
        this.chargerRepository = chargerRepository;
    }

    public List<ChargerDTO> convertEntityListToDtoList(List<ChargerEntity> entityList) {
        // Stream을 사용하여 Entity -> Dto 변환 후 리스트로 반환
        return entityList.stream()
                .map(ChargerEntity::toDTO) // 각 엔티티를 DTO로 변환
                .collect(Collectors.toList());  // 변환된 결과를 리스트로 수집
    }
    public List<ChargerEntity> convertDtoListToEntityList(List<ChargerDTO> dtoList) {
        // Stream을 사용하여 Entity -> Dto 변환 후 리스트로 반환
        return dtoList.stream()
                .map(ChargerDTO::toEntity) // 각 엔티티를 DTO로 변환
                .collect(Collectors.toList());  // 변환된 결과를 리스트로 수집
    }

    // 충전기 추가(단일)
    public ChargerDTO createCharger(ChargerDTO chargerDto) {
        return this.chargerRepository.save(chargerDto.toEntity()).toDTO();
    }

    // 충전기 추가(리스트)
    public void createCharger(List<ChargerDTO> chargerDtoList) {
        List<ChargerEntity> chargerEntityList = Converter.DtoListToEntityList(chargerDtoList, ChargerDTO::toEntity);
        this.chargerRepository.saveAll(chargerEntityList);
    }

    public List<ChargerDTO> chargerSelectAll(){
        List<ChargerEntity> entityList = this.chargerRepository.findAll();
        List<ChargerDTO> dtoList = Converter.EntityListToDtoList(entityList, ChargerEntity::toDTO);
        System.out.println("가져온 충전기 수: " + dtoList.size());
        return dtoList;
    }

    // 전체 충전기 삭제
    public void chargerDeleteAll(){
        this.chargerRepository.deleteAll();
    }

    // 특정 충전기 조회
    public ChargerDTO chargerSelect(Integer id){
        Optional<ChargerEntity> entity = this.chargerRepository.findById(id);
        if(entity.isPresent()) {
            return entity.get().toDTO();
        } else {
            throw new EntityNotFoundException();
        }
    }

    // 특정 충전기 조회(주소)
    public List<ChargerDTO> chargerSelectByAddress(String address){
        Set<ChargerEntity> chargers = new HashSet<>();
        List<ChargerDTO> chargersDTO = new ArrayList<>();
        String[] addressList = address.split(" ");
        try{
            for (int i = 0; i<addressList.length; i++){
                if(i==0){
                    chargers.addAll(this.chargerRepository.findChargersByAddress(addressList[i]));
                }else{
                    chargers.retainAll(this.chargerRepository.findChargersByAddress(addressList[i]));
                }
            }
            // chargers 리스트가 비어 있는지 체크하고, 비어 있지 않다면 내용을 출력
            if (chargers.isEmpty()) {
                System.out.println("충전소 데이터가 없습니다.");
                throw new EntityNotFoundException();
            } else {
                for(ChargerEntity chargerEntitys : chargers) {
                    chargersDTO.add(chargerEntitys.toDTO());
                }
                return chargersDTO;
            }
        }catch (Exception exception){
            throw new EntityNotFoundException();
        }
    }

    // 특정 충전기 삭제
    public void chargerDelete(Integer id){
        this.chargerRepository.deleteById(id);
    }

    // 주변 충전기 조회
    public List<ChargerDTO> chargerSelectNear(Double latitude, Double longitude){
        List<ChargerEntity> chargers = this.chargerRepository.findChargersByNear(latitude, longitude);
        if(chargers.isEmpty()) {
            throw new EntityNotFoundException();
        }
        else{
            return Converter.EntityListToDtoList(chargers, ChargerEntity::toDTO);
        }
    }
}
