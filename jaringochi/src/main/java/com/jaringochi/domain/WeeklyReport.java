package com.jaringochi.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 주간 리포트 도메인 클래스
 */
public class WeeklyReport {
    private long id;
    private long userId;
    private LocalDate weekStart;
    private LocalDate weekEnd;
    private int totalIncome;
    private int totalExpense;
    private int budgetAmount;
    private boolean budgetSuccess;
    private String feedbackMessage;
    private LocalDateTime createdAt;

    public WeeklyReport() {
    }

    public WeeklyReport(long id, long userId, LocalDate weekStart, LocalDate weekEnd,
                        int totalIncome, int totalExpense, int budgetAmount, boolean budgetSuccess,
                        String feedbackMessage, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.weekStart = weekStart;
        this.weekEnd = weekEnd;
        this.totalIncome = totalIncome;
        this.totalExpense = totalExpense;
        this.budgetAmount = budgetAmount;
        this.budgetSuccess = budgetSuccess;
        this.feedbackMessage = feedbackMessage;
        this.createdAt = createdAt;
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

    public LocalDate getWeekStart() {
        return weekStart;
    }

    public void setWeekStart(LocalDate weekStart) {
        this.weekStart = weekStart;
    }

    public LocalDate getWeekEnd() {
        return weekEnd;
    }

    public void setWeekEnd(LocalDate weekEnd) {
        this.weekEnd = weekEnd;
    }

    public int getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(int totalIncome) {
        this.totalIncome = totalIncome;
    }

    public int getTotalExpense() {
        return totalExpense;
    }

    public void setTotalExpense(int totalExpense) {
        this.totalExpense = totalExpense;
    }

    public int getBudgetAmount() {
        return budgetAmount;
    }

    public void setBudgetAmount(int budgetAmount) {
        this.budgetAmount = budgetAmount;
    }

    public boolean isBudgetSuccess() {
        return budgetSuccess;
    }

    public void setBudgetSuccess(boolean budgetSuccess) {
        this.budgetSuccess = budgetSuccess;
    }

    public String getFeedbackMessage() {
        return feedbackMessage;
    }

    public void setFeedbackMessage(String feedbackMessage) {
        this.feedbackMessage = feedbackMessage;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
