package com.jaringochi.dto;

import com.jaringochi.domain.AccountBookEntry;
import com.jaringochi.domain.Gulbi;
import java.util.List;

public class DashboardDto {
    private int weeklyBudget;
    private int weeklyExpense;
    private double budgetPercent;
    private int availableAmount;
    
    private int totalIncome;
    private int totalExpense;
    private int totalScheduled;
    
    private Gulbi gulbi;
    private List<AccountBookEntry> recentEntries;
    private String gulbiMessage;

    // Getters and Setters
    public int getWeeklyBudget() { return weeklyBudget; }
    public void setWeeklyBudget(int weeklyBudget) { this.weeklyBudget = weeklyBudget; }
    public int getWeeklyExpense() { return weeklyExpense; }
    public void setWeeklyExpense(int weeklyExpense) { this.weeklyExpense = weeklyExpense; }
    public double getBudgetPercent() { return budgetPercent; }
    public void setBudgetPercent(double budgetPercent) { this.budgetPercent = budgetPercent; }
    public int getAvailableAmount() { return availableAmount; }
    public void setAvailableAmount(int availableAmount) { this.availableAmount = availableAmount; }
    public int getTotalIncome() { return totalIncome; }
    public void setTotalIncome(int totalIncome) { this.totalIncome = totalIncome; }
    public int getTotalExpense() { return totalExpense; }
    public void setTotalExpense(int totalExpense) { this.totalExpense = totalExpense; }
    public int getTotalScheduled() { return totalScheduled; }
    public void setTotalScheduled(int totalScheduled) { this.totalScheduled = totalScheduled; }
    public Gulbi getGulbi() { return gulbi; }
    public void setGulbi(Gulbi gulbi) { this.gulbi = gulbi; }
    public List<AccountBookEntry> getRecentEntries() { return recentEntries; }
    public void setRecentEntries(List<AccountBookEntry> recentEntries) { this.recentEntries = recentEntries; }
    public String getGulbiMessage() { return gulbiMessage; }
    public void setGulbiMessage(String gulbiMessage) { this.gulbiMessage = gulbiMessage; }
}
