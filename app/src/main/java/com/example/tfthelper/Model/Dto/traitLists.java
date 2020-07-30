package com.example.tfthelper.Model.Dto;

import java.util.List;

public class traitLists {

    private List<traits> traits;

    public traitLists(List<com.example.tfthelper.Model.Dto.traits> traits) {
        this.traits = traits;
    }

    public List<com.example.tfthelper.Model.Dto.traits> getTraits() {
        return traits;
    }

    public void setTraits(List<com.example.tfthelper.Model.Dto.traits> traits) {
        this.traits = traits;
    }
}
