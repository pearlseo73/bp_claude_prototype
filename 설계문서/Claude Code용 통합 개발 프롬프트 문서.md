# Claude Code용 통합 개발 프롬프트 문서  
## 프로젝트명: 자린고치

이 문서는 Claude Code가 **Servlet + JSP 기반의 웹 애플리케이션**인 **자린고치**를 전체 구현할 수 있도록 하기 위한 통합 개발 지시서이다.  
프로젝트의 기능, 구조, DB 초안, URL 설계, Controller 설계, 메뉴 구조, 폴더 구조, 구현 우선순위를 모두 포함한다.

---

# 1. 프로젝트 개요

## 1.1 서비스 설명
자린고치는 **절약 습관 형성을 돕는 게이미피케이션 기반 가계부 웹 서비스**이다.  
일반적인 가계부 기능에 더해 다음 기능을 제공한다.

- 주간 예산 중심의 소비 관리
- 지출 시 예산 대비 퍼센트 및 굴비 캐릭터 알림
- 예정 지출 관리
- 주간/월간 레포트
- 굴비 캐릭터 육성
- 의상 획득, 새끼 굴비 획득
- 메이트와의 1:1 비교
- 커뮤니티 게시글/댓글/좋아요

---

## 1.2 핵심 컨셉
- 일반적인 “가계부”가 아니라 **절약 행동을 지속하게 만드는 서비스**
- 사용자는 자신의 **굴비 캐릭터**를 선택하고, 성격을 설정한다.
- 사용자의 기록 습관과 예산 준수 여부에 따라 **굴비의 외형/체중/보상 상태**가 달라진다.
- 월간 레포트에서 사용자는 굴비에게 다짐을 남기고, 굴비는 성격 기반 말투로 응답한다.

---

## 1.3 구현 환경
- Java 17+
- Servlet / JSP
- Apache Tomcat
- JSTL
- Bootstrap
- HTML / CSS / JavaScript
- DB: MySQL 또는 MariaDB 기준으로 설계
- IDE: STS 또는 IntelliJ 사용 가능

---

# 2. 개발 목표

Claude Code는 아래 기준으로 프로젝트를 구현한다.

1. **MVC 패턴 준수**
2. **Servlet + JSP 기반**
3. **Controller / Service / Repository / Domain 구조 분리**
4. **DB 연동**
5. **로그인 사용자 기준 기능 동작**
6. **핵심 기능 우선 구현 후 확장 기능 추가**
7. **화면은 Bootstrap 기반으로 깔끔하게 구성**
8. **URL 설계와 화면 흐름이 일관되도록 구현**
9. **향후 확장 가능한 구조로 작성**
10. **주석과 폴더 구조를 명확하게 유지**

---

# 3. 기능 범위

## 3.1 필수 기능
- 회원가입 / 로그인 / 로그아웃
- 수입 등록 / 수정 / 삭제 / 조회
- 지출 등록 / 수정 / 삭제 / 조회
- 카테고리 관리
- 주간 또는 월간 예산 설정
- 입력하지 않은 예산 자동 계산
- 지출 시 예산 대비 사용 비율 계산
- 예정 지출 등록
- 운용 가능 금액 계산
- 대시보드
- 주간 레포트
- 월간 레포트
- 월간 다짐 작성 및 굴비 응답 저장
- 굴비 3종 중 선택
- 굴비 성격 설정
- 굴비 상태 확인
- 커뮤니티 게시글/댓글/좋아요
- 메이트 요청/수락/해제
- 메이트 굴비 상태 조회

## 3.2 선택 / 확장 기능
- 예정 지출 자동 추정
- 통계 그래프
- 의상 적용
- 의상 획득
- 새끼 굴비 획득
- 대화 로그 조회
- 베스트 고치 조회
- 캘린더 형태의 일별 수입/지출 조회

---

# 4. 핵심 비즈니스 규칙

