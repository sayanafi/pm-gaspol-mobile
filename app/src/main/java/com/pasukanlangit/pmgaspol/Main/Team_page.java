package com.pasukanlangit.pmgaspol.Main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.pasukanlangit.pmgaspol.R;

public class Team_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_team_page);
    }
}