package com.lma.Adminapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
        Objects.requireNonNull(getSupportActionBar()).setTitle("Students Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3E5D7C")));
        getWindow().setNavigationBarColor(ContextCompat.getColor(this,R.color.bg));


//        loading = findViewById(R.id.sd_loading);
//        loading.setVisibility(View.VISIBLE);
//
//        fullname = findViewById(R.id.sd_fullname);
//        username = findViewById(R.id.sd_username);
//        email = findViewById(R.id.sd_email);
//        gender = findViewById(R.id.sd_gender);
//        dob = findViewById(R.id.sd_dob);
//        phone = findViewById(R.id.sd_phone);
//        semester = findViewById(R.id.sd_semester);
//        division = findViewById(R.id.sd_division);
//        branch = findViewById(R.id.sd_branch);
    }
//
//    @Override
//    protected void onStart() {
//        firebaseDatabase = FirebaseDatabase.getInstance();
//        mAuth = FirebaseAuth.getInstance();
//        String userid = mAuth.getCurrentUser().getUid().toString();
//        databaseReference = firebaseDatabase.getReference("Students").child(userid);
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                loading.setVisibility(View.GONE);
//                student_model = snapshot.getValue(Student_Model.class);
//                fullname.setText(student_model.getFullname());
//                username.setText(student_model.getUsername());
//                email.setText(student_model.getEmail());
//                gender.setText(student_model.getGender());
//                dob.setText(student_model.getDob());
//                phone.setText(student_model.getPhone());
//                semester.setText(student_model.getSemester());
//                division.setText(student_model.getDivision());
//                branch.setText(student_model.getBranch());
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                loading.setVisibility(View.GONE);
//                Toast.makeText(Student_details.this, "error "+error.toString(), Toast.LENGTH_SHORT).show();
//            }
//        });
//        super.onStart();
//    }
}