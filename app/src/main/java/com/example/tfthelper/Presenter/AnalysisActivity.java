package com.example.tfthelper.Presenter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tfthelper.Model.Dto.MatchDto;
import com.example.tfthelper.R;


public class AnalysisActivity extends AppCompatActivity {

    Bundle extras;

    private MatchDto matchDto;
    private TextView levelView, summonerNameView;
    private ImageView summonerIcon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            Intent intent = getIntent();
            extras = intent.getExtras();
            if (extras != null) {
                matchDto = (MatchDto) getIntent().getSerializableExtra("MatchData");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            Log.d("matchDto", "MatchDto is causing error?");
        }
        levelView = (TextView) findViewById(R.id.level);
        summonerNameView = (TextView) findViewById(R.id.summoner_name);
        summonerIcon = (ImageView) findViewById(R.id.profile_icon);
        setup();
        //TODO: My plan is to show:
        // Player Information (that is, icon, level, name, etc)
        // for profileIcon, use http://ddragon.leagueoflegends.com/cdn/10.14.1/img/profileicon/$(iconNumber).png
        // favorite Deck
        // average win rate
        // favorite deck compare to other set of deck
    }

    private void setup() {
        levelView.append(""  + extras.getLong("summonerLevel"));
        summonerNameView.append(extras.getString("summonerName"));

        String iconAddress = "http://ddragon.leagueoflegends.com/cdn/10.14.1/img/profileicon/" + extras.getInt("profileIconNumber") + ".png";
        Uri uri = Uri.parse(iconAddress);
        summonerIcon.setImageURI(uri);
    }


}