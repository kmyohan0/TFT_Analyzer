package com.example.tfthelper.Presenter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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

import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AsyncResponse {

    private Button checkButton, openIntentButton;
    private EditText summonerName;
    private TextView puuidViewer;

    private SummonerDto summonerDto;
    private String[] matchData;
    private MatchDto matchDto;
    private List<String> winSet = new ArrayList<>();

    final private String APIKey = ""; // Update when debugging - Using App, don't put anything when pushing

    public static final String TAG = "item_";

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
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        openIntentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity();
            }
        });
    }

    private void linkId() {
        checkButton = (Button) findViewById(R.id.check_button);
        summonerName = (EditText) findViewById(R.id.summoner_name);
        puuidViewer = (TextView) findViewById(R.id.puuid_viewer);
        openIntentButton = (Button) findViewById(R.id.open_intent_button);
    }

    private void retrieveId() {
        String userName = summonerName.getText().toString();
        String link = userIdAPI + userName + "?api_key=" + APIKey;
        SummonerIdAsync summonerIdAsync = new SummonerIdAsync();
        summonerIdAsync.delegate = this;
        summonerIdAsync.execute(link);
    }

    private void retrieveMatches() {
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
        retrieveMatches();
    }

    @Override
    public void matchListsResponse(MatchIds matchIds) {
        matchData = matchIds.getMatches();
        String result = "";
        for (int i = 0; i < 20; i++) {
            result += matchData[i];
        }
        for (int i = 0; i < 20; i++) {
            final int temp = i;
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        retrieveMatchData(temp);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                }
            }, 500);
        }
    }

    private void openActivity() {
        Intent intent = new Intent(this, AnalysisActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("MatchData", matchDto);
        bundle.putInt("profileIconNumber", summonerDto.getProfileIconId());
        bundle.putLong("summonerLevel", summonerDto.getSummonerLevel());
        bundle.putString("summonerName", summonerDto.getName());
        intent.putExtra("information", bundle);
        startActivity(intent);
    }

    @Override
    public void matchDataResponse(MatchDto matchDto) {
        this.matchDto = matchDto;
        String deckUsed = "";
        //TODO: change / create / add new arrayList that holds user's deck, instead of winner's deck (Needs to be fixed from RIOT, but still waiting on it)
        for (int i = 0; i < 8; i++) {
            int userPlacement = matchDto.getInfo().getParticipants().get(i).getPlacement();
            if (userPlacement == 1) {
                deckUsed = matchDto.getInfo().getParticipants().get(i).getTraits().get(0).getName() + " " + matchDto.getInfo().getParticipants().get(i).getTraits().get(1).getName();
                break;
            }
        }

        winSet.add(deckUsed);
        puuidViewer.append("Set: " + winSet.get(winSet.size()-1) + '\n');
    }

}