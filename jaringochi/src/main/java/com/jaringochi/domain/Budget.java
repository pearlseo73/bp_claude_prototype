package com.jaringochi.domain;

import java.time.LocalDate;

/**
 * 예산 도메인 클래스
 */
public class Budget {
    private long id;
    private long userId;
    private String budgetType;
    private int weeklyAmount;
    private int monthlyAmount;
    private LocalDate startDate;
    private LocalDate endDate;

    public Budget() {
    }

    public Budget(long id, long userId, String budgetType, int weeklyAmount, int monthlyAmount,
                  LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.userId = userId;
        this.budgetType = budgetType;
        this.weeklyAmount = weeklyAmount;
        this.monthlyAmount = monthlyAmount;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getBudgetType() {
        return budgetType;
    }

    public void setBudgetType(String budgetType) {
        this.budgetType = budgetType;
    }

    public int getWeeklyAmount() {
        return weeklyAmount;
    }

    public void setWeeklyAmount(int weeklyAmount) {
        this.weeklyAmount = weeklyAmount;
    }

    public int getMonthlyAmount() {
        return monthlyAmount;
    }

    public void setMonthlyAmount(int monthlyAmount) {
        this.monthlyAmount = monthlyAmount;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
