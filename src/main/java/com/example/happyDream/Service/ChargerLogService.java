package com.example.happyDream.Service;

import com.example.happyDream.Repository.ChargerLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChargerLogService {
    private final ChargerLogRepository chargerLogRepository;

    @Autowired
    public ChargerLogService(ChargerLogRepository chargerLogRepository) {
        this.chargerLogRepository = chargerLogRepository;
    }
}