## 4.1 예산 규칙
- 사용자는 **주간 예산 또는 월간 예산 중 하나만 입력**할 수 있다.
- 시스템은 입력되지 않은 다른 예산 값을 자동 계산하여 표시한다.
- 서비스의 중심은 **주간 예산 관리**이다.
- 지출 등록 시 현재 주간 예산 대비 사용 퍼센트를 계산한다.
- 예산 초과 시 100%를 넘는 퍼센트로 표시한다.
  - 예: 예산 40,000원 / 지출 50,000원 → 125%

## 4.2 예정 지출 규칙
- 사용자는 예정 지출을 직접 등록할 수 있다.
- 실제 지출 발생 시 예정 지출 금액에서 차감하고 실지출로 반영한다.
- 운용 가능 금액 계산식:
  - `운용 가능 금액 = 수입 - (지출 + 예정 지출)`

## 4.3 굴비 성장 규칙
보상은 아래 두 조건에 의해 판단된다.

### 성공 조건
1. 사용자의 연속적인 기록 성공
2. 주별 예산의 ±10% 범위 내 지출 시 성공

### 굴비 외형 변화
- 당일 성공/실패 여부에 따라 다음날 굴비 상태가 달라진다.

### 의상 획득
- 1) 기록 성공을 한 달 동안 매일 유지
- 2) 예산 성공을 한 달 동안 유지
- 위 두 조건 모두 만족 시 새 의상 1개 랜덤 획득

### 새끼 획득
- 첫 번째 새끼: 3개월 동안 1), 2) 성공
- 두 번째 새끼: 6개월 동안 1), 2) 성공
- 최대 2마리까지

### 체중 규칙
- 성인 굴비, 첫째 새끼, 둘째 새끼 각각 체중 1~10
- 실패 시: 성인 → 첫째 → 둘째 순으로 감소
- 성공 시: 둘째 → 첫째 → 성인 순으로 증가

## 4.4 대화 규칙
- 굴비와의 대화는 **월간 레포트 하단에서만 가능**
- 사용자가 굴비에게 다짐을 입력하면 굴비 응답을 생성해 저장한다.
- 굴비 응답은 사용자가 설정한 성격 기반 톤을 유지해야 한다.

---

# 5. DB 설계서 초안

## 5.1 주요 테이블 목록
- users
- categories
- account_book_entries
- budgets
- scheduled_expenses
- gulbis
- gulbi_clothes
- user_gulbi_clothes
- gulbi_children
- weekly_reports
- monthly_reports
- dialogue_logs
- mate_requests
- mates
- community_posts
- community_comments
- community_likes

---

## 5.2 테이블 정의 초안

### users
| 컬럼명 | 타입 | 설명 |
|---|---|---|
| id | BIGINT PK AI | 사용자 ID |
| username | VARCHAR(50) UNIQUE | 로그인 아이디 |
| password | VARCHAR(255) | 비밀번호 |
| nickname | VARCHAR(50) | 닉네임 |
| created_at | DATETIME | 생성일 |

---

### categories
| 컬럼명 | 타입 | 설명 |
|---|---|---|
| id | BIGINT PK AI | 카테고리 ID |
| user_id | BIGINT FK | 사용자 ID |
| name | VARCHAR(50) | 카테고리명 |
| type | VARCHAR(20) | INCOME / EXPENSE |

---

### account_book_entries
| 컬럼명 | 타입 | 설명 |
|---|---|---|
| id | BIGINT PK AI | 거래 ID |
| user_id | BIGINT FK | 사용자 ID |
| category_id | BIGINT FK | 카테고리 ID |
| type | VARCHAR(20) | INCOME / EXPENSE |
| amount | INT | 금액 |
| memo | VARCHAR(255) | 메모 |
| entry_date | DATE | 거래일 |
| is_scheduled | BOOLEAN | 예정 지출 전환 여부 |
| created_at | DATETIME | 생성일 |

---

### budgets
| 컬럼명 | 타입 | 설명 |
|---|---|---|
| id | BIGINT PK AI | 예산 ID |
| user_id | BIGINT FK | 사용자 ID |
| budget_type | VARCHAR(20) | WEEKLY / MONTHLY |
| weekly_amount | INT | 주간 예산 |
| monthly_amount | INT | 월간 예산 |
| start_date | DATE | 적용 시작일 |
| end_date | DATE | 적용 종료일 |

