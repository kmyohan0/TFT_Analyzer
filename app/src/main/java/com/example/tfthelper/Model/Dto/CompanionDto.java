package com.example.tfthelper.Model.Dto;

public class CompanionDto {

    //Variables
    private String content_ID, species;
    private int skin_ID;

    public CompanionDto(String content_ID, String species, int skin_ID) {
        this.content_ID = content_ID;
        this.species = species;
        this.skin_ID = skin_ID;
    }

    public String getContent_ID() {
        return content_ID;
    }

    public void setContent_ID(String content_ID) {
        this.content_ID = content_ID;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public int getSkin_ID() {
        return skin_ID;
    }

    public void setSkin_ID(int skin_ID) {
        this.skin_ID = skin_ID;
    }
}
