package com.lma.Adminapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class Student_details extends AppCompatActivity {

    private TextView fullname,username,email,gender,dob,phone,semester,division,branch;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private Student_Model student_model;
    private ProgressBar loading;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);

        //SupportActionBar
        Objects.requireNonNull(getSupportActionBar()).setTitle("Student Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3E5D7C")));
        getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.bg));



        fullname = findViewById(R.id.sd_fullname);
        username = findViewById(R.id.sd_username);
        email = findViewById(R.id.sd_email);
        gender = findViewById(R.id.sd_gender);
        dob = findViewById(R.id.sd_dob);
        phone = findViewById(R.id.sd_phone);
        semester = findViewById(R.id.sd_semester);
        division = findViewById(R.id.sd_division);
        branch = findViewById(R.id.sd_branch);


        student_model = getIntent().getParcelableExtra("Students");
        if (student_model != null) {
            fullname.setText(student_model.getFullname());
            username.setText(student_model.getUsername());
            email.setText(student_model.getEmail());
            gender.setText(student_model.getGender());
            dob.setText(student_model.getDob());
            phone.setText(student_model.getPhone());
            semester.setText(student_model.getSemester());
            division.setText(student_model.getDivision());
            branch.setText(student_model.getBranch());

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
            case R.id.dashboard:
                //Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Student_details.this,Students_list.class);
                startActivity(intent);
                break;
            case R.id.help:
                Toast.makeText(this, "Help me", Toast.LENGTH_SHORT).show();
                break;
            case R.id.logout:
                Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);


    }

}