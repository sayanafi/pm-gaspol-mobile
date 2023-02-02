package com.pasukanlangit.pmgaspol.GeneralUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.pasukanlangit.pmgaspol.R;
import com.pasukanlangit.pmgaspol.SuperUser.Dashboard_SU;
import com.pasukanlangit.pmgaspol.SuperUser.MemberList_SU;
import com.pasukanlangit.pmgaspol.SuperUser.Teams_SU;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_profile);
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