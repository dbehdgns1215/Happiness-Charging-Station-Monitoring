package com.example.happyDream.Service;

import com.example.happyDream.Entity.ChargerEntity;
import com.example.happyDream.Repository.ChargerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.happyDream.DTO.ChargerDto;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChargerService {
    private final ChargerRepository chargerRepository;

    @Autowired
    public ChargerService(ChargerRepository chargerRepository) {
        this.chargerRepository = chargerRepository;
    }

    public List<ChargerDto> convertEntityListToDtoList(List<ChargerEntity> entityList) {
        // Stream을 사용하여 Entity -> Dto 변환 후 리스트로 반환
        return entityList.stream()
                .map(ChargerEntity::toDto) // 각 엔티티를 DTO로 변환
                .collect(Collectors.toList());  // 변환된 결과를 리스트로 수집
    }

    public List<ChargerEntity> convertDtoListToEntityList(List<ChargerDto> dtoList) {
        // Stream을 사용하여 Entity -> Dto 변환 후 리스트로 반환
        return dtoList.stream()
                .map(ChargerDto::toEntity) // 각 엔티티를 DTO로 변환
                .collect(Collectors.toList());  // 변환된 결과를 리스트로 수집
    }

    public List<ChargerDto> chargerSelectAll(){
        List<ChargerDto> dtoList = convertEntityListToDtoList(this.chargerRepository.findAll());
        return dtoList;
    }
}
