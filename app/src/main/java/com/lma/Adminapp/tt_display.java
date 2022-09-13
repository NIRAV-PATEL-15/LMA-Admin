package com.lma.Adminapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.RelativeLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class tt_display extends AppCompatActivity {
private RecyclerView rv;
private FloatingActionButton fab;
private FirebaseDatabase fdb;
private ArrayList<ttHolder> ttHolderArrayList;
private RelativeLayout homeRL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tt_display);
    }
}