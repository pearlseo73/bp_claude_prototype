-- ========================================
-- 자린고치(Jaringochi) DB 스키마
-- MySQL / MariaDB 호환
-- ========================================

DROP DATABASE IF EXISTS jaringochi;
CREATE DATABASE jaringochi DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE jaringochi;

-- ----------------------------------------
-- 1. 사용자 테이블
-- ----------------------------------------
CREATE TABLE users (
    id          BIGINT       AUTO_INCREMENT PRIMARY KEY,
    username    VARCHAR(50)  NOT NULL UNIQUE COMMENT '로그인 아이디',
    password    VARCHAR(255) NOT NULL COMMENT '비밀번호',
    nickname    VARCHAR(50)  NOT NULL COMMENT '닉네임',
    created_at  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일'
) ENGINE=InnoDB COMMENT='사용자';

-- ----------------------------------------
-- 2. 카테고리 테이블
-- ----------------------------------------
CREATE TABLE categories (
    id      BIGINT      AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT      NOT NULL COMMENT '사용자 ID',
    name    VARCHAR(50) NOT NULL COMMENT '카테고리명',
    type    VARCHAR(20) NOT NULL COMMENT 'INCOME / EXPENSE',
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_categories_user (user_id)
) ENGINE=InnoDB COMMENT='카테고리';

-- ----------------------------------------
-- 3. 가계부 거래 내역 테이블
-- ----------------------------------------
CREATE TABLE account_book_entries (
    id            BIGINT       AUTO_INCREMENT PRIMARY KEY,
    user_id       BIGINT       NOT NULL COMMENT '사용자 ID',
    category_id   BIGINT       NULL COMMENT '카테고리 ID',
    type          VARCHAR(20)  NOT NULL COMMENT 'INCOME / EXPENSE',
    amount        INT          NOT NULL COMMENT '금액',
    memo          VARCHAR(255) NULL COMMENT '메모',
    entry_date    DATE         NOT NULL COMMENT '거래일',
    is_scheduled  BOOLEAN      NOT NULL DEFAULT FALSE COMMENT '예정 지출 전환 여부',
    created_at    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일',
    FOREIGN KEY (user_id)     REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE SET NULL,
    INDEX idx_entries_user_date (user_id, entry_date),
    INDEX idx_entries_type (user_id, type)
) ENGINE=InnoDB COMMENT='가계부 거래 내역';

-- ----------------------------------------
-- 4. 예산 테이블
-- ----------------------------------------
CREATE TABLE budgets (
    id              BIGINT      AUTO_INCREMENT PRIMARY KEY,
    user_id         BIGINT      NOT NULL COMMENT '사용자 ID',
    budget_type     VARCHAR(20) NOT NULL COMMENT 'WEEKLY / MONTHLY',
    weekly_amount   INT         NOT NULL DEFAULT 0 COMMENT '주간 예산',
    monthly_amount  INT         NOT NULL DEFAULT 0 COMMENT '월간 예산',
    start_date      DATE        NOT NULL COMMENT '적용 시작일',
    end_date        DATE        NULL COMMENT '적용 종료일',
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_budgets_user_date (user_id, start_date)
) ENGINE=InnoDB COMMENT='예산';

-- ----------------------------------------
-- 5. 예정 지출 테이블
-- ----------------------------------------
CREATE TABLE scheduled_expenses (
    id          BIGINT       AUTO_INCREMENT PRIMARY KEY,
    user_id     BIGINT       NOT NULL COMMENT '사용자 ID',
    category_id BIGINT       NULL COMMENT '카테고리 ID',
    name        VARCHAR(100) NOT NULL COMMENT '예정 지출명',
    amount      INT          NOT NULL COMMENT '금액',
    due_date    DATE         NOT NULL COMMENT '예정일',
    status      VARCHAR(20)  NOT NULL DEFAULT 'PENDING' COMMENT 'PENDING / COMPLETED',
    created_at  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일',
    FOREIGN KEY (user_id)     REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE SET NULL,
    INDEX idx_scheduled_user (user_id, status)
) ENGINE=InnoDB COMMENT='예정 지출';

-- ----------------------------------------
-- 6. 굴비 의상 마스터 테이블
-- ----------------------------------------
CREATE TABLE gulbi_clothes (
    id         BIGINT       AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(100) NOT NULL COMMENT '의상명',
    image_path VARCHAR(255) NOT NULL COMMENT '이미지 경로'
) ENGINE=InnoDB COMMENT='굴비 의상 마스터';

