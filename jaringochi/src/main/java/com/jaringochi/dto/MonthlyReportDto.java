package com.jaringochi.dto;

import com.jaringochi.domain.DialogueLog;
import com.jaringochi.domain.Gulbi;
import com.jaringochi.domain.MonthlyReport;

import java.util.List;

/**
 * 월간 리포트 화면 표시용 DTO (현재/이전 월 비교, 메이트 비교 포함)
 */
public class MonthlyReportDto {
    private MonthlyReport current;
    private MonthlyReport previous;
    private double expenseChangePercent;
    private Gulbi gulbi;
    private List<DialogueLog> dialogues;
    private String mateNickname;
    private int mateExpense;

    public MonthlyReportDto() {
    }

    public MonthlyReport getCurrent() {
        return current;
    }

    public void setCurrent(MonthlyReport current) {
        this.current = current;
    }

    public MonthlyReport getPrevious() {
        return previous;
    }

    public void setPrevious(MonthlyReport previous) {
        this.previous = previous;
    }

    public double getExpenseChangePercent() {
        return expenseChangePercent;
    }

    public void setExpenseChangePercent(double expenseChangePercent) {
        this.expenseChangePercent = expenseChangePercent;
    }

    public Gulbi getGulbi() {
        return gulbi;
    }

    public void setGulbi(Gulbi gulbi) {
        this.gulbi = gulbi;
    }

    public List<DialogueLog> getDialogues() {
        return dialogues;
    }

    public void setDialogues(List<DialogueLog> dialogues) {
        this.dialogues = dialogues;
    }

    public String getMateNickname() {
        return mateNickname;
    }

    public void setMateNickname(String mateNickname) {
        this.mateNickname = mateNickname;
    }

    public int getMateExpense() {
        return mateExpense;
    }

    public void setMateExpense(int mateExpense) {
        this.mateExpense = mateExpense;
    }
}
