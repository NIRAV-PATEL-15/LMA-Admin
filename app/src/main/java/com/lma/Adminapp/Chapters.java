package com.lma.Adminapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;

public class Chapters extends AppCompatActivity implements Chapter_Adapter.ChapterClick {
    private RecyclerView rv;
    private FirebaseDatabase fdb;
    private DatabaseReference dref;
    private FloatingActionButton add_chp;
    private ArrayList<Chapter_Model> chapter_models;
    private LinearLayout bottom;
    private Chapter_Adapter chapter_adapter;
    private Course_Model course_model;
    private ProgressBar loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapters);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Chapters");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3E5D7C")));
        getWindow().setNavigationBarColor(ContextCompat.getColor(this,R.color.bg));
        rv = findViewById(R.id.ch_RV);
        loading = findViewById(R.id.ch_loading);
        loading.setVisibility(View.VISIBLE);
        bottom = findViewById(R.id.ch_bottom);
        add_chp = findViewById(R.id.add_chapter);
        course_model = getIntent().getParcelableExtra("subject");
        chapter_models = new ArrayList<>();
        chapter_adapter = new Chapter_Adapter(chapter_models,this,this);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(chapter_adapter);
        fdb = FirebaseDatabase.getInstance();
        dref = fdb.getReference("Courses").child("Android").child("content");
        add_chp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Chapters.this,Add_chapter.class));

            }
        });
        getAlldata();
    }

    private void getAlldata() {
        chapter_models.clear();
        dref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loading.setVisibility(View.GONE);
                chapter_models.add(snapshot.getValue(Chapter_Model.class));
                chapter_adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loading.setVisibility(View.GONE);
                chapter_adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                loading.setVisibility(View.GONE);
                chapter_adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loading.setVisibility(View.GONE);
                chapter_adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                loading.setVisibility(View.GONE);
                //chapter_adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onChapterClick(int position) {
//        display(chapter_models.get(position));
    }

    private void display(Chapter_Model chapter_model) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View layout = LayoutInflater.from(this).inflate(R.layout.bottom_chapter, bottom);
        bottomSheetDialog.setContentView(layout);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();
        Button del = layout.findViewById(R.id.ch_delete_btn);
        TextView title = layout.findViewById(R.id.chp_name);
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();
            }

            private void delete() {
                loading.setVisibility(View.GONE);
                dref.removeValue();
                Toast.makeText(Chapters.this, "Successfully Deleted", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Chapters.this,Chapters.class));
                finishAndRemoveTask();
            }
        });
        title.setText(course_model.getName());
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
                Intent intent = new Intent(Chapters.this,MainActivity.class);
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