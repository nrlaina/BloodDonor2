package com.example.blooddonor.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blooddonor.R;
import com.example.blooddonor.model.Appointment;

import java.util.ArrayList;

public class AppointmentNotiAdapter extends RecyclerView.Adapter<AppointmentNotiAdapter.DesignViewHolder> {

    ArrayList<Appointment> appointments;

    public AppointmentNotiAdapter(ArrayList<Appointment> appointments) {
        this.appointments = appointments;
    }

    @NonNull
    @Override
    public DesignViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.appointment_noti_item, parent, false);
        return new DesignViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DesignViewHolder holder, int position) {
        holder.tvLocation.setText(appointments.get(position).getLocation());
        holder.tvDate.setText(appointments.get(position).getDate());
        holder.tvTime.setText(appointments.get(position).getTime());

//        if(appointments.get(position).getAttendance()){
//
//            holder.card.setCardBackgroundColor(Color.parseColor("#0000FF"));
//        }
//        else{
//            holder.card.setCardBackgroundColor(Color.parseColor("#FF0000"));
//        }

        if (appointments.get(position).getStatus().equals("approved"))
            holder.imgViewStatus.setImageResource(R.drawable.check_status);
        else
            holder.imgViewStatus.setImageResource(R.drawable.cancel_status);
    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }

    public static class DesignViewHolder extends RecyclerView.ViewHolder {

        TextView tvLocation, tvDate, tvTime;
        ImageView imgViewStatus;
        CardView card;
        public DesignViewHolder(@NonNull View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.bgColor);
            tvLocation = itemView.findViewById(R.id.tvLocationNotiAppt);
            tvDate = itemView.findViewById(R.id.tvDateNotiAppt);
            tvTime = itemView.findViewById(R.id.tvTimeNotiAppt);
            imgViewStatus = itemView.findViewById(R.id.imgViewAppointmentStatus);
        }
    }
}
