package com.pasukanlangit.pmgaspol.GeneralUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.pasukanlangit.pmgaspol.R;

import java.util.ArrayList;
import java.util.Objects;


public class Dashboard extends AppCompatActivity {

    private PieChart pieChart, legend;
    //private Legend legend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_dashboard);

        //SAY MY NAMEEE
        if (getSharedPreferences("PMGASPOL", 0) == null || !getSharedPreferences("PMGASPOL", 0).contains("user")) {
            startActivity(new Intent(Dashboard.this, com.pasukanlangit.pmgaspol.Login.class));
            finish();
        }

        ((TextView) findViewById(R.id.userfullname)).setText(getSharedPreferences("PMGASPOL", 0).getString("user", ""));

        //legend = findViewById(R.id.chartlegend);
//        pieChart = findViewById(R.id.chartoverview);
//        setupPieChart();
//        loadPieData();

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
                        startActivity(new Intent(getApplicationContext(),Dashboard.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navigation_listteam:
                        startActivity(new Intent(getApplicationContext(),Individual_task.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navigation_listmember:
                        startActivity(new Intent(getApplicationContext(),Team_task.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navigation_profile:
                        startActivity(new Intent(getApplicationContext(),Profile.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }

    /*private void loadLegend() {

    }

    private void setupPieChart() {
        pieChart.setDrawHoleEnabled(true);
//        pieChart.setUsePercentValues(false);
        pieChart.setCenterText("12");
//        pieChart.setEntryLabelTextSize(12);
//        pieChart.setEntryLabelColor(Color.WHITE);
        pieChart.setCenterTextSize(24);
        pieChart.getDescription().setEnabled(false);
        pieChart.setDrawEntryLabels(false);

        Legend l = pieChart.getLegend();
        l.setForm(Legend.LegendForm.CIRCLE);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(true);
        l.setEnabled(false);
    }

    //LOAD DATA TOTAL PROJECT YANG AKTIF SETIAP TEAM
    private void loadPieData() {
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(6,"Dewabiz"));
        entries.add(new PieEntry(4,"Iosys"));
        entries.add(new PieEntry(2,"Cashplus"));
        entries.add(new PieEntry(1,"ST24"));

        ArrayList<Integer> colors = new ArrayList<>();
        for (int color: ColorTemplate.MATERIAL_COLORS) {
            colors.add(color);
        }

        for (int color: ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color);
        }

        //TOTAL PROJECT SEMUA TEAM
        PieDataSet dataSet = new PieDataSet(entries, "12");
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setDrawValues(true);
        data.setValueFormatter(new PercentFormatter(pieChart));
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.BLACK);

        pieChart.setData(data);
        pieChart.invalidate();

    }*/
    }
