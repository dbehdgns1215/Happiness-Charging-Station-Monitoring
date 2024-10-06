package com.example.happyDream.Service;

import com.example.happyDream.Repository.ChargerStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChargerStateService {
    private final ChargerStateRepository chargerStateRepository;

    @Autowired
    public ChargerStateService(ChargerStateRepository chargerStateRepository) {
        this.chargerStateRepository = chargerStateRepository;
    }
}
