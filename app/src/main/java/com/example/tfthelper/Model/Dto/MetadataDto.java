package com.example.tfthelper.Model.Dto;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

public class MetadataDto implements Parcelable {

    //variables
    private String data_version, match_id;
    private List<String> participants;

    public MetadataDto(String data_version, String match_id, List<String> participants) {
        this.data_version = data_version;
        this.match_id = match_id;
        this.participants = participants;
    }

    protected MetadataDto(Parcel in) {
        data_version = in.readString();
        match_id = in.readString();
        participants = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(data_version);
        dest.writeString(match_id);
        dest.writeStringList(participants);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MetadataDto> CREATOR = new Creator<MetadataDto>() {
        @Override
        public MetadataDto createFromParcel(Parcel in) {
            return new MetadataDto(in);
        }

        @Override
        public MetadataDto[] newArray(int size) {
            return new MetadataDto[size];
        }
    };

    public String getData_version() {
        return data_version;
    }

    public void setData_version(String data_version) {
        this.data_version = data_version;
    }

    public String getMatch_id() {
        return match_id;
    }

    public void setMatch_id(String match_id) {
        this.match_id = match_id;
    }

    public List<String> getParticipants() {
        return participants;
    }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }
}