---

### scheduled_expenses
| 컬럼명 | 타입 | 설명 |
|---|---|---|
| id | BIGINT PK AI | 예정 지출 ID |
| user_id | BIGINT FK | 사용자 ID |
| category_id | BIGINT FK | 카테고리 ID |
| name | VARCHAR(100) | 예정 지출명 |
| amount | INT | 금액 |
| due_date | DATE | 예정일 |
| status | VARCHAR(20) | PENDING / COMPLETED |
| created_at | DATETIME | 생성일 |

---

### gulbis
| 컬럼명 | 타입 | 설명 |
|---|---|---|
| id | BIGINT PK AI | 굴비 ID |
| user_id | BIGINT FK UNIQUE | 사용자 ID |
| gulbi_type | VARCHAR(20) | 기본 굴비 종류 1~3 |
| personality | TEXT | 성격 설명 |
| weight | INT | 성인 굴비 체중 1~10 |
| active_clothes_id | BIGINT NULL | 현재 착용 의상 |
| created_at | DATETIME | 생성일 |

---

### gulbi_clothes
| 컬럼명 | 타입 | 설명 |
|---|---|---|
| id | BIGINT PK AI | 의상 ID |
| name | VARCHAR(100) | 의상명 |
| image_path | VARCHAR(255) | 이미지 경로 |

---

### user_gulbi_clothes
| 컬럼명 | 타입 | 설명 |
|---|---|---|
| id | BIGINT PK AI | 보유 의상 ID |
| user_id | BIGINT FK | 사용자 ID |
| clothes_id | BIGINT FK | 의상 ID |
| acquired_at | DATETIME | 획득일 |

---

### gulbi_children
| 컬럼명 | 타입 | 설명 |
|---|---|---|
| id | BIGINT PK AI | 새끼 ID |
| user_id | BIGINT FK | 사용자 ID |
| child_order | INT | 1 또는 2 |
| weight | INT | 체중 1~10 |
| acquired_at | DATETIME | 획득일 |

---

### weekly_reports
| 컬럼명 | 타입 | 설명 |
|---|---|---|
| id | BIGINT PK AI | 주간 레포트 ID |
| user_id | BIGINT FK | 사용자 ID |
| week_start | DATE | 시작일 |
| week_end | DATE | 종료일 |
| total_income | INT | 총 수입 |
| total_expense | INT | 총 지출 |
| feedback_message | TEXT | 피드백 문구 |

---

### monthly_reports
| 컬럼명 | 타입 | 설명 |
|---|---|---|
| id | BIGINT PK AI | 월간 레포트 ID |
| user_id | BIGINT FK | 사용자 ID |
| year | INT | 연도 |
| month | INT | 월 |
| total_income | INT | 총 수입 |
| total_expense | INT | 총 지출 |
| feedback_message | TEXT | 피드백 문구 |

---

### dialogue_logs
| 컬럼명 | 타입 | 설명 |
|---|---|---|
| id | BIGINT PK AI | 대화 로그 ID |
| user_id | BIGINT FK | 사용자 ID |
| monthly_report_id | BIGINT FK | 월간 레포트 ID |
| user_message | TEXT | 사용자 메시지 |
| gulbi_reply | TEXT | 굴비 응답 |
| created_at | DATETIME | 생성일 |

---

### mate_requests
| 컬럼명 | 타입 | 설명 |
|---|---|---|
| id | BIGINT PK AI | 요청 ID |
| from_user_id | BIGINT FK | 요청 보낸 사용자 |
| to_user_id | BIGINT FK | 요청 받은 사용자 |
| status | VARCHAR(20) | PENDING / ACCEPTED / REJECTED |
| created_at | DATETIME | 생성일 |

---

### mates
| 컬럼명 | 타입 | 설명 |
|---|---|---|
| id | BIGINT PK AI | 메이트 관계 ID |
| user1_id | BIGINT FK | 사용자1 |
| user2_id | BIGINT FK | 사용자2 |
| created_at | DATETIME | 생성일 |

