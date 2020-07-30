package com.example.tfthelper.Presenter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tfthelper.Model.Dto.MatchDto;
import com.example.tfthelper.Model.Dto.TraitDto;
import com.example.tfthelper.Model.Dto.UnitDto;
import com.example.tfthelper.Model.Dto.championLists;
import com.example.tfthelper.Model.Dto.champions;
import com.example.tfthelper.Model.Dto.traitLists;
import com.example.tfthelper.Model.Dto.traits;
import com.example.tfthelper.Model.Parsers.ChampionWithItems;
import com.example.tfthelper.Model.Parsers.DeckWithChampions;
import com.example.tfthelper.Model.Parsers.itemOccurrences;
import com.example.tfthelper.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;


public class AnalysisActivity extends AppCompatActivity {

    Bundle extras;

    private String traitName = "";

    private ArrayList<MatchDto> matchDto = new ArrayList<>();
    private ArrayList<ChampionWithItems> championWithItems = new ArrayList<>();
    private ArrayList<DeckWithChampions> deckWithChampions = new ArrayList<>();
    private List<UnitDto> units = new ArrayList<>();
    private ArrayList<String> unitNames = new ArrayList<>();
    private ArrayList<Integer> winRate = new ArrayList<>();
    private championLists championLists;
    private traitLists traitLists;
    private HashMap<String, Integer> synergy = new HashMap<>();
    private String puuid = "";

    private TextView levelView, summonerNameView;
    private ImageView summonerIcon;

    private TextView mostUsedDeck;
    private ImageView traitImage1, traitImage2, traitImage3;
    private ImageView traitChampionImage1, traitChampionImage2, traitChampionImage3, traitChampionImage4, traitChampionImage5, traitChampionImage6, traitChampionImage7, traitChampionImage8;

    private TextView championName1, championName2, championName3, championOccurrences1, championOccurrences2, championOccurrences3;
    private ImageView championImage1, championImage2, championImage3;
    private ImageView championItem1_1, championItem1_2, championItem1_3, championItem2_1, championItem2_2, championItem2_3, championItem3_1, championItem3_2, championItem3_3;

    private TextView averageWinRate;

    private BarChart barChart;
    private ArrayList barEntries;
    private BarData barData;
    private BarDataSet barDataSet;

