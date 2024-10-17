package com.example.happyDream.RestController;

import com.example.happyDream.DTO.ChargerDTO;
import com.example.happyDream.DTO.ChargerLogDTO;
import com.example.happyDream.DTO.ResponseDTO;
import com.example.happyDream.Service.ChargerLogService;
import com.example.happyDream.Service.ChargerServiceFacade;
import com.example.happyDream.Util.GsonUtil;
import com.example.happyDream.Util.LocalDateAdapter;
import com.example.happyDream.Util.LocalDateTimeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1", produces = "application/json")
@Tag(name = "ChargerLog", description = "충전기 로그 API")
public class ChargerLogRestController {
    private final ChargerServiceFacade chargerServiceFacade;

    @Autowired
    public ChargerLogRestController(ChargerServiceFacade chargerServiceFacade, ChargerLogService chargerLogService) {
        this.chargerServiceFacade = chargerServiceFacade;
    }
    
    @GetMapping("/chargers/logs")
    @Operation(summary = "전체 충전 로그 조회", description = "전체 충전기의 충전 로그를 조회합니다.")
    public String getAllChargerLog() {
        ResponseDTO responseDto;
        try {
            List<ChargerLogDTO> chargerLogDtoList = this.chargerServiceFacade.getAllChargerLog(false);

            if (chargerLogDtoList.isEmpty()) {
                responseDto = ResponseDTO.success("v1", HttpServletResponse.SC_NO_CONTENT, Collections.unmodifiableList(chargerLogDtoList));
            }
            else {
                responseDto = ResponseDTO.success("v1", HttpServletResponse.SC_OK, Collections.unmodifiableList(chargerLogDtoList));
            }
        } catch (Exception e) {
            log.error("알 수 없는 오류: {} ", (Object) e.getStackTrace());
            responseDto = ResponseDTO.error("v1", HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "알 수 없는 오류");
        }

        Gson gson = GsonUtil.createGson();
        return gson.toJson(responseDto);
    }
    
    // TODO - 권한 검증 추가
    @DeleteMapping("/chargers/logs")
    @Operation(summary = "전체 충전 로그 삭제", description = "전체 충전기의 충전 로그를 삭제합니다.")
    public String deleteAllChargerLog() {
        this.chargerServiceFacade.deleteAllChargerLog();
        return "";
    }

    @GetMapping("/chargers/{id}/logs")
    @Operation(summary = "특정 충전기의 전체 충전 로그 조회", description = "충전기 id를 입력받아 특정 충전기의 전체 충전 로그를 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "충전 로그 조회 성공", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public String getChargerLog(@Parameter(description = "충전기 id", required = true, in = ParameterIn.PATH, example = "1")
                                    @PathVariable("id") Integer chargerId) {
        ChargerDTO chargerDto = chargerServiceFacade.chargerSelect(chargerId);
        List<ChargerLogDTO> chargerLogDtoList = chargerServiceFacade.getAllTargetChargerLog(chargerDto);
        return "";
    }

    @PostMapping("/chargers/{id}/logs")
    @Operation(summary = "특정 충전기의 충전 로그 추가", description = "충전기 id를 입력받아 특정 충전기의 충전 로그를 추가합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "충전 로그 추가 성공", content = @Content(schema = @Schema(implementation = ResponseDTO.class, hidden = true))),
                    @ApiResponse(responseCode = "400", description = "충전 로그 추가 실패: ampere is null(파라미터 오류)", content = @Content(schema = @Schema(implementation = ResponseDTO.class, hidden = true))),
                    @ApiResponse(responseCode = "404", description = "충전 로그 추가 실패: 존재하지 않는 충전기에 대한 작업 시도", content = @Content(schema = @Schema(implementation = ResponseDTO.class, hidden = true))),
                    @ApiResponse(responseCode = "500", description = "충전 로그 추가 실패: 알 수 없는 오류", content = @Content(schema = @Schema(implementation = ResponseDTO.class, hidden = true)))},
            parameters = {
                    @Parameter(name = "id", description = "충전기 id", required = true, in = ParameterIn.PATH, example = "1")
            }
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(
            examples = {
                    @ExampleObject(
                            name = "Correct Request: Response Code 200",
                            description = "정상적인 요청",
                            value = "{ \"ampere\": 53.941 }"
                    ),
                    @ExampleObject(
                            name = "Wrong Request: Response Code 400",
                            description = "잘못된 요청(null)",
                            value = "{ }"
                    )
            }
    ))
    public String createChargerLog(@PathVariable("id") Integer chargerId, @RequestBody ChargerLogDTO _chargerLogDto) {
        log.info(_chargerLogDto.toString());
        ResponseDTO responseDto;

        try {
            Float ampere = _chargerLogDto.getAmpere();
            if (ampere == null) {
                throw new NullPointerException("ampere is null");
            }
            ChargerDTO chargerDto = chargerServiceFacade.chargerSelect(chargerId);

            ChargerLogDTO chargerLogDto = ChargerLogDTO.builder()
                    .chargerId(chargerDto.toEntity())
                    .ampere(ampere)
                    .build();

            chargerServiceFacade.createTargetChargerLog(chargerLogDto);
            responseDto = ResponseDTO.success("v1", HttpServletResponse.SC_OK);
        } catch (EntityNotFoundException ignored) {
            log.warn("존재하지 않는 충전기 id: {}", chargerId);
            responseDto = ResponseDTO.error("v1", HttpServletResponse.SC_NOT_FOUND, "존재하지 않는 충전기 id: " + chargerId);
        } catch (NullPointerException e) {
            log.error("{} - 요청한 충전기 id: {}\n{}", e.getMessage(), chargerId, e.getStackTrace());
            responseDto = ResponseDTO.error("v1", HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            log.error("알 수 없는 오류 - 요청한 충전기 id: {}\n{}", chargerId, e.getStackTrace());
            responseDto = ResponseDTO.error("v1", HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "알 수 없는 오류");
        }

        Gson gson = GsonUtil.createGson();
        return gson.toJson(responseDto);
    }

    // 충전기 하드웨어 설정값 조회
    @GetMapping("/chargers/logs/upgrade")
    public String getChargerLogSetting() {
        return "";
    }

    // 충전기 하드웨어 설정값 변경
    @PutMapping("/chargers/logs/upgrade")
    public String updateChargerLogSetting() {
        return "";
    }
}

