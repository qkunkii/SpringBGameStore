package com.qkunkii.qkn.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;
    public String name, short_desc, full_desc, image;
    public int wishlisted, downloads;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShort_desc() {
        return short_desc;
    }

    public void setShort_desc(String short_desc) {
        this.short_desc = short_desc;
    }

    public String getFull_desc() {
        return full_desc;
    }

    public void setFull_desc(String full_desc) {
        this.full_desc = full_desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Post() {

    }

    public Post(String name, String short_desc, String full_desc, String image) {
        this.name = name;
        this.short_desc = short_desc;
        this.full_desc = full_desc;
        this.image = image;
    }
    public Post(long id,String name, String short_desc, String full_desc, String image, int wishlisted, int downloads) {
        this.id = id;
        this.name = name;
        this.short_desc = short_desc;
        this.full_desc = full_desc;
        this.image = image;
        this.downloads = downloads;
        this.wishlisted = wishlisted;

    }
    public Post(String name, String short_desc, String full_desc, String image, int wishlisted, int downloads) {

        this.name = name;
        this.short_desc = short_desc;
        this.full_desc = full_desc;
        this.image = image;
        this.downloads = downloads;
        this.wishlisted = wishlisted;

    }
}
