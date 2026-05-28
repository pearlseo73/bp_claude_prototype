package com.jaringochi.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 가계부 항목 도메인 클래스
 */
public class AccountBookEntry {
    private long id;
    private long userId;
    private long categoryId;
    private String type;
    private int amount;
    private String memo;
    private LocalDate entryDate;
    private boolean isScheduled;
    private LocalDateTime createdAt;

    // 화면 표시용 transient 필드
    private String categoryName;

    public AccountBookEntry() {
    }

    public AccountBookEntry(long id, long userId, long categoryId, String type, int amount,
                            String memo, LocalDate entryDate, boolean isScheduled, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.categoryId = categoryId;
        this.type = type;
        this.amount = amount;
        this.memo = memo;
        this.entryDate = entryDate;
        this.isScheduled = isScheduled;
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

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public LocalDate getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDate entryDate) {
        this.entryDate = entryDate;
    }

    public boolean isScheduled() {
        return isScheduled;
    }

    public void setScheduled(boolean scheduled) {
        isScheduled = scheduled;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
