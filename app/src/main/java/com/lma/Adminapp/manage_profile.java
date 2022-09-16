package com.lma.Adminapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class manage_profile extends AppCompatActivity {

    private TextView username,fullname,email,gender,dob,phn,gradu;
    private Button ep;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference db;
    private FirebaseAuth mAuth;
    private  RegDataHolder rg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_profile);
        Objects.requireNonNull(getSupportActionBar()).setTitle(" Manage Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3E5D7C")));
        firebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();

        //getting userid from authentication

        String userid = mAuth.getCurrentUser().getUid().toString();
        ep = findViewById(R.id.get_btn);

        ep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(manage_profile.this, Edit_profile.class);
                i.putExtra("uid",userid);
                i.putExtra("fname",rg.getFullname());
                i.putExtra("username",rg.getUsername());
                i.putExtra("email",rg.getEmail());
                i.putExtra("gender",rg.getGender());
                i.putExtra("dob",rg.getDob());
                i.putExtra("contact",rg.getCno());
                i.putExtra("graduation",rg.getGraduation());
                startActivity(i);


            }
        });
// Fetching the textviews
        username = findViewById(R.id.mp_username);
        fullname = findViewById(R.id.mp_fullname);
        email = findViewById(R.id.mp_email);
        gender = findViewById(R.id.mp_gender);
        dob = findViewById(R.id.mp_dob);
        phn = findViewById(R.id.mp_contact);
        gradu = findViewById(R.id.mp_graduation);

        //getting vlaue from realtime database of particular user
        db = firebaseDatabase.getReference("Teachers").child(userid);
        //
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                rg = snapshot.getValue(RegDataHolder.class);
                    username.setText(rg.getUsername());
                    fullname.setText(rg.getFullname());
                    email.setText(rg.getEmail());
                    gender.setText(rg.getGender());
                    dob.setText(rg.getDob());
                    phn.setText(rg.getCno());
                    gradu.setText(rg.getGraduation());



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(manage_profile.this, "error "+error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}