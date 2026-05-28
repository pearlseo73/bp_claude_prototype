package com.jaringochi.domain;

import java.time.LocalDateTime;

/**
 * 메이트 관계 도메인 클래스
 */
public class Mate {
    private long id;
    private long user1Id;
    private long user2Id;
    private LocalDateTime createdAt;

    // 화면 표시용 transient 필드
    private String mateNickname;

    public Mate() {
    }

    public Mate(long id, long user1Id, long user2Id, LocalDateTime createdAt) {
        this.id = id;
        this.user1Id = user1Id;
        this.user2Id = user2Id;
        this.createdAt = createdAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUser1Id() {
        return user1Id;
    }

    public void setUser1Id(long user1Id) {
        this.user1Id = user1Id;
    }

    public long getUser2Id() {
        return user2Id;
    }

    public void setUser2Id(long user2Id) {
        this.user2Id = user2Id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getMateNickname() {
        return mateNickname;
    }

    public void setMateNickname(String mateNickname) {
        this.mateNickname = mateNickname;
    }
}
