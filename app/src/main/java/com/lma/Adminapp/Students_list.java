package com.lma.Adminapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

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
    private LinearLayout addstudentsll,bottomsheetstudents;
    private ArrayList<Student_Model> studentModelArrayList;
    private Student_adapter addstudentsRVAdapter;
private ProgressBar loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_list);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Students List");
loading = findViewById(R.id.sl_loading);
loading.setVisibility(View.VISIBLE);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3E5D7C")));
        addstudentsRV = findViewById(R.id.asrecyclerview);
//        addstudentsll = findViewById(R.id.asrecyclerviewrl);
        bottomsheetstudents = findViewById(R.id.studentsbottomsheet);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Add students");
        studentModelArrayList = new ArrayList<>();
        addstudentsRVAdapter = new Student_adapter(studentModelArrayList,this,this);
        addstudentsRV.setLayoutManager(new LinearLayoutManager(this));
        addstudentsRV.setAdapter(addstudentsRVAdapter);
        getAllstudents();
    }
    private void getAllstudents(){
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
    private void displayBottomSheet(Student_Model Student_Model){
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View layout = LayoutInflater.from(this).inflate(R.layout.student_bottomsheet,bottomsheetstudents);
        bottomSheetDialog.setContentView(layout);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();

        TextView fullnamebs = layout.findViewById(R.id.txtfullname);
        TextView enrollmentnobs = layout.findViewById(R.id.txtenrollmentno);
        TextView semesterbs = layout.findViewById(R.id.txtsemester);
        TextView divisionbs = layout.findViewById(R.id.txtdivision);
        Button editstudents = layout.findViewById(R.id.addstudentedit_btn);
        Button detalisstudents = layout.findViewById(R.id.addstudentdatails_btn);

        fullnamebs.setText(Student_Model.getFullname());
        enrollmentnobs.setText(Student_Model.getEnrollmentno());
        semesterbs.setText("Semester : "+ Student_Model.getSemester());
        divisionbs.setText("Class : "+ Student_Model.getDivision());

        editstudents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Students_list.this, Edit_students.class);
                i.putExtra("Add students", Student_Model);
                startActivity(i);
            }
        });

        detalisstudents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(Student_Model.getAddstudentsID()));
                startActivity(i);
            }
        });
    }
}