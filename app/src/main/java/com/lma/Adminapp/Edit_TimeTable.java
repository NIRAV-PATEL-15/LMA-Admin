package com.lma.Adminapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Edit_TimeTable extends AppCompatActivity {
    //Spinner sday, stime;
    private AutoCompleteTextView time_txt,day_txt;
    private TextInputEditText lno_txt,sub_name_txt,sub_code_txt,fac_name_txt;
    private TextInputLayout lno_field,sub_name_field,sub_code_field,fac_name_field,time_field,day_field;
    private MaterialButton update, delete;
    private FirebaseDatabase db;
    private String id;
    private DatabaseReference dref;
    private ttHolder ttHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_time_table);
        //SupportActionbar
        Objects.requireNonNull(getSupportActionBar()).setTitle("Edit Time-Table");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3E5D7C")));
        getWindow().setNavigationBarColor(ContextCompat.getColor(this,R.color.bg));
        //Spinner configs
//        sday = (Spinner) findViewById(R.id.sday);
//        ArrayAdapter<CharSequence> a1 = ArrayAdapter.createFromResource(this,
//                R.array.days, R.layout.branch_spinner);
//        a1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        sday.setAdapter(a1);
//        stime = (Spinner) findViewById(R.id.stime);
//        ArrayAdapter<CharSequence> a2 = ArrayAdapter.createFromResource(this,
//                R.array.time, R.layout.branch_spinner);
//        a2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        stime.setAdapter(a2);
        //getting id's of textviews

        lno_txt = findViewById(R.id.ett_lec_no);
        sub_name_txt = findViewById(R.id.ett_sub_name);
        sub_code_txt = findViewById(R.id.ett_sub_code);
        fac_name_txt = findViewById(R.id.ett_faculty_name);
        time_txt = findViewById(R.id.ett_time);
        day_txt = findViewById(R.id.ett_day);
        update = findViewById(R.id.update_tt_btn);
        delete = findViewById(R.id.delete_tt_btn);

        // Setting up data into the Edit-text boxes
        ttHolder = getIntent().getParcelableExtra("data");
        if (ttHolder != null) {
            lno_txt.setText(ttHolder.getLec_no());
            sub_name_txt.setText(ttHolder.getSub_code());
            sub_code_txt.setText(ttHolder.getSub_name());
            fac_name_txt.setText(ttHolder.getFaculty());
            id = ttHolder.getDay();
        }
//getting instance of database
        db = FirebaseDatabase.getInstance();
        dref = db.getReference("Time-Table").child(id).child(lno_txt.getText().toString());
        //update btn event listner
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataValidate();
            }


        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();
            }
        });
    }
    private void DataValidate() {
        lno_field = findViewById(R.id.ett_lec_no_field);
        sub_name_field = findViewById(R.id.ett_sub_name_field);
        sub_code_field = findViewById(R.id.ett_sub_code_field);
        fac_name_field = findViewById(R.id.ett_faculty_name_field);
        time_field = findViewById(R.id.ett_time_field);
        day_field = findViewById(R.id.ett_day_field);

        String lec_no,sub_name,sub_code,faculty,time,day;
        lec_no = lno_field.getEditText().getText().toString();
        sub_name = sub_name_field.getEditText().getText().toString();
        sub_code = sub_code_field.getEditText().getText().toString();
        faculty = fac_name_field.getEditText().getText().toString();
        time = time_field.getEditText().getText().toString();
        day = day_field.getEditText().getText().toString();
        //day = sday.getSelectedItem().toString();
        //time = stime.getSelectedItem().toString();
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
                        if(!TextUtils.isEmpty(time)){
                            time_field.setError(null);
                            time_field.setErrorEnabled(false);
                            if(!TextUtils.isEmpty(day)){
                                day_field.setError(null);
                                day_field.setErrorEnabled(false);
                                Update();

                            }else{
                                day_field.setError("Please Select Your Day");                            }

                        }else{
                            time_field.setError("Please Select Your Time");                        }
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
    //update function
    private void Update() {
//Getting the data into variables
        String lec_no = lno_txt.getText().toString();
        String subj_name = sub_name_txt.getText().toString();
        String subj_code = sub_code_txt.getText().toString();
        String faculty = fac_name_txt.getText().toString();
        String time = time_txt.getText().toString();
        String day = day_txt.getText().toString();
       // String day = sday.getSelectedItem().toString();
        //String time = stime.getSelectedItem().toString();
//mapping data into hashmap
        Map<String, Object> map = new HashMap<>();
        map.put("lec_no", lec_no);
        map.put("sub_name", subj_name);
        map.put("sub_code", subj_code);
        map.put("faculty", faculty);
        map.put("time", time);
        map.put("day", day);
        //value event listener
        dref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dref.updateChildren(map);
                Toast.makeText(Edit_TimeTable.this, "Successfully Updated", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Edit_TimeTable.this, TT_display.class));
                finishAndRemoveTask();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Edit_TimeTable.this, "Updation failed", Toast.LENGTH_SHORT).show();
            }
        });


    }

    //Delete function
    private void delete() {
        dref.removeValue();
        Toast.makeText(Edit_TimeTable.this, "Successfully Deleted", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(Edit_TimeTable.this, TT_display.class));
        finishAndRemoveTask();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}