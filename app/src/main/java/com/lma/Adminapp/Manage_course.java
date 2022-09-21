package com.lma.Adminapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;

public class Manage_course extends AppCompatActivity implements Course_adapter.CourseClick{
    private RecyclerView crv;

    private FirebaseDatabase fdb;

    private DatabaseReference dref;
    private ArrayList<Course_Model> course_model;
    private LinearLayout bottom;
    private Course_adapter course_adapter;
    private ProgressBar loading;
    private  TextView name, subname, subcode, faculty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_course);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Manage Courses");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3E5D7C")));
        crv = findViewById(R.id.CourseRV);
        loading = findViewById(R.id.mc_loading);
        loading.setVisibility(View.VISIBLE);
        course_model = new ArrayList<>();
        course_adapter = new Course_adapter(course_model,this,this);
        crv.setLayoutManager(new LinearLayoutManager(this));
        crv.setAdapter(course_adapter);
        fdb = FirebaseDatabase.getInstance();
        dref = fdb.getReference("Courses");
        getData();
    }

    private void getData() {
        course_model.clear();
        dref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loading.setVisibility(View.GONE);
                course_model.add(snapshot.getValue(Course_Model.class));
                course_adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loading.setVisibility(View.GONE);
                course_adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                loading.setVisibility(View.GONE);
                course_adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loading.setVisibility(View.GONE);
                course_adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                loading.setVisibility(View.GONE);
                course_adapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void onCOurseClick(int position) {
        Toast.makeText(this, "Clicked Successfully", Toast.LENGTH_SHORT).show();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}