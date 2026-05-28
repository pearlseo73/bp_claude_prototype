package com.jaringochi.domain;

/**
 * 굴비 옷 도메인 클래스
 */
public class GulbiClothes {
    private long id;
    private String name;
    private String imagePath;

    public GulbiClothes() {
    }

    public GulbiClothes(long id, String name, String imagePath) {
        this.id = id;
        this.name = name;
        this.imagePath = imagePath;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
