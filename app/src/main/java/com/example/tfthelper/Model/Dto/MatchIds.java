package com.example.tfthelper.Model.Dto;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class MatchIds implements Parcelable {

    private String[] matches;

    public MatchIds(String[] matches) {
        this.matches = matches;
    }

    protected MatchIds(Parcel in) {
        matches = in.createStringArray();
    }

    public static final Creator<MatchIds> CREATOR = new Creator<MatchIds>() {
        @Override
        public MatchIds createFromParcel(Parcel in) {
            return new MatchIds(in);
        }

        @Override
        public MatchIds[] newArray(int size) {
            return new MatchIds[size];
        }
    };

    public String[] getMatches() {
        return matches;
    }

    public void setMatches(String[] matches) {
        this.matches = matches;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(matches);
    }
}
