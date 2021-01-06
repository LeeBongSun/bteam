package com.example.myproject.Dto;

import java.io.Serializable;

/**
 * Created by LG on 2017-02-08.
 */

public class MyItem implements Serializable {
    public String id;
    public String name;
    public String hire_date;
    public String image_path;

    public MyItem(String id, String name, String hire_date, String image_path) {
        this.id = id;
        this.name = name;
        this.hire_date = hire_date;
        this.image_path = image_path;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return hire_date;
    }

    public void setDate(String hire_date) {
        this.hire_date = hire_date;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

}
