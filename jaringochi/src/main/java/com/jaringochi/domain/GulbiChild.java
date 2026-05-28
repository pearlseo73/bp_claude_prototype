package com.jaringochi.domain;

import java.time.LocalDateTime;

/**
 * 굴비 새끼 도메인 클래스
 */
public class GulbiChild {
    private long id;
    private long userId;
    private int childOrder;
    private int weight;
    private LocalDateTime acquiredAt;

    public GulbiChild() {
    }

    public GulbiChild(long id, long userId, int childOrder, int weight, LocalDateTime acquiredAt) {
        this.id = id;
        this.userId = userId;
        this.childOrder = childOrder;
        this.weight = weight;
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

    public int getChildOrder() {
        return childOrder;
    }

    public void setChildOrder(int childOrder) {
        this.childOrder = childOrder;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public LocalDateTime getAcquiredAt() {
        return acquiredAt;
    }

    public void setAcquiredAt(LocalDateTime acquiredAt) {
        this.acquiredAt = acquiredAt;
    }
}
