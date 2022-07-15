package com.example.blooddonor.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blooddonor.LoadingDialog;
import com.example.blooddonor.R;
import com.example.blooddonor.model.Donor;

import java.util.ArrayList;

public class DonorAdapter extends RecyclerView.Adapter<DonorAdapter.DesignViewHolder> {

    ArrayList<Donor> donors;
    OnDonorDetailsClickListener onDonorDetailsClickListener;

    public DonorAdapter(ArrayList<Donor> donors, OnDonorDetailsClickListener onDonorDetailsClickListener) {
        this.donors = donors;
        this.onDonorDetailsClickListener = onDonorDetailsClickListener;
    }

    @NonNull
    @Override
    public DonorAdapter.DesignViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.donor_item, parent, false);
        return new DesignViewHolder(view, onDonorDetailsClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull DonorAdapter.DesignViewHolder holder, int position) {
        holder.userName.setText(donors.get(position).getFullName());
        Log.d("name", " " + donors.get(position).getFullName());
        holder.location.setText(donors.get(position).getLocation());
        holder.status.setText(donors.get(position).getStatus());
    }

    @Override
    public int getItemCount() {
        return donors.size();
    }

    public class DesignViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView userName, location, status;
        OnDonorDetailsClickListener onDonorDetailsClickListener;

        public DesignViewHolder(@NonNull View itemView, OnDonorDetailsClickListener onDonorDetailsClickListener) {
            super(itemView);

            this.onDonorDetailsClickListener = onDonorDetailsClickListener;

            userName = itemView.findViewById(R.id.tvUserName);
            location = itemView.findViewById(R.id.tvLocationDonor);
            status = itemView.findViewById(R.id.tvStatusDonor);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onDonorDetailsClickListener.onClickDetails(getAbsoluteAdapterPosition(), donors.get(getAbsoluteAdapterPosition()));
        }
    }

    public interface OnDonorDetailsClickListener {
        void onClickDetails(int position, Donor donor);
    }
}