---

### community_posts
| 컬럼명 | 타입 | 설명 |
|---|---|---|
| id | BIGINT PK AI | 게시글 ID |
| user_id | BIGINT FK | 작성자 |
| title | VARCHAR(100) | 제목 |
| content | TEXT | 내용 |
| created_at | DATETIME | 생성일 |

---

### community_comments
| 컬럼명 | 타입 | 설명 |
|---|---|---|
| id | BIGINT PK AI | 댓글 ID |
| post_id | BIGINT FK | 게시글 ID |
| user_id | BIGINT FK | 작성자 |
| content | TEXT | 댓글 내용 |
| created_at | DATETIME | 생성일 |

---

### community_likes
| 컬럼명 | 타입 | 설명 |
|---|---|---|
| id | BIGINT PK AI | 좋아요 ID |
| post_id | BIGINT FK | 게시글 ID |
| user_id | BIGINT FK | 사용자 ID |
| created_at | DATETIME | 생성일 |

---

# 6. URL 목록 / Controller 설계서

## 6.1 인증
| URL | Method | Controller | 설명 |
|---|---|---|---|
| /auth/login | GET | AuthController | 로그인 화면 |
| /auth/login | POST | AuthController | 로그인 처리 |
| /auth/signup | GET | AuthController | 회원가입 화면 |
| /auth/signup | POST | AuthController | 회원가입 처리 |
| /auth/logout | POST | AuthController | 로그아웃 |

## 6.2 대시보드
| URL | Method | Controller | 설명 |
|---|---|---|---|
| /dashboard | GET | DashboardController | 메인 대시보드 |

## 6.3 거래 관리
| URL | Method | Controller | 설명 |
|---|---|---|---|
| /entries | GET | EntryController | 거래 목록 조회 |
| /entries/new | GET | EntryController | 거래 등록 화면 |
| /entries | POST | EntryController | 거래 등록 |
| /entries/edit | GET | EntryController | 거래 수정 화면 |
| /entries/update | POST | EntryController | 거래 수정 |
| /entries/delete | POST | EntryController | 거래 삭제 |

## 6.4 카테고리
| URL | Method | Controller | 설명 |
|---|---|---|---|
| /categories | GET | CategoryController | 카테고리 목록 |
| /categories/new | POST | CategoryController | 카테고리 등록 |
| /categories/update | POST | CategoryController | 카테고리 수정 |
| /categories/delete | POST | CategoryController | 카테고리 삭제 |

## 6.5 예산
| URL | Method | Controller | 설명 |
|---|---|---|---|
| /budgets | GET | BudgetController | 예산 조회/설정 화면 |
| /budgets | POST | BudgetController | 예산 저장 |
| /budgets/summary | GET | BudgetController | 예산 요약 조회 |

## 6.6 예정 지출
| URL | Method | Controller | 설명 |
|---|---|---|---|
| /scheduled-expenses | GET | ScheduledExpenseController | 예정 지출 목록 |
| /scheduled-expenses | POST | ScheduledExpenseController | 예정 지출 등록 |
| /scheduled-expenses/delete | POST | ScheduledExpenseController | 예정 지출 삭제 |

## 6.7 레포트
| URL | Method | Controller | 설명 |
|---|---|---|---|
| /reports/weekly | GET | ReportController | 주간 레포트 |
| /reports/monthly | GET | ReportController | 월간 레포트 |
| /reports/monthly/message | POST | ReportController | 월간 다짐 저장 및 굴비 응답 |
| /dialogues | GET | DialogueController | 대화 로그 조회 |

## 6.8 굴비
| URL | Method | Controller | 설명 |
|---|---|---|---|
| /gulbi/select | GET | GulbiController | 초기 굴비 선택 화면 |
| /gulbi/select | POST | GulbiController | 초기 굴비 선택 처리 |
| /gulbi/personality | POST | GulbiController | 성격 설정 |
| /gulbi | GET | GulbiController | 굴비 상태 조회 |
| /gulbi/closet | GET | GulbiController | 옷장 조회 |
| /gulbi/closet/apply | POST | GulbiController | 의상 적용 |
| /gulbi/children | GET | GulbiController | 새끼 상태 조회 |

