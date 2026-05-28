package com.jaringochi.dto;

import com.jaringochi.domain.WeeklyReport;

/**
 * 주간 리포트 화면 표시용 DTO (현재/이전 주 비교)
 */
public class WeeklyReportDto {
    private WeeklyReport current;
    private WeeklyReport previous;
    private double expenseChangePercent;
    private double incomeChangePercent;

    public WeeklyReportDto() {
    }

    public WeeklyReport getCurrent() {
        return current;
    }

    public void setCurrent(WeeklyReport current) {
        this.current = current;
    }

    public WeeklyReport getPrevious() {
        return previous;
    }

    public void setPrevious(WeeklyReport previous) {
        this.previous = previous;
    }

    public double getExpenseChangePercent() {
        return expenseChangePercent;
    }

    public void setExpenseChangePercent(double expenseChangePercent) {
        this.expenseChangePercent = expenseChangePercent;
    }

    public double getIncomeChangePercent() {
        return incomeChangePercent;
    }

    public void setIncomeChangePercent(double incomeChangePercent) {
        this.incomeChangePercent = incomeChangePercent;
    }
}
