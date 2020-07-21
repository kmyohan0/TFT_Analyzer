package com.example.tfthelper.Presenter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tfthelper.Model.Dto.MatchDto;
import com.example.tfthelper.R;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.List;


public class AnalysisActivity extends AppCompatActivity {

    Bundle extras;

    private MatchDto matchDto;
    private TextView levelView, summonerNameView;
    private ImageView summonerIcon;
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_analysis);
            Intent intent = getIntent();
            extras = intent.getBundleExtra("information");
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
        listView = (ListView) findViewById(R.id.content_view);
        MyAdapter adapter = new MyAdapter();
        listView.setAdapter(adapter);
        setup();
        //TODO: My plan is to show:
        // favorite Deck
        // average win rate
        // favorite deck compare to other set of deck
    }

    private void setup() {
        levelView.append(""  + extras.getLong("summonerLevel"));
        summonerNameView.append(extras.getString("summonerName"));

        String iconAddress = "https://ddragon.leagueoflegends.com/cdn/10.14.1/img/profileicon/" + extras.getInt("profileIconNumber") + ".png";
        Picasso.get().setLoggingEnabled(true);
        Picasso.get().load(iconAddress).resize(150, 150).centerCrop().into(summonerIcon);
//        Picasso.Builder builder = new Picasso.Builder(getApplicationContext());
//        builder.listener(new Picasso.Listener() {
//            @Override
//            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
//                exception.printStackTrace();
//            }
//        });

    }
    //TODO: put championDto to Bundle so that Adapter can utilize those information
    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return ideas.getIdea().get("array").length;
        }

        @Override
        public List getItem(int position) {
            return ideas.getIdea().get("array")[position];
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.activity_listview, container, false);
            }
            TextView name = (TextView) convertView.findViewById(R.id.title);
            name.setText(ideas.getIdea().get("array")[position].getActivityName());
            TextView date = (TextView) convertView.findViewById(R.id.date);
            date.setText(ideas.getIdea().get("array")[position].getDate());
            //date.setText(ideas.getIdea().get("array")[0].getDate());
            return convertView;
        }
    }

}