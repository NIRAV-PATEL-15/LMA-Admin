package com.lma.Adminapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.splashscreen.SplashScreen;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
CardView manage_profile,manage_Timetable,create_course,manage_course,add_student,student_list;

private DrawerLayout drawerLayout;
private ActionBarDrawerToggle toggle;
private NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        setContentView(R.layout.activity_main);
//        Objects.requireNonNull(getSupportActionBar()).setTitle("Dashboard");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
//        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3E5D7C")));
        manage_profile = findViewById(R.id.cv_1);
        manage_Timetable = findViewById(R.id.cv_2);
        create_course = findViewById(R.id.cv_3);
        manage_course = findViewById(R.id.cv_4);
        add_student = findViewById(R.id.cv_5);
        student_list = findViewById(R.id.cv_6);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                splashScreen.setKeepOnScreenCondition(new SplashScreen.KeepOnScreenCondition() {
                    @Override
                    public boolean shouldKeepOnScreen() {
                        return false;
                    }
                });
            }
        },3000);
        manage_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });
        manage_Timetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        create_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        manage_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        add_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,Add_students.class);
                startActivity(i);
            }
        });
        



    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(toggle.onOptionsItemSelected(item)){
            return true;
        }
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_mp:
                Toast.makeText(this, "Manage profile", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_tt:
                Toast.makeText(this, "Manage Time-table", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_cc:
                Toast.makeText(this, "Create Course", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_mc:
                Toast.makeText(this, "Manage course", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_as:
                Toast.makeText(this, "Add Student", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_sl:
                Toast.makeText(this, "Student Details", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_appinfo:
                Toast.makeText(this, "App info", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_lgo:
                Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }
}