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

public class AddstudentsRVAdapter extends RecyclerView.Adapter<AddstudentsRVAdapter.ViewHolder> {
    private ArrayList<addstudentsmodel> addstudentsmodelArrayList;
    private Context context;
    int lastPos = -1;
    private addstudentsClickInterface addstudentsClickInterface;

    public AddstudentsRVAdapter(ArrayList<addstudentsmodel> addstudentsmodelArrayList, Context context, AddstudentsRVAdapter.addstudentsClickInterface addstudentsClickInterface) {
        this.addstudentsmodelArrayList = addstudentsmodelArrayList;
        this.context = context;
        this.addstudentsClickInterface = addstudentsClickInterface;
    }

    @NonNull
    @Override
    public AddstudentsRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.addstudents_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddstudentsRVAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        addstudentsmodel addstudentsmodel = addstudentsmodelArrayList.get(position);
        holder.Fullname.setText(addstudentsmodel.getFullname());
        holder.Enrollmentno.setText(addstudentsmodel.getEnrollmentno());
        holder.Semester.setText("Semester: "+addstudentsmodel.getSemester());
        holder.Division.setText("Class: "+addstudentsmodel.getDivision());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addstudentsClickInterface.onstudentsClick(position);
            }
        });
    }

    private void setAnimation(View itemView,int position){
        if (position>lastPos){
            Animation animation = AnimationUtils.loadAnimation(context,android.R.anim.slide_in_left);
            itemView.setAnimation(animation);
            lastPos = position;
        }
    }
    @Override
    public int getItemCount() {
        return addstudentsmodelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView Fullname,Enrollmentno,Semester,Division;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Fullname = itemView.findViewById(R.id.txtfullname);
            Enrollmentno = itemView.findViewById(R.id.txtenrollmentno);
            Semester = itemView.findViewById(R.id.txtsemester);
            Division = itemView.findViewById(R.id.txtdivision);
        }
    }

    public interface addstudentsClickInterface{
        void onstudentsClick(int position);
    }
}
