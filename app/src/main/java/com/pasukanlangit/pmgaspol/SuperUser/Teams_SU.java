package com.pasukanlangit.pmgaspol.SuperUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.pasukanlangit.pmgaspol.GeneralUser.Profile;
import com.pasukanlangit.pmgaspol.Login;
import com.pasukanlangit.pmgaspol.R;
import com.pasukanlangit.pmgaspol.Register;

import java.util.Objects;

public class Teams_SU extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_teams_su);

        // Go To Create Team Page
        findViewById(R.id.btnCreateTeam).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Teams_SU.this, CreateTeam_SU.class));
            }
        });


        // Initialize and assign variable
        BottomNavigationView bottomNavigationView=findViewById(R.id.nav_view);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.home);

        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.navigation_home:
                        startActivity(new Intent(getApplicationContext(), Dashboard_SU.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navigation_listteam:
                        startActivity(new Intent(getApplicationContext(), Teams_SU.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navigation_listmember:
                        startActivity(new Intent(getApplicationContext(), MemberList_SU.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navigation_profile:
                        startActivity(new Intent(getApplicationContext(), Profile.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }
}