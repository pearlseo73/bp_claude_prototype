package com.jaringochi.service;

import com.jaringochi.domain.*;
import com.jaringochi.dto.MonthlyReportDto;
import com.jaringochi.dto.WeeklyReportDto;
import com.jaringochi.repository.*;
import com.jaringochi.util.DateUtil;

import java.time.LocalDate;
import java.util.List;

/**
 * 주간/월간 레포트 서비스.
 * 거래 내역과 예산을 기반으로 레포트를 실시간 집계한다.
 */
public class ReportService {
    private final EntryRepository entryRepository = new EntryRepository();
    private final BudgetRepository budgetRepository = new BudgetRepository();
    private final ReportRepository reportRepository = new ReportRepository();
    private final GulbiRepository gulbiRepository = new GulbiRepository();
    private final DialogueRepository dialogueRepository = new DialogueRepository();
    private final MateRepository mateRepository = new MateRepository();
    private final UserRepository userRepository = new UserRepository();

    // ---------- 주간 레포트 ----------
    public WeeklyReportDto getWeeklyReport(long userId) {
        LocalDate today = LocalDate.now();
        LocalDate curStart = DateUtil.getWeekStart(today);
        LocalDate curEnd = DateUtil.getWeekEnd(today);
        LocalDate prevStart = curStart.minusWeeks(1);
        LocalDate prevEnd = curEnd.minusWeeks(1);

        Budget budget = budgetRepository.findActiveByUserId(userId);
        int weeklyBudget = (budget != null) ? budget.getWeeklyAmount() : 0;

        WeeklyReport current = buildWeekly(userId, curStart, curEnd, weeklyBudget);
        WeeklyReport previous = buildWeekly(userId, prevStart, prevEnd, weeklyBudget);

        WeeklyReportDto dto = new WeeklyReportDto();
        dto.setCurrent(current);
        dto.setPrevious(previous);
        dto.setExpenseChangePercent(changePercent(previous.getTotalExpense(), current.getTotalExpense()));
        dto.setIncomeChangePercent(changePercent(previous.getTotalIncome(), current.getTotalIncome()));
        return dto;
    }

    private WeeklyReport buildWeekly(long userId, LocalDate start, LocalDate end, int weeklyBudget) {
        int income = entryRepository.sumByUserIdAndTypeAndDateRange(userId, "INCOME", start, end);
        int expense = entryRepository.sumByUserIdAndTypeAndDateRange(userId, "EXPENSE", start, end);
        boolean success = weeklyBudget > 0 && expense <= (int) Math.round(weeklyBudget * 1.1);

        WeeklyReport r = new WeeklyReport();
        r.setUserId(userId);
        r.setWeekStart(start);
        r.setWeekEnd(end);
        r.setTotalIncome(income);
        r.setTotalExpense(expense);
        r.setBudgetAmount(weeklyBudget);
        r.setBudgetSuccess(success);
        r.setFeedbackMessage(weeklyFeedback(success, weeklyBudget, expense));
        return r;
    }

    private String weeklyFeedback(boolean success, int budget, int expense) {
        if (budget <= 0) return "아직 주간 예산이 설정되지 않았어요. 예산을 설정하면 굴비가 더 잘 도와줄 수 있어요!";
        if (success) return "이번 주 예산을 잘 지켰어요! 굴비가 기뻐하고 있어요 🐟";
        int over = expense - budget;
        return "이번 주는 예산을 " + String.format("%,d", Math.max(over, 0)) + "원 초과했어요. 다음 주엔 더 아껴보아요! 💪";
    }

    // ---------- 월간 레포트 ----------
    public MonthlyReportDto getMonthlyReport(long userId, int year, int month) {
        LocalDate first = LocalDate.of(year, month, 1);
        LocalDate curStart = DateUtil.getMonthStart(first);
        LocalDate curEnd = DateUtil.getMonthEnd(first);
        LocalDate prevMonth = first.minusMonths(1);
        LocalDate prevStart = DateUtil.getMonthStart(prevMonth);
        LocalDate prevEnd = DateUtil.getMonthEnd(prevMonth);

        MonthlyReport current = buildMonthly(userId, year, month, curStart, curEnd);
        MonthlyReport previous = buildMonthly(userId, prevMonth.getYear(), prevMonth.getMonthValue(), prevStart, prevEnd);

        MonthlyReportDto dto = new MonthlyReportDto();
        dto.setCurrent(current);
        dto.setPrevious(previous);
        dto.setExpenseChangePercent(changePercent(previous.getTotalExpense(), current.getTotalExpense()));
        dto.setGulbi(gulbiRepository.findByUserId(userId));
        dto.setDialogues(dialogueRepository.findByUserId(userId));

        // 메이트 비교
        Long mateId = mateRepository.findMateUserId(userId);
        if (mateId != null) {
            User mate = userRepository.findById(mateId);
            if (mate != null) dto.setMateNickname(mate.getNickname());
            dto.setMateExpense(entryRepository.sumByUserIdAndTypeAndDateRange(mateId, "EXPENSE", curStart, curEnd));
        }
        return dto;
    }

    private MonthlyReport buildMonthly(long userId, int year, int month, LocalDate start, LocalDate end) {
        MonthlyReport saved = reportRepository.findMonthly(userId, year, month);
        int income = entryRepository.sumByUserIdAndTypeAndDateRange(userId, "INCOME", start, end);
        int expense = entryRepository.sumByUserIdAndTypeAndDateRange(userId, "EXPENSE", start, end);

        MonthlyReport r = new MonthlyReport();
        if (saved != null) {
            r.setId(saved.getId());
            r.setFeedbackMessage(saved.getFeedbackMessage());
        }
        r.setUserId(userId);
        r.setYear(year);
        r.setMonth(month);
        r.setTotalIncome(income);
        r.setTotalExpense(expense);
        if (r.getFeedbackMessage() == null) {
            r.setFeedbackMessage(monthlyFeedback(income, expense));
        }
        return r;
    }

    private String monthlyFeedback(int income, int expense) {
        int saving = income - expense;
        if (income == 0 && expense == 0) return "이번 달은 아직 기록이 없어요. 첫 거래를 등록해볼까요?";
        if (saving > 0) return "이번 달은 " + String.format("%,d", saving) + "원을 아꼈어요! 굴비가 살이 올랐답니다 😊";
        return "이번 달은 지출이 수입보다 많았어요. 다음 달엔 굴비와 함께 더 아껴봐요! 🐟";
    }

    /** 월간 레포트 ID 조회 (대화 저장용, 없으면 null) */
    public Long getMonthlyReportId(long userId, int year, int month) {
        MonthlyReport saved = reportRepository.findMonthly(userId, year, month);
        return saved != null ? saved.getId() : null;
    }

    private double changePercent(int prev, int cur) {
        if (prev == 0) return cur == 0 ? 0.0 : 100.0;
        return Math.round((cur - prev) * 1000.0 / prev) / 10.0;
    }
}
