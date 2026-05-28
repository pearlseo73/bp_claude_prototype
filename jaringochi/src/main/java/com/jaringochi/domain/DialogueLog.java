package com.jaringochi.domain;

import java.time.LocalDateTime;

/**
 * 대화 로그 도메인 클래스 (굴비와의 대화)
 */
public class DialogueLog {
    private long id;
    private long userId;
    private Long monthlyReportId; // nullable
    private String userMessage;
    private String gulbiReply;
    private LocalDateTime createdAt;

    public DialogueLog() {
    }

    public DialogueLog(long id, long userId, Long monthlyReportId,
                       String userMessage, String gulbiReply, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.monthlyReportId = monthlyReportId;
        this.userMessage = userMessage;
        this.gulbiReply = gulbiReply;
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

    public Long getMonthlyReportId() {
        return monthlyReportId;
    }

    public void setMonthlyReportId(Long monthlyReportId) {
        this.monthlyReportId = monthlyReportId;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    public String getGulbiReply() {
        return gulbiReply;
    }

    public void setGulbiReply(String gulbiReply) {
        this.gulbiReply = gulbiReply;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
