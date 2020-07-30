package com.example.tfthelper.Model.Dto;

import java.util.List;

public class traits {

    private String key, name, description, type;
    private List<obj> sets;

    public traits(String key, String name, String description, String type, List<obj> sets) {
        this.key = key;
        this.name = name;
        this.description = description;
        this.type = type;
        this.sets = sets;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<obj> getSets() {
        return sets;
    }

    public void setSets(List<obj> sets) {
        this.sets = sets;
    }
}
