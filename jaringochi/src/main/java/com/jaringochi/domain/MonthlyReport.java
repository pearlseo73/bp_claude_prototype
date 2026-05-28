package com.jaringochi.domain;

import java.time.LocalDateTime;

/**
 * 월간 리포트 도메인 클래스
 */
public class MonthlyReport {
    private long id;
    private long userId;
    private int year;
    private int month;
    private int totalIncome;
    private int totalExpense;
    private String feedbackMessage;
    private LocalDateTime createdAt;

    public MonthlyReport() {
    }

    public MonthlyReport(long id, long userId, int year, int month,
                         int totalIncome, int totalExpense, String feedbackMessage, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.year = year;
        this.month = month;
        this.totalIncome = totalIncome;
        this.totalExpense = totalExpense;
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
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
