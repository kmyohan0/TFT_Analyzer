package com.example.tfthelper.Model.Dto;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class MatchDto implements Parcelable {

    //variables
    private MetadataDto metadata;
    private InfoDto info;

    public MatchDto(MetadataDto metadata, InfoDto info) {
        this.metadata = metadata;
        this.info = info;
    }


    public MetadataDto getMetadata() { return metadata; }

    public void setMetadata(MetadataDto metadata) {
        this.metadata = metadata;
    }

    public InfoDto getInfo() {
        return info;
    }

    public void setInfo(InfoDto info) { this.info = info; }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.metadata, flags);
        dest.writeParcelable(this.info, flags);
    }

    protected MatchDto(Parcel in) {
        this.metadata = in.readParcelable(MetadataDto.class.getClassLoader());
        this.info = in.readParcelable(InfoDto.class.getClassLoader());
    }

    public static final Creator<MatchDto> CREATOR = new Creator<MatchDto>() {
        @Override
        public MatchDto createFromParcel(Parcel source) {
            return new MatchDto(source);
        }

        @Override
        public MatchDto[] newArray(int size) {
            return new MatchDto[size];
        }
    };
}

