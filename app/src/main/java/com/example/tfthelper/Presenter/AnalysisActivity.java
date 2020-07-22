package com.example.tfthelper.Presenter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tfthelper.Model.Dto.MatchDto;
import com.example.tfthelper.Model.Parsers.ChampionWithItems;
import com.example.tfthelper.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;


public class AnalysisActivity extends AppCompatActivity {

    Bundle extras;

    private ArrayList<MatchDto> matchDto = new ArrayList<>();
    private ArrayList<ChampionWithItems> championWithItems = new ArrayList<>();

    private TextView levelView, summonerNameView;
    private ImageView summonerIcon;

    private TextView championName1, championName2, championName3, championOccurrences1, championOccurrences2, championOccurrences3;
    private ImageView championImage1, championImage2, championImage3;
    private ImageView championItem1_1, championItem1_2, championItem1_3, championItem2_1, championItem2_2, championItem2_3, championItem3_1, championItem3_2, championItem3_3;


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
        linkId();
        analyzeMatchdatas();
        setup();
        //TODO: My plan is to show:
        // favorite Deck
        // average win rate
        // favorite deck compare to other set of deck
    }

    private void setup() {
        levelView.append("" + extras.getLong("summonerLevel"));
        summonerNameView.append(extras.getString("summonerName"));

        String iconAddress = "https://ddragon.leagueoflegends.com/cdn/10.14.1/img/profileicon/" + extras.getInt("profileIconNumber") + ".png";
        Picasso.get().setLoggingEnabled(true);
        Picasso.get().load(iconAddress).resize(150, 150).centerCrop().into(summonerIcon);

        for (int i = 0; i < 3; i++) {
            String championName = trimTraitName(championWithItems.get(i).getChamiponName()).toLowerCase();
            String occurrences = "" + championWithItems.get(i).getOccurrences();
            if (i == 0) {
                championName1.append(championName);
                championOccurrences1.append(occurrences);
                int championImageId = getResources().getIdentifier(championName, "drawable", getPackageName());
                championImage1.setImageResource(championImageId);
                int itemImagePath1 = 0, itemImagePath2 = 0, itemImagePath3 = 0;
                if (championWithItems.get(i).getItemLists().size() > 0) {
                    itemImagePath1 = getResources().getIdentifier("item_" + championWithItems.get(i).getItemLists().get(0), "drawable", getPackageName());
                }
                if (championWithItems.get(i).getItemLists().size() > 1) {
                    itemImagePath2 = getResources().getIdentifier("item_" + championWithItems.get(i).getItemLists().get(1), "drawable", getPackageName());
                }
                if (championWithItems.get(i).getItemLists().size() > 2) {
                    itemImagePath3 = getResources().getIdentifier("item_" + championWithItems.get(i).getItemLists().get(2), "drawable", getPackageName());
                }
                if (itemImagePath1 != 0) {
                    championItem1_1.setImageResource(itemImagePath1);
                }
                if (itemImagePath2 != 0) {
                    championItem1_2.setImageResource(itemImagePath2);
                }
                if (itemImagePath3 != 0) {
                    championItem1_3.setImageResource(itemImagePath3);
                }
            }
            else if (i == 1) {
                championName2.append(championName);
                championOccurrences2.append(occurrences);
                int championImageId = getResources().getIdentifier(championName, "drawable", getPackageName());
                championImage2.setImageResource(championImageId);
                int itemImagePath1 = 0, itemImagePath2 = 0, itemImagePath3 = 0;
                if (championWithItems.get(i).getItemLists().size() > 0) {
                    itemImagePath1 = getResources().getIdentifier("item_" + championWithItems.get(i).getItemLists().get(0), "drawable", getPackageName());
                }
                if (championWithItems.get(i).getItemLists().size() > 1) {
                    itemImagePath2 = getResources().getIdentifier("item_" + championWithItems.get(i).getItemLists().get(1), "drawable", getPackageName());
                }
                if (championWithItems.get(i).getItemLists().size() > 2) {
                    itemImagePath3 = getResources().getIdentifier("item_" + championWithItems.get(i).getItemLists().get(2), "drawable", getPackageName());
                }
                if (itemImagePath1 != 0) {
                    championItem2_1.setImageResource(itemImagePath1);
                }
                if (itemImagePath2 != 0) {
                    championItem2_2.setImageResource(itemImagePath2);
                }
                if (itemImagePath3 != 0) {
                    championItem2_3.setImageResource(itemImagePath3);
                }
            }
            else {
                championName3.append(championName);
                championOccurrences3.append(occurrences);
                int championImageId = getResources().getIdentifier(championName, "drawable", getPackageName());
                championImage3.setImageResource(championImageId);
                int itemImagePath1 = 0, itemImagePath2 = 0, itemImagePath3 = 0;
                if (championWithItems.get(i).getItemLists().size() > 0) {
                    itemImagePath1 = getResources().getIdentifier("item_" + championWithItems.get(i).getItemLists().get(0), "drawable", getPackageName());
                }
                if (championWithItems.get(i).getItemLists().size() > 1) {
                    itemImagePath2 = getResources().getIdentifier("item_" + championWithItems.get(i).getItemLists().get(1), "drawable", getPackageName());
                }
                if (championWithItems.get(i).getItemLists().size() > 2) {
                    itemImagePath3 = getResources().getIdentifier("item_" + championWithItems.get(i).getItemLists().get(2), "drawable", getPackageName());
                }
                if (itemImagePath1 != 0) {
                    championItem3_1.setImageResource(itemImagePath1);
                }
                if (itemImagePath2 != 0) {
                    championItem3_2.setImageResource(itemImagePath2);
                }
                if (itemImagePath3 != 0) {
                    championItem3_3.setImageResource(itemImagePath3);
                }
            }
        }
    }

    private void linkId() {
        levelView = (TextView) findViewById(R.id.summoner_level);
        summonerNameView = (TextView) findViewById(R.id.summoner_name);
        summonerIcon = (ImageView) findViewById(R.id.summoner_icon);
        championName1 = (TextView) findViewById(R.id.champion_name_1);
        championName2 = (TextView) findViewById(R.id.champion_name_2);
        championName3 = (TextView) findViewById(R.id.champion_name_3);
        championOccurrences1 = (TextView) findViewById(R.id.champion_used_1);
        championOccurrences2 = (TextView) findViewById(R.id.champion_used_2);
        championOccurrences3 = (TextView) findViewById(R.id.champion_used_3);
        championImage1 = (ImageView) findViewById(R.id.champion_image_1);
        championImage2 = (ImageView) findViewById(R.id.champion_image_2);
        championImage3 = (ImageView) findViewById(R.id.champion_image_3);
        championItem1_1 = (ImageView) findViewById(R.id.list_item_1_1);
        championItem1_2 = (ImageView) findViewById(R.id.list_item_1_2);
        championItem1_3 = (ImageView) findViewById(R.id.list_item_1_3);
        championItem2_1 = (ImageView) findViewById(R.id.list_item_2_1);
        championItem2_2 = (ImageView) findViewById(R.id.list_item_2_2);
        championItem2_3 = (ImageView) findViewById(R.id.list_item_2_3);
        championItem3_1 = (ImageView) findViewById(R.id.list_item_3_1);
        championItem3_2 = (ImageView) findViewById(R.id.list_item_3_2);
        championItem3_3 = (ImageView) findViewById(R.id.list_item_3_3);
    }

    private void analyzeMatchdatas() {
        for (int i = 0; i < matchDto.size(); i++) {
            for (int j = 0; j < 8; j++) {
                int userPlacement = matchDto.get(i).getInfo().getParticipants().get(j).getPlacement();
                //TODO: Change this from First place to User's deck (STILL WAITING FOR RIOT API BE FIXED)
                if (userPlacement == 1) {
                    for (int k = 0; k < matchDto.get(i).getInfo().getParticipants().get(j).getUnits().size(); k++) {
                        String unitName = matchDto.get(i).getInfo().getParticipants().get(j).getUnits().get(k).getCharacter_id();
                        List<Integer> itemLists = matchDto.get(i).getInfo().getParticipants().get(j).getUnits().get(k).getItems();
                        //Store UnitNames
                        AtomicBoolean checkIfChampionListUpdated = new AtomicBoolean(false);

                        for (int m = 0; m < championWithItems.size(); m++) {
                            if (championWithItems.get(m).getChamiponName().compareTo(unitName) == 0) {
                                championWithItems.get(m).setOccurrences(championWithItems.get(m).getOccurrences() + 1);
                                for (int l = 0; l < itemLists.size(); l++) {
                                    championWithItems.get(m).addItem(itemLists.get(l));
                                }
                                checkIfChampionListUpdated.set(true);
                            }
                        }
                        if (!checkIfChampionListUpdated.get()) {
                            ChampionWithItems temp = new ChampionWithItems(unitName, 1, itemLists);
                            championWithItems.add(temp);
                        }
                    }
                }
            }
        }
        //Sort championWithItems Arraylist
        Collections.sort(championWithItems);
        Collections.reverse(championWithItems);
    }

    private String trimTraitName(String trait) {
        if (trait.contains("tft") || trait.contains("TFT")) {
            trait = trait.substring(5);
        }
        return trait;
    }
}