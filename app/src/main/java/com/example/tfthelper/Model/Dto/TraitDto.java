package com.example.tfthelper.Model.Dto;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.io.Serializable;

public class TraitDto implements Parcelable {

    //variables
    private String name;
    private int num_units, tier_current, tier_total;

    public TraitDto(String name, int num_units, int tier_current, int tier_total) {
        this.name = name;
        this.num_units = num_units;
        this.tier_current = tier_current;
        this.tier_total = tier_total;
    }

    protected TraitDto(Parcel in) {
        name = in.readString();
        num_units = in.readInt();
        tier_current = in.readInt();
        tier_total = in.readInt();
    }

    public static final Creator<TraitDto> CREATOR = new Creator<TraitDto>() {
        @Override
        public TraitDto createFromParcel(Parcel in) {
            return new TraitDto(in);
        }

        @Override
        public TraitDto[] newArray(int size) {
            return new TraitDto[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum_units() {
        return num_units;
    }

    public void setNum_units(int num_units) {
        this.num_units = num_units;
    }

    public int getTier_current() {
        return tier_current;
    }

    public void setTier_current(int tier_current) {
        this.tier_current = tier_current;
    }

    public int getTier_total() {
        return tier_total;
    }

    public void setTier_total(int tier_total) {
        this.tier_total = tier_total;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(num_units);
        dest.writeInt(tier_current);
        dest.writeInt(tier_total);
    }
}
