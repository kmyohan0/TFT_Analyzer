package com.example.tfthelper.Model.Parsers;

import com.example.tfthelper.Model.Dto.TraitDto;
import com.example.tfthelper.Model.Dto.UnitDto;

import java.util.List;

public class DeckWithChampions implements Comparable<DeckWithChampions> {

    private List<UnitDto> unitDto;
    private String traitName;
    private int occurrences;

    public DeckWithChampions(List<UnitDto> unitDto, String traitName, int occurrences) {
        this.unitDto = unitDto;
        this.traitName = traitName;
        this.occurrences = occurrences;
    }

    public List<UnitDto> getUnitDto() {
        return unitDto;
    }

    public void setUnitDto(List<UnitDto> unitDto) {
        this.unitDto = unitDto;
    }

    public String getTraitName() {
        return traitName;
    }

    public void setTraitName(String traitName) {
        this.traitName = traitName;
    }

    public int getOccurrences() {
        return occurrences;
    }

    public void setOccurrences(int occurrences) {
        this.occurrences = occurrences;
    }

    @Override
    public int compareTo(DeckWithChampions o) {
        return (this.getOccurrences() < o.getOccurrences() ? -1 : (this.getOccurrences() == o.getOccurrences() ? 0 : 1));
    }
}
