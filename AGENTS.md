# 자린고치(Jaringochi) — AI 협업 컨텍스트 문서

> 이 파일은 AI 코딩 에이전트(Claude Code, Codex 등)와 사람 협업자가 **이 레포에서 작업을 이어가기 위한 단일 기준 문서**다.
> 중요한 결정·규칙·맥락은 모두 여기에 적고 git에 커밋한다.
> (Claude는 루트의 `CLAUDE.md`가 이 파일을 `@AGENTS.md`로 불러오고, Codex는 이 파일을 직접 읽는다.)

---

## 0. 이 문서가 존재하는 이유 (메모리 / 이식성)

- AI 에이전트의 자동 메모리(`~/.claude/...`)는 **"루트 폴더 절대경로"를 키로 저장**되며 **git에 포함되지 않는다.**
  - 폴더 이름 변경 / 다른 위치에서 clone / 다른 PC / 다른 AI 도구로 바꾸면 **자동 메모리는 따라오지 않는다.**
- 따라서 **자리·도구·사람이 바뀌어도 살아남아야 하는 맥락은 전부 이 문서(git 추적 파일)에 적는다.**
- 폴더 이름은 바뀌어도 무방하다. 원격 레포: `https://github.com/pearlseo73/bp_claude_prototype.git` (branch: `main`).
- 협업: 한 명은 **Claude Code**, 친구는 **Codex** 사용. 같은 원격 레포를 push/pull 하며 이 문서로 맥락을 공유한다.

---

## 1. 프로젝트 개요

**자린고치** — 절약 습관 형성을 돕는 **게이미피케이션 기반 가계부 웹 서비스**.
일반 가계부 + 주간 예산 관리 + 예정 지출 + 주/월간 레포트 + **굴비 캐릭터 육성** + 메이트 비교 + 커뮤니티.

핵심 컨셉: 단순 기록이 아니라 **절약 행동을 지속하게 만드는 서비스**. 사용자는 굴비 캐릭터를 선택·성격 설정하고, 기록 습관·예산 준수에 따라 굴비 외형/체중/보상이 변한다.

상세 기획은 `설계문서/` 참고:
- `프로젝트명세서.md` — 기능 요구사항(F401~F414), 우선순위
- `Claude Code용 통합 개발 프롬프트 문서.md` — DB 스키마 초안, URL/Controller 설계, 비즈니스 규칙
- `화면설계초안.md` — 화면 P01~P17, 사용자 흐름
- `README.md`, 유스케이스 다이어그램 PNG들

---

## 2. 현재 상태 (2026-05 기준)

### 2.1 기존 구현 = 마이그레이션 "원본"
- 위치: `jaringochi/` (bp2 하위 폴더, STS/Eclipse 프로젝트)
- 스택: **Servlet + JSP + Tomcat** (구 기술스택)
- 구성: Java 65개 파일, 완전한 MVC(`controller/service/repository/domain/dto/filter/util`), 14개 기능 JSP 뷰, `sql/schema.sql` + `sql/data.sql`
- 성격: **Claude가 프로토타입으로 생성한 코드.** 팀이 직접 설계한 게 아니라 기초 과정(DB·구조 설계)이 어려워 AI에 맡긴 결과물. → **기획 적합성은 아직 검증 전.**

### 2.2 하던 작업
- 이 JSP/Tomcat 프로토타입을 **Vue.js + Spring Boot 기술스택으로 마이그레이션**하려는 단계.
- 기존 DB 스키마/코드를 "원본"으로 삼아 새 스택으로 옮긴다.

---

## 3. 목표 기술스택 (마이그레이션 후)

| 영역 | 스택 |
|---|---|
| 프론트 | **Vue 3 (SFC) + Vite** + Router + Pinia + axios |
| 백엔드 | **Spring Boot + MyBatis + MySQL** + Spring Security + JWT |
| 통신 | axios ↔ REST API, **JWT 토큰 인증** |
| 구조 | **프론트/백 별도 레포(폴더) 분리** |
| 빌드 | 프론트 Vite(dev 5173), 백 Maven(8080) + Vite proxy/CORS |

### 3.1 버전 — ⚠️ 미확정 (팀/동료 상의 중)
- **옵션 A (수업 그대로):** Spring Boot **4.0.x** + Java **25**. 수업·실습과 100% 일치. 단 매우 최신이라 AI 학습데이터 밖 영역(4.0 신규 API/미묘한 동작/라이브러리 호환)은 불확실 → 그 지점은 "확실/불확실" 표시하며 공식문서 확인.
- **옵션 B (안정 우선):** Spring Boot **3.3.x** + Java **21(LTS)**. AI가 가장 안정적으로 다루는 조합. 예제·트러블슈팅 풍부.
- 핵심 구조(MyBatis+MySQL+REST)는 두 옵션 모두 코드 거의 동일. 차이는 주로 의존성 명칭(예: Boot 4.0은 `starter-web`→`starter-webmvc`, Jakarta 네임스페이스).
- **결정되면 이 항목을 갱신할 것.**

