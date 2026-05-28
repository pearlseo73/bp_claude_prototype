-- ========================================
-- 자린고치(Jaringochi) 샘플 데이터
-- schema.sql 실행 후 적용
-- ========================================

USE jaringochi;

-- ----------------------------------------
-- 1. 샘플 사용자 (비밀번호: 1234)
-- ----------------------------------------
INSERT INTO users (username, password, nickname) VALUES
('test1', '1234', '절약왕김굴비'),
('test2', '1234', '알뜰한이고치');

-- ----------------------------------------
-- 2. 기본 카테고리 (사용자1)
-- ----------------------------------------
INSERT INTO categories (user_id, name, type) VALUES
-- test1 수입 카테고리
(1, '급여',   'INCOME'),
(1, '용돈',   'INCOME'),
(1, '부수입', 'INCOME'),
(1, '기타수입', 'INCOME'),
-- test1 지출 카테고리
(1, '식비',   'EXPENSE'),
(1, '교통비', 'EXPENSE'),
(1, '쇼핑',   'EXPENSE'),
(1, '주거비', 'EXPENSE'),
(1, '통신비', 'EXPENSE'),
(1, '문화생활', 'EXPENSE'),
(1, '의료비', 'EXPENSE'),
(1, '기타지출', 'EXPENSE');

-- 기본 카테고리 (사용자2)
INSERT INTO categories (user_id, name, type) VALUES
-- test2 수입 카테고리
(2, '급여',   'INCOME'),
(2, '용돈',   'INCOME'),
(2, '부수입', 'INCOME'),
(2, '기타수입', 'INCOME'),
-- test2 지출 카테고리
(2, '식비',   'EXPENSE'),
(2, '교통비', 'EXPENSE'),
(2, '쇼핑',   'EXPENSE'),
(2, '주거비', 'EXPENSE'),
(2, '통신비', 'EXPENSE'),
(2, '문화생활', 'EXPENSE'),
(2, '의료비', 'EXPENSE'),
(2, '기타지출', 'EXPENSE');

-- ----------------------------------------
-- 3. 샘플 거래 내역 (사용자1 - 2026년 5월)
-- ----------------------------------------
INSERT INTO account_book_entries (user_id, category_id, type, amount, memo, entry_date) VALUES
-- 수입
(1, 1, 'INCOME',  3000000, '5월 급여',           '2026-05-01'),
(1, 2, 'INCOME',   200000, '부모님 용돈',        '2026-05-05'),
-- 지출
(1, 5, 'EXPENSE',   12000, '점심 식사',          '2026-05-19'),
(1, 5, 'EXPENSE',    8500, '편의점',              '2026-05-19'),
(1, 6, 'EXPENSE',    3200, '버스 교통비',        '2026-05-19'),
(1, 5, 'EXPENSE',   15000, '저녁 외식',          '2026-05-20'),
(1, 7, 'EXPENSE',   35000, '온라인 쇼핑',        '2026-05-20'),
(1, 6, 'EXPENSE',    6400, '지하철 교통비',      '2026-05-21'),
(1, 5, 'EXPENSE',    9800, '카페',                '2026-05-21'),
(1, 10, 'EXPENSE',  15000, '영화 관람',          '2026-05-22'),
(1, 5, 'EXPENSE',   22000, '치킨 배달',          '2026-05-22'),
(1, 5, 'EXPENSE',    7500, '아침 빵집',          '2026-05-23'),
(1, 6, 'EXPENSE',    3200, '버스 교통비',        '2026-05-23'),
(1, 5, 'EXPENSE',   11000, '점심 식사',          '2026-05-24'),
(1, 7, 'EXPENSE',   28000, '생활용품 구매',      '2026-05-24'),
(1, 5, 'EXPENSE',   18000, '삼겹살 저녁',        '2026-05-25'),
(1, 5, 'EXPENSE',    6000, '커피',                '2026-05-26'),
(1, 6, 'EXPENSE',    3200, '버스 교통비',        '2026-05-26'),
(1, 5, 'EXPENSE',   13500, '점심 식사',          '2026-05-27'),
(1, 11, 'EXPENSE',  5000, '약국',                '2026-05-27');

-- 샘플 거래 내역 (사용자2)
INSERT INTO account_book_entries (user_id, category_id, type, amount, memo, entry_date) VALUES
(2, 13, 'INCOME',  2500000, '5월 급여',          '2026-05-01'),
(2, 17, 'EXPENSE',   10000, '점심 도시락',       '2026-05-20'),
(2, 18, 'EXPENSE',    2800, '버스',               '2026-05-20'),
(2, 17, 'EXPENSE',   25000, '주말 외식',         '2026-05-24'),
(2, 19, 'EXPENSE',   15000, '옷 구매',           '2026-05-25');

-- ----------------------------------------
-- 4. 예산 설정
-- ----------------------------------------
INSERT INTO budgets (user_id, budget_type, weekly_amount, monthly_amount, start_date) VALUES
(1, 'WEEKLY',  150000, 652000, '2026-05-01'),
(2, 'MONTHLY', 125000, 543000, '2026-05-01');

