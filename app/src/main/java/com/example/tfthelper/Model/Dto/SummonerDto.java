package com.example.tfthelper.Model.Dto;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class SummonerDto implements Parcelable {

    private String accountId, name, id, puuid;
    private long revisionDate, summonerLevel;
    private int profileIconId;

    public SummonerDto(String accountId, String name, String id, String puuid, long revisionDate, long summonerLevel, int profileIconId) {
        this.accountId = accountId;
        this.name = name;
        this.id = id;
        this.puuid = puuid;
        this.revisionDate = revisionDate;
        this.summonerLevel = summonerLevel;
        this.profileIconId = profileIconId;
    }

    protected SummonerDto(Parcel in) {
        accountId = in.readString();
        name = in.readString();
        id = in.readString();
        puuid = in.readString();
        revisionDate = in.readLong();
        summonerLevel = in.readLong();
        profileIconId = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(accountId);
        dest.writeString(name);
        dest.writeString(id);
        dest.writeString(puuid);
        dest.writeLong(revisionDate);
        dest.writeLong(summonerLevel);
        dest.writeInt(profileIconId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SummonerDto> CREATOR = new Creator<SummonerDto>() {
        @Override
        public SummonerDto createFromParcel(Parcel in) {
            return new SummonerDto(in);
        }

        @Override
        public SummonerDto[] newArray(int size) {
            return new SummonerDto[size];
        }
    };

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPuuid() {
        return puuid;
    }

    public void setPuuid(String puuid) {
        this.puuid = puuid;
    }

    public long getRevisionDate() {
        return revisionDate;
    }

    public void setRevisionDate(long revisionDate) {
        this.revisionDate = revisionDate;
    }

    public long getSummonerLevel() {
        return summonerLevel;
    }

    public void setSummonerLevel(long summonerLevel) {
        this.summonerLevel = summonerLevel;
    }

    public int getProfileIconId() {
        return profileIconId;
    }

    public void setProfileIconId(int profileIconId) {
        this.profileIconId = profileIconId;
    }
}