### 3.2 참고: 수업에서 확인된 실습 환경
- DB: MySQL `ssafydb`, 계정 `ssafy`/`ssafy`, 포트 3306
- MyBatis: mapper `classpath:mappers/*.xml`, `map-underscore-to-camel-case=true`, DTO alias 패키지
- Swagger: springdoc-openapi
- 패키지 컨벤션: `com.ssafy.*` (단, 본 프로젝트 기존 코드는 `com.jaringochi.*`)

---

## 4. 작업 규칙 (사용자 지시)

1. **사용자가 "작업 시작"을 명시적으로 요청하기 전에는 파일을 변경하지 않는다.** 그 전까지는 대화·읽기(분석)만.
2. 읽기(Read/분석)는 작업 시작 전에도 허용된다 (변경이 아니므로).
3. 버전 조합 등 불확실한 최신 영역은 **"확실/불확실"을 명시**하고 추측을 단정하지 않는다.
4. 이 프로젝트는 항상 **`bp2`(원격 레포 루트)를 작업 루트로 연다.** (하위 `jaringochi`를 루트로 열면 메모리/맥락이 분리됨)
5. 중요한 결정·합의는 사용자가 "메모리에 저장"이라 하지 않아도 **이 `AGENTS.md`에 반영**하고, 폴더 이동·도구 변경에도 살아남게 git에 둔다.

---

## 5. 학습 커리큘럼 타임라인 (마이그레이션 로드맵 힌트)

마이그레이션에 필요한 기술을 수업에서 곧 배움 → 배운 방식에 맞춰 구현하면 학습효과 ↑.

- **이번 주 (Front):** Router(vue-router) → StateManagement(Pinia) → 서버연동(axios) → Framework(Front) 관통 PJT
  - ※ vue-router / pinia / axios는 **아직 안 배운 상태**였고 이번 주에 배움. 현재 Vue 실습은 순수 Vue 3.5 + Vite만 사용.
- **다음 주 (활용):** Spring Security → **JWT** → Spring AI → 관통 PJT(Framework / Spring AI)
  - → **인증은 JWT 확정 방향** (수업이 Security+JWT를 가르침)
- **참고:** Spring AI(OpenAI) 수업도 있음 → 굴비 응답 생성(F410-4) 같은 AI 기능에 활용 가능성.

---

## 6. 미확정 / 결정 대기 항목

1. **버전 조합** (3.1): 옵션 A(Boot4/Java25) vs B(Boot3.3/Java21) — 동료 상의 중.
2. **작업 시작 시점:** 지금 시작(AI 리드) vs 2주 수업 후 시작(학습효과 ↑).
3. **기존 구조의 기획 적합성 검증:** `jaringochi/src`, `jaringochi/sql`이 설계문서와 맞는지 아직 정밀 대조 안 함.
4. **DB 스키마:** 기존 `jaringochi/sql/schema.sql`을 그대로 쓸지 / 보정할지.
5. (나중에) JWT 세부정책(만료시간, refresh token), 공통 Response 래퍼 포맷, 배포 방식(분리 유지 vs 단일 jar).

---

## 7. DB 스키마 초안 (설계문서 기준)

주요 테이블: `users`, `categories`, `account_book_entries`, `budgets`, `scheduled_expenses`, `gulbis`, `gulbi_clothes`, `user_gulbi_clothes`, `gulbi_children`, `weekly_reports`, `monthly_reports`, `dialogue_logs`, `mate_requests`, `mates`, `community_posts`, `community_comments`, `community_likes`.
(컬럼 상세는 `설계문서/Claude Code용 통합 개발 프롬프트 문서.md` §5 참고. 실제 DDL은 `jaringochi/sql/schema.sql`.)

---

## 8. 핵심 비즈니스 규칙 (요약)

- **예산:** 주간/월간 중 하나만 입력 → 나머지 자동 계산. **중심은 주간 예산.** 지출 시 주간예산 대비 % 표시, 초과 시 100% 초과로 표시(예 40,000원 예산에 50,000원 지출 → 125%).
- **운용 가능 금액 = 수입 − (지출 + 예정 지출).** 예정 지출은 실제 지출 발생 시 차감 후 실지출 반영.
- **굴비 성장:** 성공조건 = ①연속 기록 성공 ②주별 예산 ±10% 내 지출. 당일 성공/실패 → 다음날 외형 변화.
- **의상:** 한 달간 ①·② 모두 유지 시 랜덤 1개 획득.
- **새끼:** 3개월(첫째)/6개월(둘째) 동안 ①·② 성공, 최대 2마리.
- **체중(각 1~10):** 실패 시 성인→첫째→둘째 순 감소, 성공 시 둘째→첫째→성인 순 증가.
- **대화:** 굴비와의 대화는 **월간 레포트 하단에서만**. 사용자 다짐 입력 → 성격 기반 굴비 응답 생성·저장.

---

## 변경 이력
- 2026-05-29: 초안 작성. JSP/Tomcat → Vue+Spring Boot 마이그레이션 결정사항, 작업 규칙, 커리큘럼, 미확정 항목 정리.
