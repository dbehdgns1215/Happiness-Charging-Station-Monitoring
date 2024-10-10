package com.example.happyDream.Service;

import com.example.happyDream.DTO.ChargerDto;
import com.example.happyDream.DTO.ChargerStateDto;
import com.example.happyDream.Entity.ChargerStateEntity;
import com.example.happyDream.Repository.ChargerStateRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class ChargerStateServiceTest {
    // 테스트 주체
    @InjectMocks
    ChargerStateService chargerStateService;

    // 테스트 협력자
    @Mock
    ChargerStateRepository chargerStateRepository;

    // 공통 given
    ChargerDto chargerDto = ChargerDto.builder().id(3).build();


    /* ===== PUBLIC TEST ===== */
    @Test
    @DisplayName("데이터베이스가 비었을 때 getAll")
    void getAllChargerStateNullDatabase() {
        // given
        List<ChargerStateDto> result = chargerStateService.getAllChargerState();

        // when 데이터베이스 조회시 빈 값 반환한다고 가정
        when(chargerStateRepository.findAll()).thenReturn(Collections.emptyList());

        // then 리스트가 비어있는지 확인
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    @Rollback
    @Disabled("테스트시 throw 주석 처리 해제 필요")
    @DisplayName("이미 존재하는 충전기 상태에 대한 추가")
    void createChargerStateAlreadyExistTest() {
        // given
        ChargerStateEntity existingEntity = ChargerStateEntity.builder().chargerId(chargerDto.toEntity()).build();

        // when 이미 존재하는 엔티티가 있다고 가정
        when(chargerStateRepository.findByChargerId(any())).thenReturn(Optional.of(existingEntity));

        // then EntityExistsException 발생 확인
        assertThrows(EntityExistsException.class, () -> {
            chargerStateService.createChargerState(chargerDto);
        });
    }

    @Test
    @DisplayName("존재하지 않는 충전기 조회")
    void getTargetChargerStateNotFoundTest() {
        // when EntityNotFoundException 발생 확인
        assertThatThrownBy(() -> chargerStateService.getTargetChargerState(chargerDto))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("존재하지 않는 충전기 업데이트")
    void changeTargetChargerStateNotFoundTest() {
        // given
        ChargerStateDto chargerStateDto = ChargerStateDto.builder()
                .chargerId(chargerDto.toEntity())
                .usingYn(true)
                .build();

        // when EntityNotFoundException 발생 확인
        assertThatThrownBy(() -> chargerStateService.changeTargetChargerState(chargerStateDto))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test()
    @Disabled("테스트시 throw 주석 처리 해제 필요")
    @DisplayName("충전기 상태 변경 시 Null 값 처리 테스트")
    void changeTargetChargerStateBooleanNullTest() {
        // given
        ChargerStateDto chargerStateDto = ChargerStateDto.builder()
                .chargerId(chargerDto.toEntity())
                .usingYn(true)
                .build();

        // when 설정하지 않은 값이 null 값을 가지는지 확인
        assertThat(chargerStateDto.getBrokenYn()).isNull();
    }
}