-- ----------------------------------------
-- 7. 굴비 테이블
-- ----------------------------------------
CREATE TABLE gulbis (
    id                BIGINT      AUTO_INCREMENT PRIMARY KEY,
    user_id           BIGINT      NOT NULL UNIQUE COMMENT '사용자 ID',
    gulbi_type        VARCHAR(20) NOT NULL COMMENT '기본 굴비 종류 (TYPE_1 / TYPE_2 / TYPE_3)',
    personality       TEXT        NULL COMMENT '성격 설명',
    weight            INT         NOT NULL DEFAULT 5 COMMENT '성인 굴비 체중 1~10',
    active_clothes_id BIGINT      NULL COMMENT '현재 착용 의상',
    streak_record     INT         NOT NULL DEFAULT 0 COMMENT '연속 기록 일수',
    streak_budget     INT         NOT NULL DEFAULT 0 COMMENT '연속 예산 성공 주수',
    last_record_date  DATE        NULL COMMENT '마지막 기록 날짜',
    created_at        DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일',
    FOREIGN KEY (user_id)           REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (active_clothes_id) REFERENCES gulbi_clothes(id) ON DELETE SET NULL
) ENGINE=InnoDB COMMENT='굴비 캐릭터';

-- ----------------------------------------
-- 8. 사용자 보유 의상 테이블
-- ----------------------------------------
CREATE TABLE user_gulbi_clothes (
    id          BIGINT   AUTO_INCREMENT PRIMARY KEY,
    user_id     BIGINT   NOT NULL COMMENT '사용자 ID',
    clothes_id  BIGINT   NOT NULL COMMENT '의상 ID',
    acquired_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '획득일',
    FOREIGN KEY (user_id)    REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (clothes_id) REFERENCES gulbi_clothes(id) ON DELETE CASCADE,
    UNIQUE KEY uk_user_clothes (user_id, clothes_id)
) ENGINE=InnoDB COMMENT='사용자 보유 의상';

-- ----------------------------------------
-- 9. 새끼 굴비 테이블
-- ----------------------------------------
CREATE TABLE gulbi_children (
    id          BIGINT   AUTO_INCREMENT PRIMARY KEY,
    user_id     BIGINT   NOT NULL COMMENT '사용자 ID',
    child_order INT      NOT NULL COMMENT '1=첫째 / 2=둘째',
    weight      INT      NOT NULL DEFAULT 5 COMMENT '체중 1~10',
    acquired_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '획득일',
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    UNIQUE KEY uk_user_child_order (user_id, child_order)
) ENGINE=InnoDB COMMENT='새끼 굴비';

-- ----------------------------------------
-- 10. 주간 레포트 테이블
-- ----------------------------------------
CREATE TABLE weekly_reports (
    id               BIGINT   AUTO_INCREMENT PRIMARY KEY,
    user_id          BIGINT   NOT NULL COMMENT '사용자 ID',
    week_start       DATE     NOT NULL COMMENT '시작일 (월요일)',
    week_end         DATE     NOT NULL COMMENT '종료일 (일요일)',
    total_income     INT      NOT NULL DEFAULT 0 COMMENT '총 수입',
    total_expense    INT      NOT NULL DEFAULT 0 COMMENT '총 지출',
    budget_amount    INT      NOT NULL DEFAULT 0 COMMENT '해당 주 예산',
    budget_success   BOOLEAN  NOT NULL DEFAULT FALSE COMMENT '예산 성공 여부',
    feedback_message TEXT     NULL COMMENT '피드백 문구',
    created_at       DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    UNIQUE KEY uk_weekly_user_start (user_id, week_start)
) ENGINE=InnoDB COMMENT='주간 레포트';

-- ----------------------------------------
-- 11. 월간 레포트 테이블
-- ----------------------------------------
CREATE TABLE monthly_reports (
    id               BIGINT   AUTO_INCREMENT PRIMARY KEY,
    user_id          BIGINT   NOT NULL COMMENT '사용자 ID',
    year             INT      NOT NULL COMMENT '연도',
    month            INT      NOT NULL COMMENT '월',
    total_income     INT      NOT NULL DEFAULT 0 COMMENT '총 수입',
    total_expense    INT      NOT NULL DEFAULT 0 COMMENT '총 지출',
    feedback_message TEXT     NULL COMMENT '피드백 문구',
    created_at       DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    UNIQUE KEY uk_monthly_user_ym (user_id, year, month)
) ENGINE=InnoDB COMMENT='월간 레포트';