    private PieChart pieChart;
    private ArrayList pieEntries;
    private PieData pieData;
    private PieDataSet pieDataSet;


    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_analysis);
            Intent intent = getIntent();
            extras = intent.getBundleExtra("information");
            puuid = extras.getString("userPuuid");
            for (int i = 0; i < 20; i++) {
                MatchDto temp = (MatchDto) extras.getParcelable("MatchData"+i);
                matchDto.add(temp);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        linkId();
        analyzeMatchdatas();
        winDeck();
        parseMostUsedDeckInfo();
        setup();
    }

    private void setup() {
        //User Profile Data
        levelView.append("" + extras.getLong("summonerLevel"));
        summonerNameView.append(extras.getString("summonerName"));
        //User Profile Image
        String iconAddress = "https://ddragon.leagueoflegends.com/cdn/10.14.1/img/profileicon/" + extras.getInt("profileIconNumber") + ".png";
        Picasso.get().setLoggingEnabled(true);
        Picasso.get().load(iconAddress).resize(450, 450).centerCrop().into(summonerIcon);
        //User's most used deck
        mostUsedDeck.append(" " + deckWithChampions.get(0).getTraitName());

        //User's most used deck champions and trait color
        int count = 3;
        for (Map.Entry<String, Integer> entry : synergy.entrySet()) {
            for (int i = 0; i < traitLists.getTraits().size(); i++) {
                if (entry.getKey().compareTo(traitLists.getTraits().get(i).getName()) == 0) {
                    for (int j = 0; j < traitLists.getTraits().get(i).getSets().size(); j++) {
                        if (entry.getValue() >= traitLists.getTraits().get(i).getSets().get(j).getMin() && entry.getValue() <= traitLists.getTraits().get(i).getSets().get(j).getMax()) {
                            if (count == 3) {
                                int imagePath = getResources().getIdentifier(traitLists.getTraits().get(i).getSets().get(j).getStyle(), "drawable", getPackageName());
                                traitImage1.setBackgroundResource(imagePath);
                                imagePath = getResources().getIdentifier(entry.getKey().replaceAll("\\s+","").toLowerCase(), "drawable", getPackageName());
                                traitImage1.setImageResource(imagePath);
                                count--;
                            }
                            else if (count == 2) {
                                int imagePath = getResources().getIdentifier(traitLists.getTraits().get(i).getSets().get(j).getStyle(), "drawable", getPackageName());
                                traitImage2.setBackgroundResource(imagePath);
                                imagePath = getResources().getIdentifier(entry.getKey().replaceAll("\\s+","").toLowerCase(), "drawable", getPackageName());
                                traitImage2.setImageResource(imagePath);
                                count--;
                            }
                            else if (count == 1) {
                                int imagePath = getResources().getIdentifier(traitLists.getTraits().get(i).getSets().get(j).getStyle(), "drawable", getPackageName());
                                traitImage3.setBackgroundResource(imagePath);
                                imagePath = getResources().getIdentifier(entry.getKey().replaceAll("\\s+","").toLowerCase(), "drawable", getPackageName());
                                traitImage3.setImageResource(imagePath);
                                count--;
                            }
                            else {
                                j = Integer.MAX_VALUE;
                                i = Integer.MAX_VALUE;
                            }
                        }
                    }
                }
            }
            if (count == 0) {
                break;
            }
        }

        //User's most used deck's champion
        ArrayList<ImageView> mostUsedChampionArrayList = new ArrayList<ImageView>(Arrays.asList(traitChampionImage1, traitChampionImage2, traitChampionImage3, traitChampionImage4, traitChampionImage5, traitChampionImage6, traitChampionImage7, traitChampionImage8));
        count = 0;
        for (String name : unitNames) {
            int resourceId = getResources().getIdentifier(name.toLowerCase(), "drawable", getPackageName());
            mostUsedChampionArrayList.get(count).setImageResource(resourceId);
            count++;
        }

        //For average Win Rate
        parseCharts();
        double average = winRate.stream().mapToDouble(val -> val).average().orElse(0.0);
        averageWinRate.append(" " + average);

        barDataSet = new BarDataSet(barEntries,"Win_Rate");
        barData = new BarData(barDataSet);
        barData.setBarWidth(1f);
        barChart.setData(barData);
        barDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        barDataSet.setValueTextColor(Color.WHITE);
        barDataSet.setValueTextSize(10f);
        barChart.setFitBars(true);
        barChart.getLegend().setEnabled(false);

        pieDataSet = new PieDataSet(pieEntries, "Win_Rate");
        pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieDataSet.setColors(new int[] {R.color.colorWin, R.color.colorLoss}, getApplicationContext());
        pieDataSet.setSliceSpace(2f);
        pieDataSet.setValueTextColor(Color.WHITE);
        pieDataSet.setValueTextSize(10f);
        pieChart.getLegend().setEnabled(false);

        //User's favorite Champions
        for (int i = 0; i < 3; i++) {
            String championName = trimSetName(championWithItems.get(i).getChamiponName()).toLowerCase();
            String occurrences = " " + championWithItems.get(i).getOccurrences();
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
            else if (i == 2) {
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
            else {
                break;
            }
        }
    }

    private void parseCharts() {
        pieEntries = new ArrayList<>();
        barEntries = new ArrayList<>();
        HashMap<Integer, Integer> winOccurrences = new HashMap<>();
        float win = 0, loss = 0;
        for (int temp : winRate) {
            if (temp == 1) {
                win += 1;
            }
            else {
                loss += 1;
            }
            boolean ifAdded = false;
            for (Map.Entry<Integer, Integer> eachElement : winOccurrences.entrySet()) {
                if (eachElement.getKey() == temp) {
                    ifAdded = true;
                    eachElement.setValue(eachElement.getValue()+1);
                }
            }
            if (!ifAdded) {
                winOccurrences.put(temp, 1);
            }
        }
        pieEntries.add(new PieEntry(win, 0));
        pieEntries.add(new PieEntry(loss, 1));

        for (Map.Entry<Integer, Integer> eachElement : winOccurrences.entrySet()) {
            barEntries.add(new BarEntry(eachElement.getKey(), eachElement.getValue()));
        }

    }

    private void linkId() {
        levelView = (TextView) findViewById(R.id.summoner_level);
        summonerNameView = (TextView) findViewById(R.id.summoner_name);
        summonerIcon = (ImageView) findViewById(R.id.summoner_icon);

        mostUsedDeck = (TextView) findViewById(R.id.most_used_deck_set);
        traitImage1 = (ImageView) findViewById(R.id.deck_set_image_1);
        traitImage2 = (ImageView) findViewById(R.id.deck_set_image_2);
        traitImage3 = (ImageView) findViewById(R.id.deck_set_image_3);
        traitChampionImage1 = (ImageView) findViewById(R.id.deck_set_champion_image_6);
        traitChampionImage2 = (ImageView) findViewById(R.id.deck_set_champion_image_1);
        traitChampionImage3 = (ImageView) findViewById(R.id.deck_set_champion_image_3);
        traitChampionImage4 = (ImageView) findViewById(R.id.deck_set_champion_image_);
        traitChampionImage5 = (ImageView) findViewById(R.id.deck_set_champion_image_4);
        traitChampionImage6 = (ImageView) findViewById(R.id.deck_set_champion_image_7);
        traitChampionImage7 = (ImageView) findViewById(R.id.deck_set_champion_image_2);
        traitChampionImage8 = (ImageView) findViewById(R.id.deck_set_champion_image_5);

        pieChart = findViewById(R.id.pie_chart);
        barChart = findViewById(R.id.average_win_rate_bar_chart);
        averageWinRate = (TextView) findViewById(R.id.average_win_rate_text);

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
        championItem1_2 = (ImageView) findViewById(R.id.list_item_2_1);
        championItem1_3 = (ImageView) findViewById(R.id.list_item_3_1);
        championItem2_1 = (ImageView) findViewById(R.id.list_item_1_2);
        championItem2_2 = (ImageView) findViewById(R.id.list_item_2_2);
        championItem2_3 = (ImageView) findViewById(R.id.list_item_3_2);
        championItem3_1 = (ImageView) findViewById(R.id.list_item_1_3);
        championItem3_2 = (ImageView) findViewById(R.id.list_item_2_3);
        championItem3_3 = (ImageView) findViewById(R.id.list_item_3_3);
    }

    private void winDeck() {
        for (int i = 0; i < matchDto.size(); i++) {
            traitName = "";
            for (int o =0; o < 8; o++) {
                if (matchDto.get(i).getInfo().getParticipants().get(o).getPuuid().contains(puuid)) {
                    winRate.add(matchDto.get(i).getInfo().getParticipants().get(o).getPlacement());
                    //Find most-used trait, not first two trait (below shows first two trait)
                    List<TraitDto> traits = matchDto.get(i).getInfo().getParticipants().get(o).getTraits();
                    units =  matchDto.get(i).getInfo().getParticipants().get(o).getUnits();
                    int index = traits.size();
                    //Parsing Trait Name
                    //No need to parse units, just pass onto deckWithChampions
                    for (int j = 1; j < index; ++j) {
                        TraitDto key = traits.get(j);
                        int k = j - 1;
                        while (k >= 0 && traits.get(k).getNum_units() > key.getNum_units()) {
                            traits.set(k + 1, traits.get(k));
                            k = k - 1;
                        }
                        traits.set(k + 1, key);
                    }
                    //Now, add traits to deckUsed. However, we need to think that some character may have same champions. Thus, make program for that (AND GO BACKWARD)
                    int count = 2;
                    ArrayList<String> traitFullName = new ArrayList<>();
                    if (traits.size() > 0) {
                        int length = traits.get(0).getNum_units();
                        for (int j = traits.size() - 1; j >= 0; j--) {
                            if (count != 0) {
                                if (length == traits.get(j).getNum_units()) {
                                    traitFullName.add(trimSetName((traits.get(j).getName())));
                                } else {
                                    traitFullName.add(trimSetName((traits.get(j).getName())));
                                    length = traits.get(j).getNum_units();
                                    count--;
                                }
                            } else {
                                Collections.sort(traitFullName);
                                String temp = "";
                                while (traitFullName.size() != 0) {
                                    temp = temp + " " +traitFullName.get(traitFullName.size() - 1);
                                    traitFullName.remove(traitFullName.size() - 1);
                                }
                                deckWithChampions.add(new DeckWithChampions(units, temp, 1));
                                j = 0;
                            }
                        }
                    }
                    o = 8;
                }
            }
        }
        sortMostUsedDeck();
        //Now, get data from raw/traits.json, then make a class for that so that setup() method can utilize those information and set appropriate background bg.png img
        Collections.sort(deckWithChampions);
        Collections.reverse(deckWithChampions);
    }

    private void sortMostUsedDeck() {
        ArrayList<DeckWithChampions> temp = new ArrayList<>();
        for (DeckWithChampions obj : deckWithChampions) {
            boolean check = false;
            for (DeckWithChampions obj1 : temp) {
                if (obj.getTraitName().compareTo(obj1.getTraitName()) == 0) {
                    check = true;
                    obj1.setOccurrences(obj1.getOccurrences()+1);
                }
            }
            if (!check) {
                temp.add(obj);
            }
        }
        deckWithChampions = null;
        deckWithChampions = temp;
    }

    private void parseMostUsedDeckInfo() {
        //For champions.json
        String line = "";
        try {
            InputStream inputStream = getResources().openRawResource(R.raw.champions);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder(inputStream.available());
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            line = stringBuilder.toString();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        championLists = gson.fromJson(line, championLists.class);

        //For traits.json
        line = "";
        try {
            InputStream inputStream = getResources().openRawResource(R.raw.traits);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder(inputStream.available());
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            line = stringBuilder.toString();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson1 = builder.create();
        traitLists = gson1.fromJson(line, traitLists.class);

        //Store Champion names
        //Dynamically add traitIcons
        //Plan: Create Map / HashMap and store traits by occurrences
        //Then, compare with Gson-created object to see which color matches
        List<UnitDto> units = deckWithChampions.get(0).getUnitDto();
        for (UnitDto unitDto : units) {
            unitNames.add(trimSetName(unitDto.getCharacter_id()));
            for (champions ch : championLists.getChampions()) {
                if (unitDto.getCharacter_id().compareTo(ch.getChampionId()) == 0) {
                    for (String trait : ch.getTraits()) {
                        boolean checkIfAdded = false;
                        for (Map.Entry<String, Integer> entry : synergy.entrySet()) {
                            if (entry.getKey().contains(trait)) {
                                entry.setValue(entry.getValue()+1);
                                checkIfAdded = true;
                            }
                        }
                        if (!checkIfAdded) {
                            synergy.put(trait, 1);
                        }
                    }
                }
            }
        }

        //Sort HashMap
        List<Map.Entry<String, Integer> > list =
                new LinkedList<Map.Entry<String, Integer> >(synergy.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2)
            {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        // put data from sorted list to hashmap
        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        synergy = temp;
        //Trim Picture from bg.png so that image has right background color.  (Using ClipDrawable)
        //https://stackoverflow.com/questions/18073588/androidhow-can-i-show-a-part-of-image

    }

    private void analyzeMatchdatas() {
        for (int i = 0; i < matchDto.size(); i++) {
            for (int j = 0; j < 8; j++) {
                int userPlacement = matchDto.get(i).getInfo().getParticipants().get(j).getPlacement();
                if (matchDto.get(i).getInfo().getParticipants().get(j).getPuuid().contains(puuid)) {
                    for (int k = 0; k < matchDto.get(i).getInfo().getParticipants().get(j).getUnits().size(); k++) {
                        String unitName = trimSetName(matchDto.get(i).getInfo().getParticipants().get(j).getUnits().get(k).getCharacter_id());
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
        //Sort Items
        for (int i = 0; i < championWithItems.size(); i++) {
            championWithItems.get(i).setItemLists(new itemOccurrences(championWithItems.get(i).getItemLists()).getItems());
        }
    }

    private String trimSetName(String trait) {
        if (trait.toLowerCase().contains("set") || trait.toLowerCase().contains("tft")) {
            trait = trait.substring(5);
        }
        return trait;
    }
}