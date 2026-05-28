package com.jaringochi.service;

import com.jaringochi.domain.AccountBookEntry;
import com.jaringochi.domain.Budget;
import com.jaringochi.repository.EntryRepository;
import com.jaringochi.repository.BudgetRepository;
import com.jaringochi.util.DateUtil;
import java.time.LocalDate;
import java.util.List;

public class EntryService {
    private EntryRepository entryRepository = new EntryRepository();
    private BudgetRepository budgetRepository = new BudgetRepository();

    public List<AccountBookEntry> getEntries(long userId) {
        return entryRepository.findByUserId(userId);
    }

    public List<AccountBookEntry> getRecentEntries(long userId, int limit) {
        return entryRepository.findRecentByUserId(userId, limit);
    }

    public void addEntry(AccountBookEntry entry) {
        entryRepository.save(entry);
    }

    public void deleteEntry(long id) {
        entryRepository.delete(id);
    }

    public double calculateBudgetUsage(long userId) {
        Budget budget = budgetRepository.findActiveByUserId(userId);
        if (budget == null || budget.getWeeklyAmount() == 0) return 0.0;
        
        LocalDate now = LocalDate.now();
        LocalDate weekStart = DateUtil.getWeekStart(now);
        LocalDate weekEnd = DateUtil.getWeekEnd(now);
        
        int currentExpense = entryRepository.sumByUserIdAndTypeAndDateRange(userId, "EXPENSE", weekStart, weekEnd);
        return (double) currentExpense / budget.getWeeklyAmount() * 100;
    }

    public String generateGulbiMessage(double budgetPercent, String personality) {
        if (budgetPercent > 100) {
            return "위험해! 예산을 초과했어... 다음 주엔 더 아껴보자! 😢";
        } else if (budgetPercent > 80) {
            return "헉! 너무 썼잖아! 남은 금액을 확인해봐! 😰";
        } else if (budgetPercent > 50) {
            return "슬슬 조심해야 할 때야! 남은 예산을 잘 생각해봐! 🐟";
        } else {
            return "잘 아끼고 있어! 이 속도면 이번 주도 성공이야! 🐟✨";
        }
    }
}
