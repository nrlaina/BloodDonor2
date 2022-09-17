package com.example.blooddonor.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blooddonor.R;
import com.example.blooddonor.model.Appointment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class rewardAppointmentAdapter extends RecyclerView.Adapter<rewardAppointmentAdapter.MyViewHolder> {

    Context context;
    ArrayList<Appointment>list;

    //testing date3
    //String date = new Date().toString();


    public rewardAppointmentAdapter(Context context, ArrayList<Appointment> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.reward_appointment_layout,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

            Appointment appt = list.get(position);
            holder.name.setText(appt.getFullName());
            holder.venue.setText(appt.getLocation());
            holder.time.setText(appt.getTime());
            holder.btnApp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("tgh click", "onClick: ");
                    MaterialAlertDialogBuilder builder;
                    builder = new MaterialAlertDialogBuilder(context);
                    builder.setTitle("Reward")
                            .setMessage("Do you want to reward this user?").setCancelable(true)
                            .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    FirebaseDatabase db = FirebaseDatabase.getInstance();
                                    DatabaseReference reference = db.getReference().child("Appointments").child(appt.getAppointmentId());
                                    String auid = reference.child("attendance").getKey();
//                                    String uid = reference.push().getKey();

                                    Map<String, Object> newAppointment = new HashMap<>();
                                    newAppointment.put(auid, true);
                                    //update realtime database on attendance
                                    reference.updateChildren(newAppointment);
                                    reference.addValueEventListener(new ValueEventListener() {
                                        String userID,point;
//
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot){
                                           if(dataSnapshot.exists()){
                                                userID = dataSnapshot.child("userID").getValue().toString();
                                               Log.e(userID, "dah dapat data UID: ");

                                               DatabaseReference user = db.getReference().child("Users").child(userID);
                                               user.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                                                   @Override
                                                   public void onSuccess(DataSnapshot dataSnapshot1) {
                                                       point = dataSnapshot1.child("point").getValue().toString();
                                                       Log.e(point, "dah dapat point: " );
                                                       int currentPoint = Integer.parseInt(point);
                                                       int newPoint = currentPoint + 1;
                                                       String keyp = user.child("point").getKey();
                                                       Map<String, Object> newPointa = new HashMap<>();
                                                       newPointa.put(keyp, newPoint);
                                                       //update realtime database on attendance
                                                       user.updateChildren(newPointa).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                           @Override
                                                           public void onSuccess(Void unused) {
                                                               notifyDataSetChanged();
                                                           }
                                                       });

                                                   }


                                               });
                                               Toast.makeText(context, "Successfully claim points", Toast.LENGTH_SHORT).show();




                                           }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });

                                    //creating new database query to update points for attended user


                                }
                            }
                            ).show();
                }
            });
    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        Button btnApp = itemView.findViewById(R.id.btnrewardUser);
        TextView name, venue, time;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tvAppName);
            venue = itemView.findViewById(R.id.tvAppVenue);
            time = itemView.findViewById(R.id.tvAppTime);


        }
    }
}
