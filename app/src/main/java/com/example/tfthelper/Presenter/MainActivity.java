package com.example.tfthelper.Presenter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.tfthelper.R;

public class MainActivity extends AppCompatActivity {

    private Button checkButton;
    private EditText summonerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linkId();
    }

    private void linkId() {
        checkButton = (Button) findViewById(R.id.check_button);
        summonerName = (EditText) findViewById(R.id.summoner_name);
    }
}