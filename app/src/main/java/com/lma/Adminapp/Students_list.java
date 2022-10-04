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
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;

public class Students_list extends AppCompatActivity implements Student_adapter.addstudentsClickInterface {

    private RecyclerView addstudentsRV;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private LinearLayout  bottomsheetstudents;
    private ArrayList<Student_Model> studentModelArrayList;
    private Student_adapter addstudentsRVAdapter;
    private ProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_list);
        //SupportActionBar
        Objects.requireNonNull(getSupportActionBar()).setTitle("Students List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3E5D7C")));
        getWindow().setNavigationBarColor(ContextCompat.getColor(this,R.color.bg));
        // getting id's of the components
        addstudentsRV = findViewById(R.id.asrecyclerview);
        loading = findViewById(R.id.sl_loading);
        loading.setVisibility(View.VISIBLE);
        bottomsheetstudents = findViewById(R.id.studentsbottomsheet);
        //getting database reference
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Students");
        studentModelArrayList = new ArrayList<>();
        addstudentsRVAdapter = new Student_adapter(studentModelArrayList, this, this);
        addstudentsRV.setLayoutManager(new LinearLayoutManager(this));
        addstudentsRV.setAdapter(addstudentsRVAdapter);
        getAllstudents();
    }

    private void getAllstudents() {
        studentModelArrayList.clear();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loading.setVisibility(View.GONE);
                studentModelArrayList.add(snapshot.getValue(Student_Model.class));
                addstudentsRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loading.setVisibility(View.GONE);
                addstudentsRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                loading.setVisibility(View.GONE);

                addstudentsRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loading.setVisibility(View.GONE);

                addstudentsRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                loading.setVisibility(View.GONE);


            }
        });
    }

    @Override
    public void onstudentsClick(int position) {
        displayBottomSheet(studentModelArrayList.get(position));
    }

    private void displayBottomSheet(Student_Model Student_Model) {
        //setting up the bottomSheetDialog

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View layout = LayoutInflater.from(this).inflate(R.layout.student_bottomsheet, bottomsheetstudents);
        bottomSheetDialog.setContentView(layout);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();
//getting id's of components in bottomSheet
        TextView fullname = layout.findViewById(R.id.sb_fullname);
        TextView username = layout.findViewById(R.id.sb_username);
        TextView semester = layout.findViewById(R.id.sb_semester);
        TextView division = layout.findViewById(R.id.sb_division);
        Button edit = layout.findViewById(R.id.edit_btn);
        Button view = layout.findViewById(R.id.view_btn);
//setting data into Textviews
        fullname.setText(Student_Model.getFullname());
        username.setText(Student_Model.getUsername());
        semester.setText("Semester : " + Student_Model.getSemester());
        division.setText("Class : " + Student_Model.getDivision());

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Students_list.this, Edit_students.class);
                i.putExtra("Students", Student_Model);
                startActivity(i);
                finishAndRemoveTask();
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Students_list.this, Student_details.class);
                i.putExtra("Students", Student_Model);
                startActivity(i);
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