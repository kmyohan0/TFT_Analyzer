package com.example.tfthelper.Presenter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.tfthelper.Model.Dto.MatchDto;
import com.example.tfthelper.Model.Parsers.AsyncResponse;
import com.example.tfthelper.Model.Dto.MatchIds;
import com.example.tfthelper.Model.Parsers.MatchDataAsync;
import com.example.tfthelper.Model.Parsers.MatchesAsync;
import com.example.tfthelper.Model.Parsers.SummonerIdAsync;
import com.example.tfthelper.Model.Dto.SummonerDto;
import com.example.tfthelper.R;

import java.net.MalformedURLException;

public class MainActivity extends AppCompatActivity implements AsyncResponse {

    private Button checkButton;
    private EditText summonerName;
    private TextView puuidViewer;

    private SummonerDto summonerDto;
    private String[] matchData;
    private MatchDto matchDto;

    final private String APIKey = "RGAPI-608a5661-60d2-4a2f-9a20-573c9d0a715c";

    //For Retrieving User's Id
    String userIdAPI = "https://na1.api.riotgames.com/tft/summoner/v1/summoners/by-name/"; // and add "UserName" + API_KEY="APIKEY"
    String matchesAPI = "https://americas.api.riotgames.com/tft/match/v1/matches/by-puuid/"; // and add "Puuid" +  ?count="number of data" + ?API_KEY="APIKEY"
    String matchDataAPI = "https://americas.api.riotgames.com/tft/match/v1/matches/"; // and add "Match ID" + ?API_KEY="APIKEY"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linkId();
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    retrieveId();
                    Handler handler = new Handler();
                    //Giving intentional Delay so that thread is replaced by new process (from AsyncTask)
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                retrieveMatches();
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }
                    }, 1500);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                Handler handler = new Handler();
                //Giving intentional Delay so that thread is replaced by new process (from AsyncTask)
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            retrieveMatchData(0);
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                    }
                }, 5000);
            }
        });
    }

    private void linkId() {
        checkButton = (Button) findViewById(R.id.check_button);
        summonerName = (EditText) findViewById(R.id.summoner_name);
        puuidViewer = (TextView) findViewById(R.id.puuid_viewer);
    }

    private void retrieveId() throws MalformedURLException {
        String userName = summonerName.getText().toString();
        String link = userIdAPI + userName + "?api_key=" + APIKey;
        SummonerIdAsync summonerIdAsync = new SummonerIdAsync();
        summonerIdAsync.delegate = this;
        summonerIdAsync.execute(link);
    }

    private void retrieveMatches() throws MalformedURLException{
        String link = matchesAPI + summonerDto.getPuuid() + "/ids?count=" + 20 + "&api_key=" + APIKey;
        MatchesAsync matchesAsync = new MatchesAsync();
        matchesAsync.delegate = this;
        matchesAsync.execute(link);
    }

    private void retrieveMatchData(int index) throws MalformedURLException{
        String link = matchDataAPI +  matchData[index] + "?api_key=" + APIKey;
        MatchDataAsync matchDataAsync = new MatchDataAsync();
        matchDataAsync.delegate = this;
        matchDataAsync.execute(link);
    }

    @Override
    public void userIdResponse(SummonerDto summonerDto) throws MalformedURLException {
        this.summonerDto = summonerDto;
        puuidViewer.setText(summonerDto.getPuuid());
    }

    @Override
    public void matchListsResponse(MatchIds matchIds) {
        matchData = matchIds.getMatches();
        String result = "";
        for (int i = 0; i < 20; i++) {
            result += matchData[i];
        }
        puuidViewer.setText(result);
    }

    @Override
    public void matchDataResponse(MatchDto matchDto) {
        this.matchDto = matchDto;
        puuidViewer.setText(matchDto.getInfo().getTft_set_number());
    }
}