-- ----------------------------------------
-- 12. 대화 로그 테이블
-- ----------------------------------------
CREATE TABLE dialogue_logs (
    id                BIGINT   AUTO_INCREMENT PRIMARY KEY,
    user_id           BIGINT   NOT NULL COMMENT '사용자 ID',
    monthly_report_id BIGINT   NULL COMMENT '월간 레포트 ID',
    user_message      TEXT     NOT NULL COMMENT '사용자 메시지',
    gulbi_reply       TEXT     NOT NULL COMMENT '굴비 응답',
    created_at        DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일',
    FOREIGN KEY (user_id)           REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (monthly_report_id) REFERENCES monthly_reports(id) ON DELETE SET NULL,
    INDEX idx_dialogue_user (user_id)
) ENGINE=InnoDB COMMENT='대화 로그';

-- ----------------------------------------
-- 13. 메이트 요청 테이블
-- ----------------------------------------
CREATE TABLE mate_requests (
    id           BIGINT      AUTO_INCREMENT PRIMARY KEY,
    from_user_id BIGINT      NOT NULL COMMENT '요청 보낸 사용자',
    to_user_id   BIGINT      NOT NULL COMMENT '요청 받은 사용자',
    status       VARCHAR(20) NOT NULL DEFAULT 'PENDING' COMMENT 'PENDING / ACCEPTED / REJECTED',
    created_at   DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일',
    FOREIGN KEY (from_user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (to_user_id)   REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_mate_req_to (to_user_id, status)
) ENGINE=InnoDB COMMENT='메이트 요청';

-- ----------------------------------------
-- 14. 메이트 관계 테이블
-- ----------------------------------------
CREATE TABLE mates (
    id         BIGINT   AUTO_INCREMENT PRIMARY KEY,
    user1_id   BIGINT   NOT NULL COMMENT '사용자1',
    user2_id   BIGINT   NOT NULL COMMENT '사용자2',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일',
    FOREIGN KEY (user1_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (user2_id) REFERENCES users(id) ON DELETE CASCADE,
    UNIQUE KEY uk_mates (user1_id, user2_id)
) ENGINE=InnoDB COMMENT='메이트 관계';

-- ----------------------------------------
-- 15. 커뮤니티 게시글 테이블
-- ----------------------------------------
CREATE TABLE community_posts (
    id         BIGINT       AUTO_INCREMENT PRIMARY KEY,
    user_id    BIGINT       NOT NULL COMMENT '작성자',
    title      VARCHAR(100) NOT NULL COMMENT '제목',
    content    TEXT         NOT NULL COMMENT '내용',
    created_at DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일',
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_posts_created (created_at DESC)
) ENGINE=InnoDB COMMENT='커뮤니티 게시글';

-- ----------------------------------------
-- 16. 커뮤니티 댓글 테이블
-- ----------------------------------------
CREATE TABLE community_comments (
    id         BIGINT   AUTO_INCREMENT PRIMARY KEY,
    post_id    BIGINT   NOT NULL COMMENT '게시글 ID',
    user_id    BIGINT   NOT NULL COMMENT '작성자',
    content    TEXT     NOT NULL COMMENT '댓글 내용',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일',
    FOREIGN KEY (post_id) REFERENCES community_posts(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_comments_post (post_id)
) ENGINE=InnoDB COMMENT='커뮤니티 댓글';

-- ----------------------------------------
-- 17. 커뮤니티 좋아요 테이블
-- ----------------------------------------
CREATE TABLE community_likes (
    id         BIGINT   AUTO_INCREMENT PRIMARY KEY,
    post_id    BIGINT   NOT NULL COMMENT '게시글 ID',
    user_id    BIGINT   NOT NULL COMMENT '사용자 ID',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일',
    FOREIGN KEY (post_id) REFERENCES community_posts(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    UNIQUE KEY uk_like (post_id, user_id)
) ENGINE=InnoDB COMMENT='커뮤니티 좋아요';
