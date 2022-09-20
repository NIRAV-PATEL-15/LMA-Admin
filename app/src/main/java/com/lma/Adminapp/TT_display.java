package com.lma.Adminapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
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
    private RelativeLayout bottom;
    private Tt_Adapter tt_adapter;
    private Spinner ttd_day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tt_display);
        rv = findViewById(R.id.ttRV);
        fab = findViewById(R.id.floating_add);
        fdb = FirebaseDatabase.getInstance();
        bottom = findViewById(R.id.tt_bottom);
        ttd_day = findViewById(R.id.ttd_days);
        ArrayAdapter<CharSequence> a1 = ArrayAdapter.createFromResource(this,
                R.array.days, R.layout.branch_spinner);
        a1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ttd_day.setAdapter(a1);
        String day = ttd_day.getSelectedItem().toString();
        dref = fdb.getReference("Time-Table").child(day);

        ttHolderArrayList = new ArrayList<>();
        tt_adapter = new Tt_Adapter(ttHolderArrayList, this, this);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(tt_adapter);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TT_display.this, Add_TimeTable.class));
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
        displayBottom(ttHolderArrayList.get(position));

    }

    private void displayBottom(ttHolder ttHolder) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View layout = LayoutInflater.from(this).inflate(R.layout.bottom_tt, bottom);
        bottomSheetDialog.setContentView(layout);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();
        TextView lec, subname, subcode, faculty, time;
        lec = findViewById(R.id.ttdb_lecno);
        subname = findViewById(R.id.ttdb_sub_name);
        subcode = findViewById(R.id.ttdb_sub_code);
        faculty = findViewById(R.id.ttdb_fac_name);
        time = findViewById(R.id.ttdb_time);
//        lec.setText(ttHolder.getLec_no());
//        subname.setText(ttHolder.getSub_name());
//        subcode.setText(ttHolder.getSub_code());
//        faculty.setText(ttHolder.getFaculty());
//        time.setText(ttHolder.getTime());
        Button edit, view;
        edit = findViewById(R.id.ttdb_edit);
        view = findViewById(R.id.ttdb_view);
//        edit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Intent i = new Intent(TT_display.this,Edit_TimeTable.class);
////            i.putExtra("Timetable",ttHolder);
////            startActivity(i);
//                Toast.makeText(TT_display.this, "Edit", Toast.LENGTH_SHORT).show();
//            }
//        });
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(TT_display.this, "View", Toast.LENGTH_SHORT).show();
//
//            }
//        });
    }
}