## 6.9 메이트
| URL | Method | Controller | 설명 |
|---|---|---|---|
| /mates | GET | MateController | 메이트 화면 |
| /mates/request | POST | MateController | 메이트 요청 |
| /mates/accept | POST | MateController | 메이트 수락 |
| /mates/remove | POST | MateController | 메이트 해제 |
| /mates/view | GET | MateController | 옆집 굴비 조회 |

## 6.10 커뮤니티
| URL | Method | Controller | 설명 |
|---|---|---|---|
| /community | GET | CommunityController | 게시글 목록 |
| /community/new | GET | CommunityController | 게시글 작성 화면 |
| /community | POST | CommunityController | 게시글 작성 |
| /community/view | GET | CommunityController | 게시글 상세 |
| /community/comment | POST | CommunityController | 댓글 작성 |
| /community/like | POST | CommunityController | 좋아요 |
| /community/best | GET | CommunityController | 베스트 고치 조회 |

## 6.11 캘린더
| URL | Method | Controller | 설명 |
|---|---|---|---|
| /calendar | GET | CalendarController | 월간 캘린더 조회 |
| /calendar/day | GET | CalendarController | 특정 날짜 상세 거래 조회 |

---

# 7. Controller 구현 지침

## 공통 규칙
- 모든 Controller는 Servlet으로 구현
- `doGet`, `doPost`를 명확히 분리
- 로그인 사용자 정보는 세션에서 조회
- Service 호출 후 JSP로 forward
- 수정/저장/삭제 후에는 redirect
- validation 실패 시 에러 메시지와 입력값 유지

## Controller별 역할
- AuthController: 로그인/회원가입/로그아웃
- DashboardController: 메인 요약 데이터 제공
- EntryController: 거래 CRUD
- CategoryController: 카테고리 관리
- BudgetController: 예산 설정 및 요약
- ScheduledExpenseController: 예정 지출 관리
- ReportController: 주간/월간 레포트 및 굴비 메시지 처리
- DialogueController: 대화 로그 조회
- GulbiController: 굴비 선택, 성격, 상태, 옷장, 새끼
- MateController: 메이트 요청/수락/해제/비교
- CommunityController: 게시글, 댓글, 좋아요, 베스트
- CalendarController: 월간 캘린더 및 날짜별 거래 조회

---

# 8. 화면 목록을 바탕으로 한 메뉴 구조도

## 상단 네비게이션
- 대시보드
- 가계부
  - 거래 등록
  - 거래 내역
  - 카테고리 관리
  - 캘린더 보기
- 예산
  - 예산 설정
  - 예정 지출
- 레포트
  - 주간 레포트
  - 월간 레포트
  - 대화 로그
- 내 굴비
  - 굴비 상태
  - 옷장
  - 새끼 굴비
- 메이트
- 커뮤니티
- 로그아웃

## 메인 대시보드에서 바로가기
- 오늘 지출 등록
- 거래 내역 보기
- 예산 설정
- 월간 레포트 보기
- 굴비 상태 보기
- 메이트 비교
- 커뮤니티 보기

---

# 9. 프로젝트 폴더 구조 초안

