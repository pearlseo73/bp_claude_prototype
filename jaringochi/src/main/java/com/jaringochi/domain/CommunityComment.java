package com.jaringochi.domain;

import java.time.LocalDateTime;

/**
 * 커뮤니티 댓글 도메인 클래스
 */
public class CommunityComment {
    private long id;
    private long postId;
    private long userId;
    private String content;
    private LocalDateTime createdAt;

    // 화면 표시용 transient 필드
    private String nickname;

    public CommunityComment() {
    }

    public CommunityComment(long id, long postId, long userId, String content, LocalDateTime createdAt) {
        this.id = id;
        this.postId = postId;
        this.userId = userId;
        this.content = content;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
