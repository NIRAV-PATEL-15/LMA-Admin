package com.lma.Adminapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Timetable_Adapter extends RecyclerView.Adapter<Timetable_Adapter.ViewHolder> {
    private ArrayList<tt_holder> tt_array;
    private Context context;
    int lastpos = -1;
    private TimetableClick timetableClick;

    public Timetable_Adapter(ArrayList<tt_holder> tt_array, Context context, TimetableClick timetableClick) {
        this.tt_array = tt_array;
        this.context = context;
        this.timetableClick = timetableClick;
    }

    @NonNull
    @Override
    public Timetable_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_timetable_card,parent,false);
return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Timetable_Adapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        tt_holder th = tt_array.get(position);
        holder.lecno.setText(th.getLec_no());
        holder.sub_name.setText("Sub : "+th.getSub_name());
        holder.sub_code.setText("Code : "+th.getSub_code());
        holder.fac_name.setText("Faculty: "+th.getFaculty());
        holder.time.setText("Time : "+th.getTime());
        setAnimation(holder.itemView,position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              timetableClick.onTTclick(position);
            }
        });

    }

    private void setAnimation(View itemView, int position) {
        if(position > lastpos){
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            itemView.setAnimation(animation);
            lastpos = position;
        }
    }

    @Override
    public int getItemCount() {
        return tt_array.size();
    }
    public interface TimetableClick{
        void onTTclick(int position);
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView lecno,sub_name,sub_code,fac_name,time;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lecno = itemView.findViewById(R.id.ttd_lecno);
            sub_name = itemView.findViewById(R.id.ttd_sub_name);
            sub_code = itemView.findViewById(R.id.ttd_sub_code);
            fac_name = itemView.findViewById(R.id.ttd_fac_name);
            time = itemView.findViewById(R.id.ttd_time);
        }
    }

}