```text
jaringochi/
 ┣ src/main/java/
 ┃ ┣ controller/
 ┃ ┃ ┣ AuthController.java
 ┃ ┃ ┣ DashboardController.java
 ┃ ┃ ┣ EntryController.java
 ┃ ┃ ┣ CategoryController.java
 ┃ ┃ ┣ BudgetController.java
 ┃ ┃ ┣ ScheduledExpenseController.java
 ┃ ┃ ┣ ReportController.java
 ┃ ┃ ┣ DialogueController.java
 ┃ ┃ ┣ GulbiController.java
 ┃ ┃ ┣ MateController.java
 ┃ ┃ ┣ CommunityController.java
 ┃ ┃ ┗ CalendarController.java
 ┃ ┣ service/
 ┃ ┃ ┣ AuthService.java
 ┃ ┃ ┣ DashboardService.java
 ┃ ┃ ┣ EntryService.java
 ┃ ┃ ┣ CategoryService.java
 ┃ ┃ ┣ BudgetService.java
 ┃ ┃ ┣ ScheduledExpenseService.java
 ┃ ┃ ┣ ReportService.java
 ┃ ┃ ┣ DialogueService.java
 ┃ ┃ ┣ GulbiService.java
 ┃ ┃ ┣ RewardService.java
 ┃ ┃ ┣ MateService.java
 ┃ ┃ ┣ CommunityService.java
 ┃ ┃ ┗ CalendarService.java
 ┃ ┣ repository/
 ┃ ┃ ┣ UserRepository.java
 ┃ ┃ ┣ EntryRepository.java
 ┃ ┃ ┣ CategoryRepository.java
 ┃ ┃ ┣ BudgetRepository.java
 ┃ ┃ ┣ ScheduledExpenseRepository.java
 ┃ ┃ ┣ ReportRepository.java
 ┃ ┃ ┣ DialogueRepository.java
 ┃ ┃ ┣ GulbiRepository.java
 ┃ ┃ ┣ MateRepository.java
 ┃ ┃ ┣ CommunityRepository.java
 ┃ ┃ ┗ CalendarRepository.java
 ┃ ┣ domain/
 ┃ ┃ ┣ User.java
 ┃ ┃ ┣ Category.java
 ┃ ┃ ┣ AccountBookEntry.java
 ┃ ┃ ┣ Budget.java
 ┃ ┃ ┣ ScheduledExpense.java
 ┃ ┃ ┣ WeeklyReport.java
 ┃ ┃ ┣ MonthlyReport.java
 ┃ ┃ ┣ DialogueLog.java
 ┃ ┃ ┣ Gulbi.java
 ┃ ┃ ┣ GulbiClothes.java
 ┃ ┃ ┣ GulbiChild.java
 ┃ ┃ ┣ Mate.java
 ┃ ┃ ┣ CommunityPost.java
 ┃ ┃ ┣ CommunityComment.java
 ┃ ┃ ┗ CommunityLike.java
 ┃ ┣ dto/
 ┃ ┃ ┣ DashboardDto.java
 ┃ ┃ ┣ DailySummaryDto.java
 ┃ ┃ ┣ CalendarDayDto.java
 ┃ ┃ ┗ ...
 ┃ ┣ util/
 ┃ ┃ ┣ DBUtil.java
 ┃ ┃ ┣ DateUtil.java
 ┃ ┃ ┣ SessionUtil.java
 ┃ ┃ ┗ ValidationUtil.java
 ┃ ┗ filter/
 ┃   ┗ LoginCheckFilter.java
 ┣ src/main/webapp/
 ┃ ┣ WEB-INF/
 ┃ ┃ ┣ views/
 ┃ ┃ ┃ ┣ auth/
 ┃ ┃ ┃ ┣ dashboard/
 ┃ ┃ ┃ ┣ entry/
 ┃ ┃ ┃ ┣ category/
 ┃ ┃ ┃ ┣ budget/
 ┃ ┃ ┃ ┣ scheduledExpense/
 ┃ ┃ ┃ ┣ report/
 ┃ ┃ ┃ ┣ dialogue/
 ┃ ┃ ┃ ┣ gulbi/
 ┃ ┃ ┃ ┣ mate/
 ┃ ┃ ┃ ┣ community/
 ┃ ┃ ┃ ┣ calendar/
 ┃ ┃ ┃ ┗ common/
 ┃ ┃ ┗ lib/
 ┃ ┣ static/
 ┃ ┃ ┣ css/
 ┃ ┃ ┣ js/
 ┃ ┃ ┗ images/
 ┃ ┗ index.jsp
 ┣ sql/
 ┃ ┣ schema.sql
 ┃ ┗ data.sql
 ┣ docs/
 ┃ ┣ README.md
 ┃ ┣ 프로젝트명세서.md
 ┃ ┣ 화면설계서.md
 ┃ ┗ 유스케이스다이어그램.md
 ┗ pom.xml 또는 build 관련 파일
