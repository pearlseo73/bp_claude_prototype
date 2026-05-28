package com.jaringochi.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 굴비 캐릭터 도메인 클래스
 */
public class Gulbi {
    private long id;
    private long userId;
    private String gulbiType;
    private String personality;
    private int weight;
    private Long activeClothesId; // nullable
    private int streakRecord;
    private int streakBudget;
    private LocalDate lastRecordDate;
    private LocalDateTime createdAt;

    public Gulbi() {
    }

    public Gulbi(long id, long userId, String gulbiType, String personality, int weight,
                 Long activeClothesId, int streakRecord, int streakBudget,
                 LocalDate lastRecordDate, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.gulbiType = gulbiType;
        this.personality = personality;
        this.weight = weight;
        this.activeClothesId = activeClothesId;
        this.streakRecord = streakRecord;
        this.streakBudget = streakBudget;
        this.lastRecordDate = lastRecordDate;
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

    public String getGulbiType() {
        return gulbiType;
    }

    public void setGulbiType(String gulbiType) {
        this.gulbiType = gulbiType;
    }

    public String getPersonality() {
        return personality;
    }

    public void setPersonality(String personality) {
        this.personality = personality;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Long getActiveClothesId() {
        return activeClothesId;
    }

    public void setActiveClothesId(Long activeClothesId) {
        this.activeClothesId = activeClothesId;
    }

    public int getStreakRecord() {
        return streakRecord;
    }

    public void setStreakRecord(int streakRecord) {
        this.streakRecord = streakRecord;
    }

    public int getStreakBudget() {
        return streakBudget;
    }

    public void setStreakBudget(int streakBudget) {
        this.streakBudget = streakBudget;
    }

    public LocalDate getLastRecordDate() {
        return lastRecordDate;
    }

    public void setLastRecordDate(LocalDate lastRecordDate) {
        this.lastRecordDate = lastRecordDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
