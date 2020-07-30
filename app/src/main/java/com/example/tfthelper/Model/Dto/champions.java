package com.example.tfthelper.Model.Dto;

import java.util.List;

public class champions {

    private  String name;
    private String championId;
    private int cost;
    private List<String> traits;

    public champions(String name, String championId, int cost, List<String> traits) {
        this.name = name;
        this.championId = championId;
        this.cost = cost;
        this.traits = traits;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChampionId() {
        return championId;
    }

    public void setChampionId(String championId) {
        this.championId = championId;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public List<String> getTraits() {
        return traits;
    }

    public void setTraits(List<String> traits) {
        this.traits = traits;
    }
}
