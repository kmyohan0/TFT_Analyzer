package com.example.tfthelper.Model.Dto;

import java.util.List;

public class championLists {

    private List<champions> champions;

    public championLists(List<com.example.tfthelper.Model.Dto.champions> champions) {
        this.champions = champions;
    }

    public List<com.example.tfthelper.Model.Dto.champions> getChampions() {
        return champions;
    }

    public void setChampions(List<com.example.tfthelper.Model.Dto.champions> champions) {
        this.champions = champions;
    }
}
