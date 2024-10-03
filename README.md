# 🌏 지체장애인을 위한 행복충전소 지도 어플리케이션 : "해피드림" <br><br> 👥 TEAM 세잎 클로버 <br>
<br>

## 📖 목차
1. [프로젝트 설명](#-프로젝트-설명)
2. [프로젝트 목표](#-프로젝트-목표)
3. [기대효과](#-기대효과)
4. [기여 가이드](#-기여-가이드)
5. [프로젝트 구조](#-프로젝트-구조)
6. [기술 스택](#-기술-스택)
7. [프로젝트 기여자](#-프로젝트-기여자)

--- 
## 📜 프로젝트 설명
현재 대전시에는 지체장애인들을 위한 전동휠체어를 충전하는 행복충전소(전동휠체어 급속충전기)가 상용화 되고 있습니다. 사용자는 이러한 행복충전소를 기존에 제공되고 있던 행복충전소 위치 파악 어플리케이션들을 이용해 사용하고 있습니다. 하지만 해당 애플리케이션들은 행복충전소의 위치만 알려주기 때문에 현재 사용이 가능한지 알 수 없으며, 행복충전소  사용빈도, 고장여부 등을 알 수 없다는 점을 가지고 있습니다. 본 프로젝트는 기존 지도 기반의 솔루션에 충전소 사용 가능 여부, 충전소 분석(사용빈도, 전력량, 고장여부 등), 지체장애인을 위한 편의 기능 등을 추가적으로 개발하여 실시간으로 사용자에게 편의를 제공하고자 합니다. 
<br><br>

## 🥅 프로젝트 목표
- 설치된 충전기의 효율적인 운영 및 수요 분석을 위해 충전기 사용 현황을 실시간으로 모니터링하는 서비스를 만들고자 함.
- 기업 측 요구사항에 따라 하드웨어보다 소프트웨어 및 실제 운영 가능한 서비스에 초점을 맞춰 개발할 것임.
- 추후 기업 측에서 하드웨어 개발/운영 가능성이 있으므로, REST API 등을 통한 확장성 있는 개발 방식을 선택해 개발할 것임.
- 각 충전기의 충전 상태 및 각종 정보를 관리하는 서버와 실사용자인 지체장애인이 이용 가능한 앱 형태로 개발할 것임.
- 서버 개발을 위해 JAVA/Spring을 사용하여 서버를 개발하고, 코틀린을 사용해 안드로이드 앱을 개발할 것임.
- 충전기의 위치를 지도상에 표시하고, 충전기의 상세 정보를 확인할 수 있는 기능을 필수적으로 구현하고자 함.
<br><br>

## 🔍 기대효과
- 본 프로젝트의 결과물을 통해 설치된 충전기의 충전 현황을 실시간으로 관리할 수 있음.
- 이를 통해 충전기 운영 패턴을 파악하고, 잠재 수요지를 파악하여 충전기 추가 설치 등의 연계 사업이 가능함.
- 충전기 정보 확인 외에도 장애인 화장실, 장애인 보장구 수리점 등의 위치를 확인할 수 있도록 할 수 있고, 장애인 콜택시 등과 연계하여 택시 호출 등 장애인 편의 기능을 다양하게 탑재할 수 있을 것으로 기대됨.
- 또한 전력 측정 데이터를 수집하는 시스템이므로, 홈 IoT 사업으로 추가 개발이 가능함.
- 이를 위해서는 전문적인 하드웨어 개발이 필요할 것으로 보임 .
<br><br>
---

## 🎮 기여 가이드

### 1. 커밋 메시지 규칙
커밋 메시지를 단순하면서도 명확하게 작성하여 프로젝트의 변경 사항을 추적하기 쉽게 유지합니다.

- **커밋 메시지 형식**:  
  `태그: 작업 내용`
  ```
  // Header, Body, Footer는 빈 행으로 구분한다.
  타입: 주제(제목) // Header(헤더)

  본문 // Body(바디)

  바닥글 // Footer
  ```

  ---

  **Header**는 **필수**이며 **타입**은 해당 커밋의 성격을 나타냅니다.
  또한 타입은 아래의 **태그** 중 하나여야만 합니다.

  **Header**의 기본 형식은 **타입: 주제(or 제목)**로 구성됩니다.
   
  **Body**는 **선택 사항**입니다.
  어떤 변경을 했는지 더 구체적으로 설명하며, 여러 줄로 작성할 수 있습니다.
  무조건 필요한 내용이 아니면 **생략**할 수 있습니다.

  **Body**의 기본 형식은 **Header**와 **Footer**의 사이에 **빈 줄**을 두고 작성합니다.

  **Footer**는 **선택 사항**입니다.
  주로 특정 이슈나 참고해야 할 정보가 있다면 여기에 추가합니다.
  주로 **이슈 번호**, **참조 링크** 등을 표시하는 용도로 사용합니다.

  **Footer**의 기본 형식은 `resolves: #이슈번호` 또는 `fixes: #이슈번호`로 작성합니다.

  ---
  
- **태그**는 다음 중 하나를 사용:
  - `feat`: 새로운 기능 추가
  - `fix`: 버그 수정
  - `docs`: 문서 작업 (README 수정 등)
  - `style`: 코드 스타일 수정 (포매팅, 주석 추가 등)
  - `refactor`: 코드 리팩토링 (기능 변경 없음)
  - `test`: 테스트 코드 관련 작업
  - `chore`: 기타 작업 (예: 빌드 설정 변경, 패키지 업데이트)
<br><br>

- **커밋 메시지 예시**:
  ```
  feat: 전동휠체어 충전소 검색 기능 추가

  전동휠체어 사용자를 위한 충전소 검색 기능을 추가하여
  주변 충전소를 쉽게 찾을 수 있도록 구현함.

  resolves: #12
  ```
  ```
  fix: 충전소 지도에서 좌표 오차 수정

  좌표 변환 오류로 인해 500m 오차가 발생하던 문제를 수정함.
  좌표계 변환 로직을 적용하여 정확한 위치 표시.

  resolves: #34
  ```
  ```
  docs: README에 프로젝트 개요 추가

  프로젝트 목표와 설치 방법에 대한 설명을 README에 추가함.
  ```
  ```
  style: 코딩 컨벤션에 맞게 코드 포매팅 수정

  불필요한 공백과 들여쓰기를 수정하여 코드 스타일을 통일함.
  
  커밋 메시지 작성 시 작업 내용을 간단하고 명확하게 적습니다. 너무 많은 변경 사항을 한 번에 커밋하기보다, 가능한 작은 단위로 자주 커밋하는 것이 좋습니다.
  ```
  
### 2. 브랜치 전략
우리 프로젝트에서는 **Git Flow**와 비슷한 전략을 사용하되, 조금 더 유연하게 적용합니다.

- **메인 브랜치**:
  - `main`: 항상 안정적이고 배포 가능한 상태의 코드를 유지합니다. 릴리즈가 될 때 이 브랜치로 머지됩니다.
<br><br>
- **개발 브랜치**:
  - `develop`: 모든 기능이 통합되는 브랜치입니다. 새로운 기능 개발은 이 브랜치에서 시작하고, 버그 수정과 작은 변경 사항도 여기서 처리됩니다.
<br><br>
- **기능 브랜치 (Feature Branch)**:
  - `feature/기능명`: 새로운 기능을 개발할 때 사용하는 브랜치입니다. 각 기능은 독립된 브랜치에서 작업하고, 작업이 완료되면 `develop` 브랜치로 머지합니다.
  - 기능이 작은 경우 한두 번의 커밋으로 완료될 수 있습니다. 큰 기능의 경우, 작업 단계를 나눠 작은 커밋들을 여러 번 푸시할 수 있습니다.
  
  **예시**:  
  - `feature/login-page`: 로그인 기능을 개발하는 브랜치
  - `feature/map-fix`: 지도 API 버그를 수정하는 브랜치
<br><br>
- **버그 수정 브랜치 (Hotfix Branch)**:
  - `hotfix/버그명`: 배포된 코드에 긴급한 버그가 발생했을 때 사용하는 브랜치입니다. 이 브랜치는 `main`에서 바로 따고, 수정 후 바로 `main`에 머지됩니다. 이후 `develop`에도 머지하여, 개발 중인 코드도 최신 상태로 유지합니다.
  
  **예시**:  
  - `hotfix/api-crash`: 중요한 API 관련 문제를 빠르게 해결
<br><br>
- **릴리즈 브랜치 (Release Branch)**:
  - `release/버전번호`: 배포 전 안정화 작업을 위한 브랜치입니다. QA 과정에서 발생한 버그를 수정하거나, 최종 테스트 후 문제가 없다면 `main` 브랜치로 머지됩니다.
  - 릴리즈가 완료되면, 이 브랜치는 삭제됩니다.

  **예시**:  
  - `release/1.0.0`: 첫 번째 릴리즈를 준비하는 브랜치
<br><br>
### 브랜치 생성 규칙 요약
- **기능 개발**: `feature/기능명`
- **긴급 버그 수정**: `hotfix/버그명`
- **릴리즈 준비**: `release/버전번호`
<br><br>
### 3. 코드 리뷰 규칙
1. 새로운 기능 또는 버그 수정을 완료한 후, **Pull Request (PR)**를 생성합니다.
2. PR에는 작업 내용을 간단히 설명하고, 관련된 이슈나 참고 사항이 있으면 링크합니다.
3. 팀원 한 명 이상의 리뷰를 받고, 피드백이 반영되면 `develop` 또는 `main` 브랜치에 머지합니다.

---

## 🏗 프로젝트 구조

  `/src/main`: 메인 소스 코드 및 리소스<br>
  `/src/test`: 테스트 코드
  
### Controller

- chargerController
- chargerLogController
- chargerStateController
- userController
- reviewController

### Service

- chargerServiceFacade
    - chargerService
    - chargerLogService
    - chargerStateService
- userService
- reviewService

### Repository

- chargerRepository
- chargerLogRepository
- chargerStateRepository
- userRepository
- reviewRepository

### Entity

- chargerEntity
- chargerLogEntity
- chargerStateEntity
- userEntity
- reviewEntity

### DTO

- chargerDTO
- chargerLogDTO
- chargerStateDTO
- userDTO
- reviewDTO

## API 명세서

### URI

- URI는 반드시 복수형으로 작성
- 2개 이상의 단어 사용을 위해 구분자 필요 시 대시(-) 사용

### 권한 수준

<aside>
🚧

상위 레벨은 하위 레벨에 접근 가능

</aside>

- 일반(레벨1): 일반 사용자 앱에서 호출
- 하드웨어(레벨2): 하드웨어에서 호출
- 관리자(레벨3): 관리자 페이지에서 호출
- 테스트(레벨4): 내부 로직에 의해 호출되거나, Postman 등 테스트 도구에 의해 호출(외부 호출일 경우 작업 비밀번호 등 파라미터로 받아서 검증 필수)

### API 목록

![image](https://github.com/user-attachments/assets/c2dffd75-2c44-44b0-a2e0-43a9c460ddfe)

## Notion
[![Notion](https://img.shields.io/badge/Notion-000000?style=for-the-badge&logo=notion&logoColor=white)](https://www.notion.so/24-2f5b2c84ca834c8ea0b2ae211ed28b35)

---

## 💻 기술 스택
- Java Spring
- MySQL
- Kotlin (Android 앱)
- Naver Maps API

---
## 👨‍💻 프로젝트 기여자
<table>
<thead>
<tr>
<th align="center"><strong>유동훈</strong></th>
<th align="center"><strong>안성모</strong></th>
<th align="center"><strong>김태완</strong></th>
</tr>
</thead>
<tbody>
<tr>
<td align="center"><a href="https://github.com/dbehdgns1215"><img src="" height="150" width="150" style="max-width: 100%;"> <br> @dbehdgns1215</a></td>
<td align="center"><a href="https://github.com/yolol312"><img src="" height="150" width="150" style="max-width: 100%;"> <br> @yolol312</a></td>
<td align="center"><a href="https://github.com/yolol312"><img src="" height="150" width="150" style="max-width: 100%;"> <br> @Lullu</a></td>
</tr>
</tbody>
</table>
<br>

