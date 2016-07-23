package com.mdgagj.clicker3.models;

/**
 * Project YobaClicker. Created by gwa on 7/23/16.
 */

public class Achievement {
    private String name;
    private String description;
    private Integer image;
    private String status;
    private Integer id;

    public Achievement(Integer id, String name, String description, Integer image, String status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.status = status;
    }

    public Achievement() {
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getImage() {
        return image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(Integer image) {
        this.image = image;
    }
}
