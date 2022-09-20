package com.lma.Adminapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

import java.util.HashMap;
import java.util.Map;

public class Edit_Add_students extends AppCompatActivity {

    //Spinner addstudent_branch_spinner;
    private TextInputEditText fullnameedt,enrollmentnoedt,emailedt,passwordedt,cpasswordedt,dobedt,phoneedt,semesteredt,divisionedt;
    private Button updeteaddstudentsbtn,deleteaddstudentsbtn;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String addstudentsID;
    private addstudentsmodel addstudentsmodel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_students);

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
        addstudentsmodel = getIntent().getParcelableExtra("Add students");
        if (addstudentsmodel!=null){
            fullnameedt.setText(addstudentsmodel.getFullname());
            enrollmentnoedt.setText(addstudentsmodel.getEnrollmentno());
            emailedt.setText(addstudentsmodel.getEmail());
            passwordedt.setText(addstudentsmodel.getPassword());
            cpasswordedt.setText(addstudentsmodel.getConfirmpassword());
            dobedt.setText(addstudentsmodel.getDob());
            phoneedt.setText(addstudentsmodel.getPhone());
            semesteredt.setText(addstudentsmodel.getSemester());
            divisionedt.setText(addstudentsmodel.getDivision());
            addstudentsID = addstudentsmodel.getAddstudentsID();
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
                        Toast.makeText(Edit_Add_students.this, "Students Details Updated..", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Edit_Add_students.this,RecyclerView_Add_Students.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(Edit_Add_students.this, "Fail to Update Students Details..", Toast.LENGTH_SHORT).show();;
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
        startActivity(new Intent(Edit_Add_students.this,RecyclerView_Add_Students.class));
    }
}