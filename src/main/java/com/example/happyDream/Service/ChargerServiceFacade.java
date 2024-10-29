package com.example.happyDream.Service;

import com.example.happyDream.DTO.*;
import com.example.happyDream.Entity.ChargerEntity;
import com.example.happyDream.Util.GsonUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ChargerServiceFacade {
    private static final String CHARGER_MAPPING_JSON_PATH = "src/main/resources/data/chargerDataGovernmentMapping.json";

    private final ChargerService chargerService;
    private final ChargerStateService chargerStateService;
    private final ChargerLogService chargerLogService;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public ChargerServiceFacade(ChargerService chargerService, ChargerStateService chargerStateService, ChargerLogService chargerLogService) {
        this.chargerService = chargerService;
        this.chargerStateService = chargerStateService;
        this.chargerLogService = chargerLogService;
    }

    /* ===== ChargerService ===== */
    // 전체 충전기 조회
    public List<ChargerDTO> chargerSelectAll(){
        return this.chargerService.chargerSelectAll();
    }

    public List<ChargerDetailDTO> chargerSelectAllDetail(){
        return this.chargerService.chargerSelectAllDetail();
    }

    @Transactional
    public ResponseDTO createChargerSyncGovernment(String requestJson) {
        Gson gson = GsonUtil.createGson();

        // JSON 기본 구조 파싱
        JsonObject jsonObject = gson.fromJson(requestJson, JsonObject.class); // JsonObject로 파싱
        JsonArray fieldsArray = jsonObject.getAsJsonArray("fields"); // fields 배열 추출(데이터 형식)
        JsonArray recordsArray = jsonObject.getAsJsonArray("records"); // records 배열 추출(실제 데이터)

        // 공공데이터-ChargerDTO 간 필드 매핑 데이터 로딩
        Map<String, String> fieldMapping = new HashMap<>();
        try (FileReader reader = new FileReader(CHARGER_MAPPING_JSON_PATH)) {
            fieldMapping = gson.fromJson(reader, Map.class);
        } catch (IOException e) {
            log.error("공공데이터-ChargerDTO 간 필드 매핑에 필요한 JSON 파일을 불러오지 못함 {}\n{}", CHARGER_MAPPING_JSON_PATH, e.getStackTrace());
            return ResponseDTO.error("v1", HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "공공데이터-ChargerDTO 간 필드 매핑에 필요한 JSON 파일을 불러오지 못함");
        }

        // 필드 매핑
        List<ChargerDTO> chargerDtoList = new ArrayList<>();
        for (JsonElement recordElement : recordsArray) {
            JsonObject recordObject = recordElement.getAsJsonObject(); // 레코드 파싱

            ChargerDTO newCharger = new ChargerDTO();
            for (Field field : ChargerDTO.class.getDeclaredFields()) {
                field.setAccessible(true); // 리플렉션을 통해 private 접근 제어 무시하고 접근
                String fieldName = fieldMapping.get(field.getName()); // DTO의 필드명을 공공데이터의 필드명으로 변환
                if (fieldName != null) {
                    try {
                        String fieldValue = recordObject.get(fieldName).getAsString(); // DTO에 매핑할 필드의 데이터를 가져옴
                        // 타입별 데이터 파싱 (Reflection 활용)
                        if (field.getType() == String.class) {
                            field.set(newCharger, fieldValue);
                        } else if (field.getType() == Integer.TYPE || field.getType() == Integer.class) {
                            field.set(newCharger, Integer.parseInt(fieldValue));
                        } else if (field.getType() == Double.TYPE || field.getType() == Double.class) {
                            field.set(newCharger, Double.parseDouble(fieldValue));
                        } else if (field.getType() == LocalTime.class) {
                            field.set(newCharger, LocalTime.parse(fieldValue + ":00"));
                        } else if (field.getType() == Time.class) {
                            field.set(newCharger, Time.valueOf(fieldValue + ":00")); // DTO 상 LocalTime 임에도 Time 으로 데이터 인식
                        } else if (field.getType() == LocalDate.class) {
                            field.set(newCharger, LocalDate.parse(fieldValue));
                        } else if (field.getType() == Date.class) {
                            field.set(newCharger, Date.valueOf(fieldValue)); // DTO 상 LocalDate 임에도 Date 로 데이터 인식
                        } else if (field.getType() == Boolean.TYPE || field.getType() == Boolean.class) {
                            field.set(newCharger, fieldValue.equals("Y"));
                        }
                    } catch (IllegalAccessException | NumberFormatException e) {
                        log.warn("타입별 데이터 파싱 중 오류 발생 - 필드명: {}\n{}", field.getName(), e.getStackTrace());
                    }
                }
            }
            ChargerEntity chargerEntity = this.chargerService.createChargerLegacy(newCharger.toEntity());
            this.chargerService.chargerSelect(chargerEntity.getId());
            this.chargerStateService.createChargerStateLegacy(chargerEntity);
            this.chargerService.chargerSelect(chargerEntity.getId());

            chargerDtoList.add(chargerEntity.toDTO());
        }
        log.info("변환 성공한 충전기 수: {}", chargerDtoList.size());

        return ResponseDTO.success("v1", HttpServletResponse.SC_OK, chargerDtoList);
    }

    // 충전기 추가
    public void createCharger(ChargerDTO chargerDTO) {
        this.chargerService.createCharger(chargerDTO);
    }

    // 전체 충전기 삭제
    public void chargerDeleteAll(){
        this.chargerService.chargerDeleteAll();
    }

    // 특정 충전기 조회
    public ChargerDTO chargerSelect(Integer id){
        return this.chargerService.chargerSelect(id);
    }

    // 특정 충전기 조회(주소)
    public List<ChargerDTO> chargerSelectByAddress(String address){
        return this.chargerService.chargerSelectByAddress(address);
    }

    // 주변 충전기 조회
    public List<ChargerDTO> chagerSelectNear(Double latitude, Double longitude){
        return this.chargerService.chargerSelectNear(latitude, longitude);
    }

    // 특정 충전기 삭제
    public void chargerDelete(Integer id){
        this.chargerService.chargerDelete(id);
    }

    /* ===== ChargerLogService ===== */

    // 전체 충전 로그 조회
    public List<ChargerLogDTO> getAllChargerLog(Boolean join) {
        return this.chargerLogService.getAllChargerLog(join);
    }

    // 전체 충전 로그 삭제
    public Long deleteAllChargerLog() {
        return this.chargerLogService.deleteAllChargerLog();
    }

    // 특정 충전기의 전체 충전 로그 조회
    public List<ChargerLogDTO> getAllTargetChargerLog(Integer chargerId) {
        return this.chargerLogService.getAllTargetChargerLog(chargerId);
    }

    // 특정 충전기 충전 로그 추가
    public void createTargetChargerLog(ChargerLogDTO chargerLogDto) {
        // FK(ChargerId) 존재 여부 1차 확인 후 PK(ChargerLogId) 부존재 여부 2차 확인
        try {
            this.chargerService.chargerSelect(chargerLogDto.getChargerId()); // 검증용 호출, 반환값 ignored
            this.chargerLogService.createTargetChargerLog(chargerLogDto);
        } catch (EntityNotFoundException ignored) { }
    }

    /* ===== ChargerStateService ===== */

    // 전체 충전기 상태 조회
    public List<ChargerStateDTO> getAllChargerState() {
        return this.chargerStateService.getAllChargerState();
    }

    // 전체 충전기 상태 초기화
    public void initAllChargerState() {
        this.chargerStateService.initAllChargerState();
    }

    // 특정 충전기 상태 조회
    public ChargerStateDTO getTargetChargerState(ChargerDTO chargerDto) {
        try {
            return this.chargerStateService.getTargetChargerState(chargerDto);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException();
        }
    }

    // 특정 충전기 상태 업데이트
    public void changeTargetChargerState(ChargerStateDTO chargerStateDto) {
        this.chargerStateService.changeTargetChargerState(chargerStateDto);
    }

}
