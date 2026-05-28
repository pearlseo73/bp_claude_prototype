package com.jaringochi.domain;

/**
 * 카테고리 도메인 클래스 (수입/지출 분류)
 */
public class Category {
    private long id;
    private long userId;
    private String name;
    private String type; // INCOME / EXPENSE

    public Category() {
    }

    public Category(long id, long userId, String name, String type) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.type = type;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
