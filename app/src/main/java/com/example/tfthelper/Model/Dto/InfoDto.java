package com.example.tfthelper.Model.Dto;

import java.io.Serializable;
import java.util.List;

public class InfoDto implements Serializable {

    //Variables
    private long game_datetime;
    private float game_length;
    private String game_variation, game_version;
    private List<ParticipantDto> participants;
    private int queue_id, tft_set_number;

    public InfoDto(long game_datetime, float game_length, String game_variation, String game_version, List<ParticipantDto> participants, int queue_id, int tft_set_number) {
        this.game_datetime = game_datetime;
        this.game_length = game_length;
        this.game_variation = game_variation;
        this.game_version = game_version;
        this.participants = participants;
        this.queue_id = queue_id;
        this.tft_set_number = tft_set_number;
    }

    public long getGame_datetime() {
        return game_datetime;
    }

    public void setGame_datetime(long game_datetime) {
        this.game_datetime = game_datetime;
    }

    public float getGame_length() {
        return game_length;
    }

    public void setGame_length(float game_length) {
        this.game_length = game_length;
    }

    public String getGame_variation() {
        return game_variation;
    }

    public void setGame_variation(String game_variation) {
        this.game_variation = game_variation;
    }

    public String getGame_version() {
        return game_version;
    }

    public void setGame_version(String game_version) {
        this.game_version = game_version;
    }

    public List<ParticipantDto> getParticipants() {
        return participants;
    }

    public void setParticipants(List<ParticipantDto> participants) {
        this.participants = participants;
    }

    public int getQueue_id() {
        return queue_id;
    }

    public void setQueue_id(int queue_id) {
        this.queue_id = queue_id;
    }

    public int getTft_set_number() {
        return tft_set_number;
    }

    public void setTft_set_number(int tft_set_number) {
        this.tft_set_number = tft_set_number;
    }
}
