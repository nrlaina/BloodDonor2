package com.example.blooddonor.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blooddonor.R;
import com.example.blooddonor.model.Appointment;

import java.util.ArrayList;

public class AppoinmentAdapter extends RecyclerView.Adapter<AppoinmentAdapter.DesignViewHolder> {

    ArrayList<Appointment> appointments;
    private OnDeleteAppointmentListener onDeleteAppointmentListener;

    public AppoinmentAdapter(ArrayList<Appointment> appointments, OnDeleteAppointmentListener onDeleteAppointmentListener) {
        this.appointments = appointments;
        this.onDeleteAppointmentListener = onDeleteAppointmentListener;
    }

    @NonNull
    @Override
    public DesignViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.appointment_item, parent, false);
        return new DesignViewHolder(view, onDeleteAppointmentListener);
    }

    @Override
    public void onBindViewHolder(@NonNull DesignViewHolder holder, int position) {
        holder.location.setText(appointments.get(position).getLocation());
        holder.date.setText(appointments.get(position).getDate());
        holder.time.setText(appointments.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }

    public class DesignViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener{

        TextView location, date, time;
        OnDeleteAppointmentListener onDeleteAppointmentListener;

        public DesignViewHolder(@NonNull View itemView, OnDeleteAppointmentListener onDeleteAppointmentListener) {
            super(itemView);

            this.onDeleteAppointmentListener = onDeleteAppointmentListener;

            location = itemView.findViewById(R.id.tvLocationAppt);
            date = itemView.findViewById(R.id.tvDateAppt);
            time = itemView.findViewById(R.id.tvTimeAppt);

            itemView.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View view) {
            onDeleteAppointmentListener.onAppointmentDelete(getAbsoluteAdapterPosition(),appointments.get(getAbsoluteAdapterPosition()));
            return true;
        }
    }

    public interface OnDeleteAppointmentListener {
        void onAppointmentDelete(int position, Appointment appointment);
    }
}
