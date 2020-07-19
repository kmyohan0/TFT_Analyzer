package com.example.tfthelper.Model.Dto;

import java.io.Serializable;

public class MatchDto implements Serializable {

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

    public void setInfo(InfoDto info) {
        this.info = info;
    }
}
