# Agent가 개발 전에 파악해야 할 규칙 및 메모리
AGENTS.md, CLAUDE.md에 써있음

# 목차
[1] 자린고치(Jaringochi) 실행 매뉴얼

[2] 자린고치 프로젝트 설명 (README)



# [1] 자린고치(Jaringochi) 실행 매뉴얼

이 문서는 저장소를 clone 받은 뒤 프로젝트를 실행하기까지의 초기 설정 절차를 설명합니다.

> 프로젝트 코드는 `jaringochi/` 폴더에 있으며, 설계 문서(명세서, 화면설계서, 유스케이스 다이어그램 등)는 상위 폴더에 함께 있습니다.

* sts5에서 run as -> run on server로 실행 (tomcat11 설치되어 있어야 함)

---

## 1. 필수 설치 항목

| 항목 | 버전 | 비고 |
|---|---|---|
| JDK | **17 이상** | `pom.xml` 컴파일 타깃이 17 |
| Apache Tomcat | **11** | Servlet 6.1 (`jakarta.servlet-api 6.1.0`) 기준 |
| MySQL | 8.x | MariaDB도 호환 |
| IDE | STS / Eclipse (또는 IntelliJ) | Maven 내장 |

> Maven 의존성(JSTL, MySQL Connector 8.3.0 등)은 `pom.xml`을 통해 자동 다운로드되므로 별도 jar 설치가 필요 없습니다.

---

## 2. 데이터베이스 준비

MySQL에 접속하여 **스키마 → 데이터 순서**로 SQL을 실행합니다.

```bash
# jaringochi 폴더 기준
mysql -u root -p < sql/schema.sql
mysql -u root -p < sql/data.sql
```

- `schema.sql` : `jaringochi` 데이터베이스와 전체 테이블을 새로 생성합니다.
- `data.sql` : 샘플 데이터(테스트 계정, 카테고리, 거래 내역, 굴비 등)를 삽입합니다.

---

## 3. DB 접속 정보 확인 / 수정

접속 정보는 **`jaringochi/src/main/java/com/jaringochi/util/DBUtil.java`** (11~13행)에 설정되어 있습니다.

```java
private static final String URL = "jdbc:mysql://localhost:3306/jaringochi?useSSL=false&serverTimezone=Asia/Seoul&characterEncoding=UTF-8";
private static final String USER = "root";
private static final String PASSWORD = "ssafy";
```

본인 환경의 계정/비밀번호가 다르면 이 부분을 수정하세요.

---

## 4. 프로젝트 Import 및 빌드

### STS / Eclipse
1. `File ▸ Import ▸ Maven ▸ Existing Maven Projects`
2. `jaringochi` 폴더 선택 후 완료

> `.classpath`, `.project`, `.settings/` 파일은 `.gitignore`로 제외되어 있어 저장소에 포함되지 않습니다.
> 위 import 과정에서 IDE가 자동으로 재생성하므로 정상입니다.

### 커맨드라인(Maven)
```bash
cd jaringochi
mvn clean package
```
→ `target/jaringochi.war` 가 생성됩니다.

---

## 5. 실행

- **STS**: 프로젝트 우클릭 ▸ `Run As ▸ Run on Server` ▸ Tomcat 11 선택
- **수동 배포**: `target/jaringochi.war` 를 Tomcat의 `webapps/` 폴더에 복사
- **접속 주소**: <http://localhost:8080/jaringochi/>
  - 로그인하지 않은 상태면 자동으로 로그인 화면으로 이동합니다.

---

## 6. 테스트 로그인 계정

`data.sql` 적용 시 아래 계정이 생성됩니다.

| 아이디 | 비밀번호 | 닉네임 |
|---|---|---|
| `test1` | `1234` | 절약왕김굴비 |
| `test2` | `1234` | 알뜰한이고치 |

---

## 7. 자주 발생하는 문제

| 증상 | 원인 / 해결 |
|---|---|
| 실행 시 DB 연결 오류 | MySQL 미실행, 또는 `DBUtil.java`의 계정/비밀번호 불일치 → 3번 항목 확인 |
| 한글이 깨져 보임 | DB 생성 시 `utf8mb4` 사용(스키마에 포함됨), 톰캣/브라우저 인코딩 UTF-8 확인 |
| 404 / 페이지 없음 | 컨텍스트 경로 확인 (WAR `finalName`이 `jaringochi`이므로 `/jaringochi/`) |
| 의상 이미지가 깨짐 | `gulbi_clothes` 의상 이미지 파일은 미포함. 기능 동작에는 영향 없음 |
| Servlet 관련 클래스 오류 | Tomcat 버전 확인(Servlet 6.1 → Tomcat 11), JDK 17 이상 사용 |

---

# [2] 자린고치 프로젝트 설명 (README)

## 1. 프로젝트 소개
자린고치는 절약 습관 형성을 돕는 게이미피케이션 기반 가계부 웹 서비스입니다.  
일반적인 수입/지출 기록 기능에 더해, 주간 예산 중심의 관리, 예정 지출 계산, 주/월간 레포트, 굴비 캐릭터 육성, 메이트 경쟁, 커뮤니티 기능을 제공합니다.

