package com.example.tfthelper.Model.Dto;

import java.io.Serializable;

public class MatchIds implements Serializable {

    private String[] matches;

    public MatchIds(String[] matches) {
        this.matches = matches;
    }

    public String[] getMatches() {
        return matches;
    }

    public void setMatches(String[] matches) {
        this.matches = matches;
    }
}
