package com.jaringochi.service;

import com.jaringochi.domain.Budget;
import com.jaringochi.repository.BudgetRepository;
import com.jaringochi.util.DateUtil;

public class BudgetService {
    private BudgetRepository budgetRepository = new BudgetRepository();

    public Budget getActiveBudget(long userId) {
        return budgetRepository.findActiveByUserId(userId);
    }

    public void saveBudget(Budget budget) {
        // Auto-calculate the other amount
        if ("WEEKLY".equals(budget.getBudgetType())) {
            budget.setMonthlyAmount(DateUtil.weeklyToMonthly(budget.getWeeklyAmount()));
        } else {
            budget.setWeeklyAmount(DateUtil.monthlyToWeekly(budget.getMonthlyAmount()));
        }
        budgetRepository.save(budget);
    }
}
