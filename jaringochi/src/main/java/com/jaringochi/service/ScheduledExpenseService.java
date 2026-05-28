package com.jaringochi.service;

import com.jaringochi.domain.ScheduledExpense;
import com.jaringochi.repository.ScheduledExpenseRepository;
import java.util.List;

public class ScheduledExpenseService {
    private ScheduledExpenseRepository repo = new ScheduledExpenseRepository();

    public List<ScheduledExpense> getPendingExpenses(long userId) {
        return repo.findPendingByUserId(userId);
    }

    public void addExpense(ScheduledExpense exp) {
        repo.save(exp);
    }
    
    public void deleteExpense(long id) {
        repo.delete(id);
    }
}