/* https://second-brain.tistory.com/15
## Swagger에서 @ExampleObject를 이용한 테스트 JSON 분리하기

**@ExampleObject** 어노테이션은 Swagger UI에서 API를 테스트할 때 사용되는 예시 JSON 데이터를 정의하는 데 유용합니다. 하지만 예시 데이터가 길어지면 코드 가독성이 떨어지고 관리하기 어려워질 수 있습니다. 이러한 경우, 예시 데이터를 별도의 properties 파일로 분리하여 관리하는 것이 효과적입니다.

**SpringDoc OpenApi**를 사용하여 properties 파일을 활용하는 방법을 예시와 함께 설명하겠습니다.

### 1\. Properties 파일 생성

`application.properties` 또는 `application.yml` 파일과 같은 설정 파일을 생성하고, 예시 JSON 데이터를 다음과 같이 정의합니다.

```properties
# application.properties
example.user.json=classpath:user-example.json
```

`user-example.json` 파일에는 실제 예시 JSON 데이터를 작성합니다.

```json
# user-example.json
{
    "name": "John Doe",
    "age": 30,
    "email": "johndoe@example.com"
}
```

### 2\. @ExampleObject 어노테이션 사용

Controller 클래스에서 @ExampleObject 어노테이션을 사용하여 properties 파일의 위치를 지정합니다.

```java
@PostMapping("/users")
public User createUser(@RequestBody @Schema(description = "사용자 정보") UserDto userDto) {
    // ...
}

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("My API"))
                .components(new Components()
                    .addSchemas("UserDto", new Schema()
                    .description("사용자 정보")
                    .example(new Reference().$ref("UserDto"))
                    .examples(new Examples()
                        .addExamples("userExample", new Example()
                            .value(new StringReference("classpath:user-example.json"))
                            .summary("User Example"))))));
    }
}
```

### 코드 설명

  * `@Schema(example = new Reference().$ref("UserDto"))`: UserDto 스키마에 대한 예시를 정의합니다.
  * `@Examples`: 여러 개의 예시를 추가할 수 있습니다.
  * `StringReference`: 외부 파일의 위치를 참조합니다.

### 장점

  * **코드 가독성 향상:** 예시 데이터가 코드에서 분리되어 가독성이 좋아집니다.
  * **유지보수 편의성:** 예시 데이터를 변경할 때 코드를 수정할 필요 없이 properties 파일만 수정하면 됩니다.
  * **다양한 예시 관리:** 여러 개의 예시를 쉽게 관리할 수 있습니다.

### 추가 고려 사항

  * **프로젝트 구조:** 프로젝트 구조에 맞게 properties 파일의 위치를 조정해야 합니다.
  * **복잡한 예시 데이터:** 매우 복잡한 예시 데이터의 경우, JSON 스키마를 활용하여 더욱 정교하게 관리할 수 있습니다.
  * **다른 라이브러리:** SpringDoc OpenApi 외에도 다른 Swagger 라이브러리를 사용하는 경우, 설정 방법이 약간 다를 수 있습니다.

**주의:**

  * **SpringDoc OpenApi 버전:** 사용하는 SpringDoc OpenApi 버전에 따라 설정 방법이 약간 다를 수 있습니다.
  * **properties 파일 위치:** properties 파일의 위치를 정확하게 설정해야 합니다.
  * **JSON 스키마:** 복잡한 예시 데이터의 경우, JSON 스키마를 활용하여 더욱 정교하게 관리할 수 있습니다.

**이 방법을 활용하여 Swagger UI에서 더욱 효율적으로 예시 데이터를 관리하고, API 문서의 가독성을 높일 수 있습니다.**

* */
