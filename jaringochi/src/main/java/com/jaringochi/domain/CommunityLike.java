package com.jaringochi.domain;

import java.time.LocalDateTime;

/**
 * 커뮤니티 좋아요 도메인 클래스
 */
public class CommunityLike {
    private long id;
    private long postId;
    private long userId;
    private LocalDateTime createdAt;

    public CommunityLike() {
    }

    public CommunityLike(long id, long postId, long userId, LocalDateTime createdAt) {
        this.id = id;
        this.postId = postId;
        this.userId = userId;
        this.createdAt = createdAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
