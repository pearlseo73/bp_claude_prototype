package com.jaringochi.dto;

/**
 * 캘린더 일자별 DTO
 */
public class CalendarDayDto {
    private int day;
    private int totalIncome;
    private int totalExpense;
    private boolean hasEntries;

    public CalendarDayDto() {
    }

    public CalendarDayDto(int day, int totalIncome, int totalExpense, boolean hasEntries) {
        this.day = day;
        this.totalIncome = totalIncome;
        this.totalExpense = totalExpense;
        this.hasEntries = hasEntries;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
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

    public boolean isHasEntries() {
        return hasEntries;
    }

    public void setHasEntries(boolean hasEntries) {
        this.hasEntries = hasEntries;
    }
}
