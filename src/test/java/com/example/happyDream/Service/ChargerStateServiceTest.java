package com.example.happyDream.Service;

import com.example.happyDream.DTO.ChargerDto;
import com.example.happyDream.Repository.ChargerStateRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(SpringExtension.class)
class ChargerStateServiceTest {
    // 테스트 주체
    @Autowired
    ChargerStateService chargerStateService;

    // 테스트 협력자
    @MockBean
    ChargerStateRepository chargerStateRepository;

    ChargerDto chargerDto = ChargerDto.builder().id(123).build();

    @Test
    void getAllChargerState() {
    }

    @Test
    void initAllChargerState() {
    }

    @Test
    @DisplayName("존재하는 충전기에 대한 상태 추가")
    void createChargerStateAlreadyExistTest() {

    }

    @Test
    @DisplayName("존재하지 않는 충전기 조회")
    void getTargetChargerStateNotFoundTest() {
        assertThatThrownBy(() -> chargerStateService.getTargetChargerState(chargerDto)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("존재하지 않는 충전기 업데이트")
    void changeTargetChargerStateNotFoundTest() {
        chargerStateService.changeTargetChargerState(chargerDto, false);
    }
}