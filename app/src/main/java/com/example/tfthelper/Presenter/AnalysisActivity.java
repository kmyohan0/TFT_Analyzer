package com.example.tfthelper.Presenter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


public class AnalysisActivity extends AppCompatActivity {

    Bundle extras;

    private ArrayList<MatchDto> matchDto = new ArrayList<>();
    private TextView levelView, summonerNameView;
    private ImageView summonerIcon;
    private ListView listView;
    //Variable mostUsedUnits consists of:
    //Key = HashMap that has ChampionName as Key
    //Value = number of occurrences for that champion
    private HashMap<String, Integer> mostUsedUnits = new HashMap<>();
    //Unfortunately, It is extremely difficult to store itemlists with champion occurence, thus we are separating it, and increase time complexity (What could go wrong...)
    //Key = HashMap that has ChampionName as Key,
    //Value = HashMap that has ItemNumber as Key, and # of occurrences for that Item as value
    private HashMap<String, HashMap<Integer, Integer>> usedItemsForUsedMostUnit = new HashMap<>();


    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_analysis);
            Intent intent = getIntent();
            extras = intent.getBundleExtra("information");
            for (int i = 0; i < 20; i++) {
                MatchDto temp = (MatchDto) extras.getParcelable("MatchData"+i);
                matchDto.add(temp);
            }
            Log.d("ArraylistSize", ""+matchDto.size());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        levelView = (TextView) findViewById(R.id.level);
        summonerNameView = (TextView) findViewById(R.id.summoner_name);
        summonerIcon = (ImageView) findViewById(R.id.profile_icon);
        listView = (ListView) findViewById(R.id.content_view);
        setup();
        analyzeMatchdatas();
        MyAdapter adapter = new MyAdapter();
        listView.setAdapter(adapter);
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
    }

    private void analyzeMatchdatas() {
        for (int i = 0; i < matchDto.size(); i++) {
            for (int j =0; j < 8; j++) {
                int userPlacement = matchDto.get(i).getInfo().getParticipants().get(j).getPlacement();
                //TODO: Change this from First place to User's deck (STILL WAITING FOR RIOT API BE FIXED)
                if (userPlacement == 1) {
                    for (int k =0; k < matchDto.get(i).getInfo().getParticipants().get(j).getUnits().size(); k++) {
                        String unitName = matchDto.get(i).getInfo().getParticipants().get(j).getUnits().get(k).getCharacter_id();
                        List<Integer> itemLists = matchDto.get(i).getInfo().getParticipants().get(j).getUnits().get(k).getItems();
                        //Store UnitNames
                        if (mostUsedUnits.containsKey(unitName)) {
                            mostUsedUnits.replace(unitName, mostUsedUnits.get(unitName)+1);
                        }
                        else {
                            mostUsedUnits.put(unitName, 1);
                        }
                        //Now update ItemLists
                        //First, check to see if unit is in HashMap
                        if (usedItemsForUsedMostUnit.containsKey(unitName)) {
                            //now we know that HashMap has the key, meaning that we did put some element in there
                            //Thus, we need to update our items
                            //First, for all items in itemLists, we need to check if we already putted or not.
                            for (int l = 0; l < itemLists.size(); l++) {
                                if (usedItemsForUsedMostUnit.get(unitName).containsKey(itemLists.get(l))) {
                                    usedItemsForUsedMostUnit.get(unitName).replace(itemLists.get(l), usedItemsForUsedMostUnit.get(unitName).get(itemLists.get(l))+1);
                                }
                                else {
                                    usedItemsForUsedMostUnit.get(unitName).put(itemLists.get(l),1);
                                }
                            }
                        }
                        //If unit is not in usedItemsFOrUsdeMostUnit, then put everything in there
                        else {
                            HashMap<Integer, Integer> newItemMap = new HashMap<>();
                            for (int l = 0; l < itemLists.size(); l++) {
                                newItemMap.put(itemLists.get(l), 1);
                            }
                            usedItemsForUsedMostUnit.put(unitName, newItemMap);
                        }
                    }
                }
            }
        }
    }

    private class MyAdapter extends BaseAdapter {

        private String trimTraitName(String trait) {
            if (trait.contains("TFT") || trait.contains("Tft")) {
                trait = trait.substring(5);
            }
            return trait;
        }

        @Override
        public int getCount() {
            return mostUsedUnits.size();
        }

        @Override
        public Object getItem(int position) {
            return mostUsedUnits.get(position);
        }


        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup container) {
            ImageView championView;
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.activity_listview, container, false);
                championView = (ImageView) convertView.findViewById(R.id.champion_image);
                String unitName = mostUsedUnits.keySet().toArray()[position].toString();
                int championImagePath = getResources().getIdentifier((trimTraitName(unitName).toLowerCase()), "drawable", getPackageName());
                championView.setImageResource(championImagePath);
                TextView name = (TextView) convertView.findViewById(R.id.champion_name);
                name.append(trimTraitName(unitName));
                TextView used = (TextView) convertView.findViewById(R.id.champion_used);
                used.append(mostUsedUnits.get(unitName).toString());
                //Now, access item
                ImageView itemView1 = (ImageView) convertView.findViewById(R.id.list_item_1);
                ImageView itemView2 = (ImageView) convertView.findViewById(R.id.list_item_2);
                ImageView itemView3 = (ImageView) convertView.findViewById(R.id.list_item_3);
                //Find item images by using String
                int itemImagePath1 = 0, itemImagePath2 = 0, itemImagePath3 = 0;
                Iterator iterator = usedItemsForUsedMostUnit.get(unitName).keySet().iterator();
                if (iterator.hasNext()) {
                    itemImagePath1 = getResources().getIdentifier("item_" + iterator.next().toString(), "drawable", getPackageName());
                }
                if (iterator.hasNext()) {
                    itemImagePath2 = getResources().getIdentifier("item_" + iterator.next().toString(), "drawable", getPackageName());
                }
                if (iterator.hasNext()) {
                    itemImagePath3 = getResources().getIdentifier("item_" + iterator.next().toString(), "drawable", getPackageName());
                }

                if (itemImagePath1 != 0) {
                    itemView1.setImageResource(itemImagePath1);
                }
                if (itemImagePath2 != 0) {
                    itemView2.setImageResource(itemImagePath2);
                }
                if (itemImagePath3 != 0) {
                    itemView3.setImageResource(itemImagePath3);
                }
            }
            return convertView;
        }
    }

}