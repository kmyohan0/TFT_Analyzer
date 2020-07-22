package com.example.tfthelper.Model.Parsers;

import java.util.List;

public class ChampionWithItems implements Comparable<ChampionWithItems>{
    private String chamiponName;
    private int occurrences;
    private List<Integer> itemLists;

    public ChampionWithItems(String chamiponName, int occurrences, List<Integer> itemLists) {
        this.chamiponName = chamiponName;
        this.occurrences = occurrences;
        this.itemLists = itemLists;
    }

    public String getChamiponName() {
        return chamiponName;
    }

    public void setChamiponName(String chamiponName) {
        this.chamiponName = chamiponName;
    }

    public int getOccurrences() {
        return occurrences;
    }

    public void setOccurrences(int occurrences) {
        this.occurrences = occurrences;
    }

    public List<Integer> getItemLists() {
        return itemLists;
    }

    public void setItemLists(List<Integer> itemLists) {
        this.itemLists = itemLists;
    }

    public void addItem(int itemNumber) {
        itemLists.add(itemNumber);
    }

    @Override
    public int compareTo(ChampionWithItems o) {
        return (this.getOccurrences() < o.getOccurrences() ? -1 : (this.getOccurrences() == o.getOccurrences() ? 0 : 1));
    }
}
