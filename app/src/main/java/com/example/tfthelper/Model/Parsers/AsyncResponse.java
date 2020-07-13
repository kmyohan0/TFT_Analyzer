package com.example.tfthelper.Model.Parsers;

import com.example.tfthelper.Model.Dto.MatchDto;
import com.example.tfthelper.Model.Dto.MatchIds;
import com.example.tfthelper.Model.Dto.SummonerDto;

import java.net.MalformedURLException;

public interface AsyncResponse {
    void userIdResponse(SummonerDto summonerDto) throws MalformedURLException;
    void matchListsResponse(MatchIds matchIds);
    void matchDataResponse(MatchDto matchDto);
}
