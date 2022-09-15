package com.lma.Adminapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class TT_display extends AppCompatActivity implements Tt_Adapter.TimetableClick {
private RecyclerView rv;
private FloatingActionButton fab;
private FirebaseDatabase fdb;
private MaterialButton day_btn;
private DatabaseReference dref;
private ArrayList<ttHolder> ttHolderArrayList;
private RelativeLayout homeRL;
private  Tt_Adapter tt_adapter;
private Spinner ttd_day;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tt_display);
        rv = findViewById(R.id.ttRV);
        fab = findViewById(R.id.floating_add);
        fdb = FirebaseDatabase.getInstance();

        ttd_day = findViewById(R.id.ttd_days);
        ArrayAdapter<CharSequence> a1 = ArrayAdapter.createFromResource(this,
                R.array.days, R.layout.branch_spinner);
        a1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ttd_day.setAdapter(a1);
        String day = ttd_day.getSelectedItem().toString();
        dref = fdb.getReference("Time-Table").child(day);

        ttHolderArrayList = new ArrayList<>();
        tt_adapter = new Tt_Adapter(ttHolderArrayList,this,this);
rv.setLayoutManager(new LinearLayoutManager(this));
rv.setAdapter(tt_adapter);
fab.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {startActivity(new Intent(TT_display.this,Add_TimeTable.class));
    }
});
getallData();

    }

    private void getallData() {
        ttHolderArrayList.clear();
        dref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ttHolderArrayList.add(snapshot.getValue(ttHolder.class));
                tt_adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                tt_adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                tt_adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                tt_adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                tt_adapter.notifyDataSetChanged();

            }
        });
    }

    @Override
    public void onTTclick(int position) {

    }
}