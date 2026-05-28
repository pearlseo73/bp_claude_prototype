package com.jaringochi.dto;

import java.time.LocalDate;

/**
 * 일별 요약 DTO
 */
public class DailySummaryDto {
    private LocalDate date;
    private int totalIncome;
    private int totalExpense;
    private int entryCount;

    public DailySummaryDto() {
    }

    public DailySummaryDto(LocalDate date, int totalIncome, int totalExpense, int entryCount) {
        this.date = date;
        this.totalIncome = totalIncome;
        this.totalExpense = totalExpense;
        this.entryCount = entryCount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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

    public int getEntryCount() {
        return entryCount;
    }

    public void setEntryCount(int entryCount) {
        this.entryCount = entryCount;
    }
}
