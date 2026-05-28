package com.jaringochi.service;

import com.jaringochi.domain.AccountBookEntry;
import com.jaringochi.domain.Budget;
import com.jaringochi.domain.Gulbi;
import com.jaringochi.dto.DashboardDto;
import com.jaringochi.repository.BudgetRepository;
import com.jaringochi.repository.EntryRepository;
import com.jaringochi.repository.GulbiRepository;
import com.jaringochi.repository.ScheduledExpenseRepository;
import com.jaringochi.util.DateUtil;

import java.time.LocalDate;
import java.util.List;

public class DashboardService {
    private EntryRepository entryRepository = new EntryRepository();
    private BudgetRepository budgetRepository = new BudgetRepository();
    private GulbiRepository gulbiRepository = new GulbiRepository();
    private ScheduledExpenseRepository scheduledExpenseRepository = new ScheduledExpenseRepository();

    public DashboardDto getDashboardData(long userId) {
        DashboardDto dto = new DashboardDto();
        
        // 1. 굴비 상태
        Gulbi gulbi = gulbiRepository.findByUserId(userId);
        dto.setGulbi(gulbi);
        
        // 2. 예산 및 지출 (이번 주)
        Budget budget = budgetRepository.findActiveByUserId(userId);
        LocalDate now = LocalDate.now();
        LocalDate weekStart = DateUtil.getWeekStart(now);
        LocalDate weekEnd = DateUtil.getWeekEnd(now);
        
        int weeklyBudget = budget != null ? budget.getWeeklyAmount() : 0;
        int currentExpense = entryRepository.sumByUserIdAndTypeAndDateRange(userId, "EXPENSE", weekStart, weekEnd);
        
        dto.setWeeklyBudget(weeklyBudget);
        dto.setWeeklyExpense(currentExpense);
        
        if (weeklyBudget > 0) {
            dto.setBudgetPercent((double) currentExpense / weeklyBudget * 100);
            dto.setAvailableAmount(weeklyBudget - currentExpense);
        } else {
            dto.setBudgetPercent(0);
            dto.setAvailableAmount(0);
        }
        
        // 3. 최근 거래 내역
        List<AccountBookEntry> recentEntries = entryRepository.findRecentByUserId(userId, 5);
        dto.setRecentEntries(recentEntries);
        
        // 메시지 설정 로직 (임시)
        if (dto.getBudgetPercent() > 100) {
            dto.setGulbiMessage("위험해! 예산을 초과했어... 😢");
        } else if (dto.getBudgetPercent() > 80) {
            dto.setGulbiMessage("헉! 너무 썼잖아! 😰");
        } else {
            dto.setGulbiMessage("잘 아끼고 있어! 이 속도면 성공이야! 🐟✨");
        }
        
        return dto;
    }
}
