package com.example.tfthelper.Model.Dto;

import java.io.Serializable;
import java.util.List;

public class MetadataDto implements Serializable {

    //variables
    private String data_version, match_id;
    private List<String> participants;

    public MetadataDto(String data_version, String match_id, List<String> participants) {
        this.data_version = data_version;
        this.match_id = match_id;
        this.participants = participants;
    }

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
