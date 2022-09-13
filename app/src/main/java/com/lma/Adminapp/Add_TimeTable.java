package com.lma.Adminapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class Add_TimeTable extends AppCompatActivity {
Spinner sday,stime;
private TextInputLayout lno_field,sub_name_field,sub_code_field,fac_name_field;
private MaterialButton add;
private FirebaseDatabase db;
private DatabaseReference dref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_time_table);
         sday = (Spinner) findViewById(R.id.sday);
        ArrayAdapter<CharSequence> a1 = ArrayAdapter.createFromResource(this,
                R.array.days, R.layout.branch_spinner);
        a1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sday.setAdapter(a1);
         stime = (Spinner) findViewById(R.id.stime);
        ArrayAdapter<CharSequence> a2 = ArrayAdapter.createFromResource(this,
                R.array.time, R.layout.branch_spinner);
        a2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stime.setAdapter(a2);
        Objects.requireNonNull(getSupportActionBar()).setTitle(" Timetable");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3E5D7C")));
lno_field = findViewById(R.id.tt_lec_no_field);
sub_name_field = findViewById(R.id.tt_sub_name_field);
sub_code_field = findViewById(R.id.tt_sub_code_field);
fac_name_field = findViewById(R.id.tt_faculty_name_field);
db =FirebaseDatabase.getInstance();
dref = db.getReference("Time-Table");
add = findViewById(R.id.add_tt_btn);
add.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        validate();
    }




});

    }

    private void validate() {
        String lec_no,sub_name,sub_code,faculty,time,day;
        lec_no = lno_field.getEditText().getText().toString();
        sub_name = sub_name_field.getEditText().getText().toString();
        sub_code = sub_code_field.getEditText().getText().toString();
        faculty = fac_name_field.getEditText().getText().toString();
        day = sday.getSelectedItem().toString();
        time = stime.getSelectedItem().toString();
        if(!TextUtils.isEmpty(lec_no)){
            lno_field.setError(null);
            lno_field.setErrorEnabled(false);
            if(!TextUtils.isEmpty(sub_name)){
                sub_name_field.setError(null);
                sub_name_field.setErrorEnabled(false);
                if(!TextUtils.isEmpty(sub_code)){
                    sub_code_field.setError(null);
                    sub_code_field.setErrorEnabled(false);
                    if(!TextUtils.isEmpty(faculty)){
                        fac_name_field.setError(null);
                        fac_name_field.setErrorEnabled(false);
                        if(!time.equals("Select Time")){
                            if(!day.equals("Select Day")){
                                StoreToFirebase();

                            }else{
                                Toast.makeText(this, "Please select a Day", Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            Toast.makeText(this, "Please select a Time", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        fac_name_field.setError("Faculty name Cannot be empty");
                    }
                }else{
                    sub_code_field.setError("Code Cannot be empty");
                }
            }else{
                sub_name_field.setError("Name Cannot be empty");
            }
        }else{
            lno_field.setError("No Cannot be empty");
        }

    }

    private void StoreToFirebase() {
        String lec_no,sub_name,sub_code,faculty,time,day;
        lec_no = lno_field.getEditText().getText().toString();
        sub_name = sub_name_field.getEditText().getText().toString();
        sub_code = sub_code_field.getEditText().getText().toString();
        faculty = fac_name_field.getEditText().getText().toString();
        day = sday.getSelectedItem().toString();
        time = stime.getSelectedItem().toString();
        ttHolder ttHolder = new ttHolder(lec_no,sub_name,sub_code,faculty,time,day);
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dref.child(day).child(lec_no).setValue(ttHolder);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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