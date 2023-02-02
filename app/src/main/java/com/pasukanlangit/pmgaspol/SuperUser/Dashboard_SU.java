package com.pasukanlangit.pmgaspol.SuperUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.pasukanlangit.pmgaspol.GeneralUser.Dashboard;
import com.pasukanlangit.pmgaspol.GeneralUser.Individual_task;
import com.pasukanlangit.pmgaspol.GeneralUser.Profile;
import com.pasukanlangit.pmgaspol.GeneralUser.Team_task;
import com.pasukanlangit.pmgaspol.R;

import org.w3c.dom.Text;

import java.util.Objects;

public class Dashboard_SU extends AppCompatActivity {

    String nama;
    TextView displaynama;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_dashboard_su);
//        TextView = findViewById(R.id.userfullname);

        // Initialize Name
        SharedPreferences mSettings = getSharedPreferences("user", Context.MODE_PRIVATE);
        nama = mSettings.getString("nama",null);

//        TextView.setText(Integer.parseInt(nama));
//        getData();
//        ((TextView) findViewById(R.id.userfullname)).setText(getSharedPreferences("nama", 0).getString("user", ""));

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
