package com.example.tfthelper.Model.Dto;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.tfthelper.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UnitDto implements Parcelable {

    //Variables
    private List<Integer> items;
    private String character_id, name;
    private int rarity, tier;

    public UnitDto(List<Integer> items, String character_id, String name, int rarity, int tier) {
        this.items = items;
        this.character_id = character_id;
        this.name = name;
        this.rarity = rarity;
        this.tier = tier;
    }

    public List<Integer> getItems() {
        return items;
    }

    public void setItems(List<Integer> items) {
        this.items = items;
    }

    public String getCharacter_id() {
        return character_id;
    }

    public void setCharacter_id(String character_id) {
        this.character_id = character_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRarity() {
        return rarity;
    }

    public void setRarity(int rarity) {
        this.rarity = rarity;
    }

    public int getTier() {
        return tier;
    }

    public void setTier(int tier) {
        this.tier = tier;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.items);
        dest.writeString(this.character_id);
        dest.writeString(this.name);
        dest.writeInt(this.rarity);
        dest.writeInt(this.tier);
    }

    protected UnitDto(Parcel in) {
        this.items = new ArrayList<Integer>();
        in.readList(this.items, Integer.class.getClassLoader());
        this.character_id = in.readString();
        this.name = in.readString();
        this.rarity = in.readInt();
        this.tier = in.readInt();
    }

    public static final Creator<UnitDto> CREATOR = new Creator<UnitDto>() {
        @Override
        public UnitDto createFromParcel(Parcel source) {
            return new UnitDto(source);
        }

        @Override
        public UnitDto[] newArray(int size) {
            return new UnitDto[size];
        }
    };
}