사용자는 자신의 소비/저축 습관에 따라 성장하는 굴비 캐릭터를 키우며, 예산을 지키고 꾸준히 기록하는 행동을 지속할 수 있습니다.

---

## 2. 프로젝트 목표
- 사용자가 **주간 예산 기준**으로 소비를 더 구체적으로 관리할 수 있도록 돕는다.
- 단순한 가계부 기록을 넘어, **캐릭터 육성 요소**를 통해 절약 습관을 지속하게 만든다.
- 예산 대비 현재 소비 상태를 직관적으로 보여주고, 예정 지출까지 반영하여 실질적인 운용 가능 금액을 확인할 수 있도록 한다.
- 월간 레포트와 굴비와의 상호작용을 통해 사용자의 회고와 다음 계획 수립을 돕는다.

---

## 3. 핵심 기능
### 3.1 가계부 기능
- 수입 등록 / 수정 / 삭제
- 지출 등록 / 수정 / 삭제
- 거래 내역 조회
- 카테고리 관리
- 수입/지출 통계 그래프 조회

### 3.2 예산 관리 기능
- 주간 예산 또는 월간 예산 입력
- 한쪽 입력 시 다른 쪽 자동 계산
- 주간 예산 대비 현재 지출 비율 계산
- 지출 시 굴비 캐릭터 알림 제공
- 예산 초과 시 100% 이상 퍼센트 표시

### 3.3 예정 지출 기능
- 예정 지출 직접 등록
- 과거 소비 내역 기반 예정 지출 자동 추정
- 운용 가능 금액 계산  
  - 운용 가능 금액 = 수입 - (지출 + 예정 지출)

### 3.4 레포트 기능
- 주간 레포트 제공
- 월간 레포트 제공
- 이전 기간과 소비 비교
- 월간 레포트 하단에서 사용자가 굴비에게 다짐 작성 가능
- 굴비의 성격 기반 응답 생성
- 대화 로그 저장 및 조회

### 3.5 굴비 캐릭터 기능
- 초기 굴비 3종 중 1종 선택
- 굴비 성격 설정
- 소비/저축 성과에 따라 굴비 상태 변화
- 의상 획득
- 새끼 굴비 획득
- 옷장 확인 및 의상 적용

### 3.6 메이트/커뮤니티 기능
- 1:1 메이트 요청 / 수락 / 해제
- 메이트의 굴비 상태 확인
- 커뮤니티 게시글 작성
- 댓글 작성
- 좋아요 기능
- 주간/월간 베스트 고치 확인

---

## 4. 보상 및 성장 규칙
### 4.1 성공 조건
보상은 아래 두 조건에 의해 결정된다.

1. 사용자의 연속적인 기록 성공
2. 주별 예산의 ±10% 범위 내 지출 성공

### 4.2 굴비 상태 변화
- 당일 성공/실패 여부에 따라 다음날 굴비 형태가 달라진다.

### 4.3 의상 획득
- 1) 기록 성공을 한 달 동안 매일 유지
- 2) 예산 성공을 한 달 동안 유지
- 위 두 조건을 만족하면 새로운 의상 1개를 랜덤 획득

### 4.4 새끼 획득
- 첫 번째 새끼: 3개월 동안 1), 2) 성공 시 획득
- 두 번째 새끼: 6개월 동안 1), 2) 성공 시 획득
- 새끼는 최대 2마리까지 획득 가능

### 4.5 체중 변화 규칙
- 성인 굴비, 첫째 새끼, 둘째 새끼 각각 체중 1~10
- 실패 시: 성인 → 첫째 → 둘째 순으로 체중 감소
- 성공 시: 둘째 → 첫째 → 성인 순으로 체중 증가

---

## 5. 기술 스택
- Language: Java 17+
- Backend: Servlet, JSP
- WAS: Apache Tomcat
- IDE: STS (Spring Tool Suite)
- Frontend: HTML, CSS, JavaScript
- UI Library: Bootstrap
- JSTL 사용

---

## 6. 프로젝트 구조
- Controller: HTTP 요청 처리, URL 라우팅
- Service: 비즈니스 로직 및 검증 처리
- Repository(DAO): 데이터 저장/조회/수정/삭제
- DTO/Domain: 사용자, 거래내역, 예산, 굴비 등 도메인 표현
- View(JSP): 화면 렌더링
- Filter(선택): 로그인 인증, 권한 체크, 인코딩 처리

---

## 7. 주요 도메인
- User
- AccountBookEntry
- Category
- Budget
- ScheduledExpense
- WeeklyReport
- MonthlyReport
- Gulbi
- GulbiClothes
- GulbiChild
- Mate
- CommunityPost
- Comment
- Like
- DialogueLog

---

## 8. 팀 역할
- A: 추후 확정
- B: 추후 확정

---

## 9. 실행 환경
- Java 17 이상
- Apache Tomcat
- JSTL 라이브러리 포함
- Bootstrap 적용

---

## 10. 향후 문서
- WBS
- 간트 차트
- 유스케이스 다이어그램
- 화면 설계서
- 프로젝트 명세서
- 발표 PPT
