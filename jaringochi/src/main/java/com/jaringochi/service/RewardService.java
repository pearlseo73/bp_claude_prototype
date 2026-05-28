package com.jaringochi.service;

import com.jaringochi.domain.Budget;
import com.jaringochi.domain.Gulbi;
import com.jaringochi.domain.GulbiChild;
import com.jaringochi.repository.BudgetRepository;
import com.jaringochi.repository.EntryRepository;
import com.jaringochi.repository.GulbiRepository;
import com.jaringochi.util.DateUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

/**
 * 굴비 성장 및 보상 서비스 (F412).
 *
 * 성공 조건
 *  1) 기록 성공: 해당 주에 거래를 꾸준히 기록
 *  2) 예산 성공: 주별 예산 ±10% 범위 내 지출
 *
 * 체중 규칙: 성인/첫째/둘째 각 1~10
 *  - 성공 시: 둘째 → 첫째 → 성인 순으로 증가
 *  - 실패 시: 성인 → 첫째 → 둘째 순으로 감소
 *
 * 의상 획득: 기록+예산 성공을 한 달(streak) 유지 시 랜덤 1개
 * 새끼 획득: 3개월 유지 → 첫째, 6개월 유지 → 둘째 (최대 2마리)
 */
public class RewardService {
    private final GulbiRepository gulbiRepository = new GulbiRepository();
    private final EntryRepository entryRepository = new EntryRepository();
    private final BudgetRepository budgetRepository = new BudgetRepository();
    private final Random random = new Random();

    private static final int CLOTHES_MASTER_COUNT = 10; // data.sql 기준 의상 10종

    /** 예산 ±10% 범위 내 지출이면 성공 */
    public boolean isBudgetSuccess(int expense, int budget) {
        return budget > 0 && expense <= Math.round(budget * 1.1);
    }

    /**
     * 이번 주 성과를 굴비에 반영한다.
     * @return 결과 요약 메시지
     */
    public String applyWeeklyGrowth(long userId) {
        Gulbi gulbi = gulbiRepository.findByUserId(userId);
        if (gulbi == null) return "굴비가 없어요. 먼저 굴비를 선택해주세요.";

        LocalDate today = LocalDate.now();
        LocalDate weekStart = DateUtil.getWeekStart(today);
        LocalDate weekEnd = DateUtil.getWeekEnd(today);

        int expense = entryRepository.sumByUserIdAndTypeAndDateRange(userId, "EXPENSE", weekStart, weekEnd);
        Budget budget = budgetRepository.findActiveByUserId(userId);
        int weeklyBudget = (budget != null) ? budget.getWeeklyAmount() : 0;

        // 기록 성공: 이번 주 거래가 1건 이상 있으면 성공으로 간주
        boolean recordSuccess = !entryRepository.findByUserIdAndDateRange(userId, weekStart, weekEnd).isEmpty();
        boolean budgetSuccess = isBudgetSuccess(expense, weeklyBudget);
        boolean overall = recordSuccess && budgetSuccess;

        // 연속 카운트 갱신
        int streakRecord = recordSuccess ? gulbi.getStreakRecord() + 1 : 0;
        int streakBudget = budgetSuccess ? gulbi.getStreakBudget() + 1 : 0;
        gulbiRepository.updateStreaks(userId, streakRecord, streakBudget);

        // 체중 변화 (성인 굴비 기준)
        int weight = gulbi.getWeight();
        StringBuilder msg = new StringBuilder();
        if (overall) {
            if (weight < 10) {
                weight++;
                gulbiRepository.updateWeight(userId, weight);
            }
            msg.append("이번 주 기록·예산을 모두 달성했어요! 굴비 체중이 ").append(weight).append("kg가 되었어요 🐟✨");
        } else {
            if (weight > 1) {
                weight--;
                gulbiRepository.updateWeight(userId, weight);
            }
            msg.append("이번 주는 목표를 달성하지 못했어요. 굴비 체중이 ").append(weight).append("kg로 줄었어요. 다음 주엔 함께 분발해요! 💪");
        }

        // 보상 판정
        String reward = checkRewards(userId, streakRecord, streakBudget);
        if (reward != null) msg.append(" ").append(reward);

        return msg.toString();
    }

    /** 의상/새끼 획득 조건 체크 후 지급 */
    private String checkRewards(long userId, int streakRecord, int streakBudget) {
        boolean monthlyAchieved = streakRecord >= 4 && streakBudget >= 4; // 약 한 달(4주)

        // 새끼 획득 (3개월=12주, 6개월=24주)
        List<GulbiChild> children = gulbiRepository.findChildrenByUserId(userId);
        if (streakBudget >= 24 && children.size() < 2) {
            gulbiRepository.saveChild(userId, 2, 5);
            return "🎉 6개월 연속 성공! 둘째 새끼 굴비가 태어났어요!";
        }
        if (streakBudget >= 12 && children.isEmpty()) {
            gulbiRepository.saveChild(userId, 1, 5);
            return "🎉 3개월 연속 성공! 첫째 새끼 굴비가 태어났어요!";
        }

        // 의상 획득 (한 달 조건)
        if (monthlyAchieved) {
            long clothesId = 1 + random.nextInt(CLOTHES_MASTER_COUNT);
            gulbiRepository.grantClothes(userId, clothesId);
            return "🎁 한 달 목표 달성 보상으로 새 의상을 획득했어요! (옷장 확인)";
        }
        return null;
    }
}
