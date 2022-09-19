package com.lma.Adminapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RecyclerView_Add_Students extends AppCompatActivity implements AddstudentsRVAdapter.addstudentsClickInterface {

    private RecyclerView addstudentsRV;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private LinearLayout addstudentsll,bottomsheetstudents;
    private ArrayList<addstudentsmodel> addstudentsmodelArrayList;
    private AddstudentsRVAdapter addstudentsRVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_add_students);
        addstudentsRV = findViewById(R.id.asrecyclerview);
        addstudentsll = findViewById(R.id.asrecyclerviewll);
        bottomsheetstudents = findViewById(R.id.studentsbottomsheet);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Add students");
        addstudentsmodelArrayList = new ArrayList<>();
        addstudentsRVAdapter = new AddstudentsRVAdapter(addstudentsmodelArrayList,this,this);
        addstudentsRV.setLayoutManager(new LinearLayoutManager(this));
        addstudentsRV.setAdapter(addstudentsRVAdapter);
        getAllstudents();
    }
    private void getAllstudents(){
        addstudentsmodelArrayList.clear();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                addstudentsmodelArrayList.add(snapshot.getValue(addstudentsmodel.class));
                addstudentsRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                addstudentsRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                addstudentsRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                addstudentsRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onstudentsClick(int position) {
        displayBottomSheet(addstudentsmodelArrayList.get(position));
    }
    private void displayBottomSheet(addstudentsmodel addstudentsmodel){
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View layout = LayoutInflater.from(this).inflate(R.layout.addstudents_bottom_sheet_dialog,bottomsheetstudents);
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

        fullnamebs.setText(addstudentsmodel.getFullname());
        enrollmentnobs.setText(addstudentsmodel.getEnrollmentno());
        semesterbs.setText("Semester: "+addstudentsmodel.getSemester());
        divisionbs.setText("Class: "+addstudentsmodel.getDivision());

        editstudents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RecyclerView_Add_Students.this,Edit_Add_students.class);
                i.putExtra("Add students",addstudentsmodel);
                startActivity(i);
            }
        });

        detalisstudents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(addstudentsmodel.getAddstudentsID()));
                startActivity(i);
            }
        });
    }
}