-- ----------------------------------------
-- 5. 예정 지출
-- ----------------------------------------
INSERT INTO scheduled_expenses (user_id, category_id, name, amount, due_date, status) VALUES
(1, 8,  '월세',         500000, '2026-06-01', 'PENDING'),
(1, 9,  '통신비',        55000, '2026-06-05', 'PENDING'),
(1, 12, '보험료',       120000, '2026-06-10', 'PENDING'),
(2, 20, '월세',         450000, '2026-06-01', 'PENDING');

-- ----------------------------------------
-- 6. 굴비 의상 마스터 데이터
-- ----------------------------------------
INSERT INTO gulbi_clothes (name, image_path) VALUES
('기본 옷',         'images/gulbi/clothes/default.png'),
('선글라스',        'images/gulbi/clothes/sunglasses.png'),
('산타 모자',       'images/gulbi/clothes/santa_hat.png'),
('왕관',           'images/gulbi/clothes/crown.png'),
('리본',           'images/gulbi/clothes/ribbon.png'),
('넥타이',         'images/gulbi/clothes/necktie.png'),
('운동복',         'images/gulbi/clothes/sportswear.png'),
('한복',           'images/gulbi/clothes/hanbok.png'),
('마법사 모자',     'images/gulbi/clothes/wizard_hat.png'),
('꽃 화관',        'images/gulbi/clothes/flower_crown.png');

-- ----------------------------------------
-- 7. 굴비 캐릭터 데이터
-- ----------------------------------------
INSERT INTO gulbis (user_id, gulbi_type, personality, weight, streak_record, streak_budget) VALUES
(1, 'TYPE_1', '다정하고 응원해주는 성격. 항상 칭찬을 아끼지 않는 따뜻한 굴비.', 7, 5, 2),
(2, 'TYPE_2', '까칠하지만 속은 따뜻한 츤데레 성격. 직설적이지만 진심이 담긴 굴비.', 5, 3, 1);

-- ----------------------------------------
-- 8. 사용자 보유 의상
-- ----------------------------------------
INSERT INTO user_gulbi_clothes (user_id, clothes_id) VALUES
(1, 1),
(1, 2),
(1, 5);

-- ----------------------------------------
-- 9. 새끼 굴비 (사용자1만 첫째 보유)
-- ----------------------------------------
INSERT INTO gulbi_children (user_id, child_order, weight) VALUES
(1, 1, 4);

-- ----------------------------------------
-- 10. 주간 레포트 샘플
-- ----------------------------------------
INSERT INTO weekly_reports (user_id, week_start, week_end, total_income, total_expense, budget_amount, budget_success, feedback_message) VALUES
(1, '2026-05-12', '2026-05-18', 0, 135000, 150000, TRUE,  '이번 주 예산을 잘 지켰어요! 굴비가 기뻐하고 있어요 🐟'),
(1, '2026-05-19', '2026-05-25', 200000, 168600, 150000, FALSE, '이번 주는 예산을 조금 초과했어요. 다음 주엔 더 아껴보아요! 💪');

-- ----------------------------------------
-- 11. 월간 레포트 샘플
-- ----------------------------------------
INSERT INTO monthly_reports (user_id, year, month, total_income, total_expense, feedback_message) VALUES
(1, 2026, 4, 3200000, 580000, '4월에는 꽤 절약을 잘 했어요! 굴비가 살이 올랐답니다 😊'),
(1, 2026, 5, 3200000, 0, NULL);

-- ----------------------------------------
-- 12. 대화 로그 샘플
-- ----------------------------------------
INSERT INTO dialogue_logs (user_id, monthly_report_id, user_message, gulbi_reply) VALUES
(1, 1, '이번 달도 열심히 절약할게! 굴비야 응원해줘!',
 '너무 잘하고 있어! 이번 달도 함께 아껴보자~ 내가 응원할게! 🐟✨');

-- ----------------------------------------
-- 13. 메이트 관계
-- ----------------------------------------
INSERT INTO mates (user1_id, user2_id) VALUES
(1, 2);

-- ----------------------------------------
-- 14. 커뮤니티 게시글
-- ----------------------------------------
INSERT INTO community_posts (user_id, title, content) VALUES
(1, '이번 달 예산 성공했어요!', '드디어 한 달 동안 예산을 지켰습니다! 굴비가 살이 쪘어요 ㅎㅎ 다들 화이팅!'),
(2, '절약 팁 공유합니다', '도시락 싸가면 식비를 엄청 아낄 수 있어요. 저는 이번 달 식비 30% 절약 성공!'),
(1, '굴비한테 선글라스 입혔어요', '의상 획득했는데 선글라스가 나왔어요! 너무 귀엽다 ㅎㅎ');

-- ----------------------------------------
-- 15. 커뮤니티 댓글
-- ----------------------------------------
INSERT INTO community_comments (post_id, user_id, content) VALUES
(1, 2, '대박! 축하해요~ 저도 열심히 해야겠어요!'),
(2, 1, '오! 좋은 팁이네요. 저도 도시락 도전해볼게요!'),
(3, 2, '선글라스 굴비 보고 싶어요! 사진 올려주세요 ㅎㅎ');

-- ----------------------------------------
-- 16. 커뮤니티 좋아요
-- ----------------------------------------
INSERT INTO community_likes (post_id, user_id) VALUES
(1, 2),
(2, 1),
(3, 1),
(3, 2);
