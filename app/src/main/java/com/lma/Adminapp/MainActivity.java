package com.lma.Adminapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.splashscreen.SplashScreen;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
CardView manage_profilecv,manage_Timetable,manage_course,add_student,student_list;
private FirebaseAuth mAuth;
private DatabaseReference dref;
private DrawerLayout drawerLayout;

private ActionBarDrawerToggle toggle;
private NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).setTitle("LMA_TEACHER");

        manage_profilecv = findViewById(R.id.cv_1);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3E5D7C")));

        manage_Timetable = findViewById(R.id.cv_2);

        manage_course = findViewById(R.id.cv_3);
        add_student = findViewById(R.id.cv_5);
        student_list = findViewById(R.id.cv_6);
        //Navigation
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        toggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(this);
        mAuth = FirebaseAuth.getInstance();
        dref = FirebaseDatabase.getInstance().getReference("Teachers").child(mAuth.getCurrentUser().getUid().toString());
//SplashScreen
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
        // Cardview onclick events
        manage_profilecv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(MainActivity.this,manage_profile.class));

            }
        });
        manage_Timetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Manage Time-table", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this,TT_display.class));



            }
        });
        manage_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Manage course", Toast.LENGTH_SHORT).show();


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



    //Onstart method to check weather the user is already logged in
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user ==null){
            Intent i = new Intent(MainActivity.this,StartScreen.class);
            startActivity(i);
            this.finish();


        }
        else{
            dref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    View view = navigationView.inflateHeaderView(R.layout.nav_drawer);
                    TextView username = view.findViewById(R.id.nav_username);
                    username.setText(snapshot.child("username").getValue().toString());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
//toogle for navigation
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(toggle.onOptionsItemSelected(item)){
            return true;
        }
        return true;

    }

// nav item selection events
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_appinfo:
                Intent i = new Intent(MainActivity.this,app_info.class);
                startActivity(i);
                break;
            case R.id.nav_lgo:
                mAuth.signOut();
                startActivity(new Intent(MainActivity.this,StartScreen.class));
                finish();
        }
        return true;
    }
    // to get username on navigation panel

}