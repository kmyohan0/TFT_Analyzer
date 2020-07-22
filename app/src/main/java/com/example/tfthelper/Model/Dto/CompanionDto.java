package com.example.tfthelper.Model.Dto;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class CompanionDto implements Parcelable {

    //Variables
    private String content_ID, species;
    private int skin_ID;

    public CompanionDto(String content_ID, String species, int skin_ID) {
        this.content_ID = content_ID;
        this.species = species;
        this.skin_ID = skin_ID;
    }

    protected CompanionDto(Parcel in) {
        content_ID = in.readString();
        species = in.readString();
        skin_ID = in.readInt();
    }

    public static final Creator<CompanionDto> CREATOR = new Creator<CompanionDto>() {
        @Override
        public CompanionDto createFromParcel(Parcel in) {
            return new CompanionDto(in);
        }

        @Override
        public CompanionDto[] newArray(int size) {
            return new CompanionDto[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(content_ID);
        dest.writeString(species);
        dest.writeInt(skin_ID);
    }
}
