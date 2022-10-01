package com.lma.Adminapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class Add_chapter extends AppCompatActivity {
private TextInputEditText title,link;
private Button upload;
private FirebaseDatabase db;
private DatabaseReference dref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_chapter);
        Objects.requireNonNull(getSupportActionBar()).setTitle(" Chapter");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3E5D7C")));
        getWindow().setNavigationBarColor(ContextCompat.getColor(this,R.color.bg));
        title = findViewById(R.id.ac_title);
        link = findViewById(R.id.ac_file);
        upload = findViewById(R.id.upload_btn);
        db =FirebaseDatabase.getInstance();
        dref = db.getReference("Courses").child("BI").child("content");

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Upload();
            }

            private void Upload() {
                String name,file;
                name = title.getText().toString();
                file = link.getText().toString();
                Chapter_Model chapter_model = new Chapter_Model(name,file);
                dref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                   dref.child(name).setValue(chapter_model);
                        Toast.makeText(Add_chapter.this, "added", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Add_chapter.this,Chapters.class));
                        finishAndRemoveTask();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(Add_chapter.this, "Failed", Toast.LENGTH_SHORT).show();


                    }
                });
            }
        });
    }
}