package com.example.blooddonor.staff.donor;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.blooddonor.R;
import com.example.blooddonor.adapter.DonorAdapter;
import com.example.blooddonor.model.Appointment;
import com.example.blooddonor.model.Donor;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PendingFragment extends Fragment implements DonorAdapter.OnDonorDetailsClickListener {

    private RecyclerView recyclerViewPending;

    private FirebaseAuth auth;
    private FirebaseDatabase firebase;
    private DatabaseReference appointmentRef;
    private DatabaseReference userRef;
    private DonorAdapter donorAdapter;

    private ArrayList<Donor> donors = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pending, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // init firebase
        auth = FirebaseAuth.getInstance();
        firebase = FirebaseDatabase.getInstance();
        appointmentRef = firebase.getReference().child("Appointments");
        userRef = firebase.getReference().child("Users");

        // init component
        recyclerViewPending = view.findViewById(R.id.rcPendingDonors);

        // recycler view
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerViewPending.setLayoutManager(linearLayoutManager);
        donorAdapter = new DonorAdapter(donors, this);
        recyclerViewPending.setAdapter(donorAdapter);

        // retrieve pending donors
        appointmentRef.orderByChild("status")
                .equalTo("pending")
                .addValueEventListener(new ValueEventListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Donor donor = dataSnapshot.getValue(Donor.class);
                            donors.add(donor);
                        }
                        donorAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    @Override
    public void onClickDetails(int position, Donor donor) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("donor", donor);

        Intent intent = new Intent(getActivity(), DonorDetailActivity.class);
        intent.putExtras(bundle);

        startActivity(intent);
    }
}