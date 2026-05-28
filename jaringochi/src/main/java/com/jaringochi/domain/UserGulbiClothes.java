package com.jaringochi.domain;

import java.time.LocalDateTime;

/**
 * 사용자 보유 굴비 옷 도메인 클래스
 */
public class UserGulbiClothes {
    private long id;
    private long userId;
    private long clothesId;
    private LocalDateTime acquiredAt;

    public UserGulbiClothes() {
    }

    public UserGulbiClothes(long id, long userId, long clothesId, LocalDateTime acquiredAt) {
        this.id = id;
        this.userId = userId;
        this.clothesId = clothesId;
        this.acquiredAt = acquiredAt;
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

    public long getClothesId() {
        return clothesId;
    }

    public void setClothesId(long clothesId) {
        this.clothesId = clothesId;
    }

    public LocalDateTime getAcquiredAt() {
        return acquiredAt;
    }

    public void setAcquiredAt(LocalDateTime acquiredAt) {
        this.acquiredAt = acquiredAt;
    }
}
