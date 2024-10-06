package com.example.happyDream.Service;

import com.example.happyDream.Repository.ChargerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChargerService {
    private final ChargerRepository chargerRepository;

    @Autowired
    public ChargerService(ChargerRepository chargerRepository) {
        this.chargerRepository = chargerRepository;
    }
}
