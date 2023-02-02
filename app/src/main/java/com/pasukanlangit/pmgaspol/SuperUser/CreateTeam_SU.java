package com.pasukanlangit.pmgaspol.SuperUser;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.pasukanlangit.pmgaspol.R;

import java.util.Objects;

public class CreateTeam_SU extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_create_team_su);

        // Close Create Team
        findViewById(R.id.imgclose_create).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CreateTeam_SU.this, Teams_SU.class));
                finish();
            }
        });
    }
}