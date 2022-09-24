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
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Edit_students extends AppCompatActivity {

    //Spinner addstudent_branch_spinner;
    private TextInputEditText fullnameedt,enrollmentnoedt,emailedt,passwordedt,cpasswordedt,dobedt,phoneedt,semesteredt,divisionedt;
    private Button updeteaddstudentsbtn,deleteaddstudentsbtn;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String addstudentsID;
    private Student_Model Student_Model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_students);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Edit Students");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3E5D7C")));
        firebaseDatabase = FirebaseDatabase.getInstance();
        fullnameedt = findViewById(R.id.as_fullname);
        enrollmentnoedt = findViewById(R.id.as_enrollmentno);
        emailedt = findViewById(R.id.as_email);
        passwordedt = findViewById(R.id.as_password);
        cpasswordedt = findViewById(R.id.as_cpassword);
        phoneedt = findViewById(R.id.as_phn);
        dobedt = findViewById(R.id.as_dob);
        semesteredt = findViewById(R.id.as_semester);
        divisionedt = findViewById(R.id.as_division);
        updeteaddstudentsbtn = findViewById(R.id.addstudentupdate_btn);
        deleteaddstudentsbtn = findViewById(R.id.addstudentdelete_btn);
        Student_Model = getIntent().getParcelableExtra("Add students");
        if (Student_Model !=null){
            fullnameedt.setText(Student_Model.getFullname());
            enrollmentnoedt.setText(Student_Model.getEnrollmentno());
            emailedt.setText(Student_Model.getEmail());
            passwordedt.setText(Student_Model.getPassword());
            cpasswordedt.setText(Student_Model.getConfirmpassword());
            dobedt.setText(Student_Model.getDob());
            phoneedt.setText(Student_Model.getPhone());
            semesteredt.setText(Student_Model.getSemester());
            divisionedt.setText(Student_Model.getDivision());
            addstudentsID = Student_Model.getAddstudentsID();
        }
        databaseReference = firebaseDatabase.getReference("Add students").child(addstudentsID);
        updeteaddstudentsbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String fullname = fullnameedt.getText().toString();
                String enrollmentno = enrollmentnoedt.getText().toString();
                String email = emailedt.getText().toString();
                String password = passwordedt.getText().toString();
                String confirmpassword = cpasswordedt.getText().toString();
                String dob = dobedt.getText().toString();
                String phone = phoneedt.getText().toString();
                String semester = semesteredt.getText().toString();
                String division = divisionedt.getText().toString();

                Map<String,Object> map = new HashMap<>();
                map.put("fullname",fullname);
                map.put("enrollmentno",enrollmentno);
                map.put("email",email);
                map.put("password",password);
                map.put("confirmpassword",confirmpassword);
                map.put("dob",dob);
                map.put("phone",phone);
                map.put("semester",semester);
                map.put("division",division);
                map.put("addstudentsID",addstudentsID);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        databaseReference.updateChildren(map);
                        Toast.makeText(Edit_students.this, "Students Details Updated..", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Edit_students.this, Students_list.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(Edit_students.this, "Fail to Update Students Details..", Toast.LENGTH_SHORT).show();;
                    }
                });
            }
        });

        deleteaddstudentsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletestudents();
            }
        });

    }
    private void deletestudents(){
        databaseReference.removeValue();
        Toast.makeText(this, "Students Deleted..", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(Edit_students.this, Students_list.class));
    }
    //back to previous activity
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}