package com.example.happyDream.Service;

import com.example.happyDream.DTO.ChargerDto;
import com.example.happyDream.DTO.ChargerStateDto;
import com.example.happyDream.Entity.ChargerEntity;
import com.example.happyDream.Entity.ChargerStateEntity;
import com.example.happyDream.Repository.ChargerStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChargerStateService {
    private final ChargerStateRepository chargerStateRepository;

    @Autowired
    public ChargerStateService(ChargerStateRepository chargerStateRepository) {
        this.chargerStateRepository = chargerStateRepository;
    }

    public List<ChargerStateDto> toEntityList(List<ChargerStateEntity> entityList) {
        return entityList.stream()
                .map(ChargerStateEntity::toDto)
                .collect(Collectors.toList());
    }

    public List<ChargerStateEntity> toDtoList(List<ChargerStateDto> dtoList) {
        return dtoList.stream()
                .map(ChargerStateDto::toEntity)
                .collect(Collectors.toList());
    }
}
