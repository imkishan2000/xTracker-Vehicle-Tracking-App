package com.victusvir.xtracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminServiceCheck extends AppCompatActivity {

    ListView listViewService;
    List<ServiceDetail> servicelist;

    private String vehicleId;

    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_service_check);

        listViewService = (ListView) findViewById(R.id.listViewService);
        servicelist = new ArrayList<>();

        Intent intent = getIntent();
        vehicleId = intent.getStringExtra(AdminUserProfile.VEHICLE_ID);

        database = FirebaseDatabase.getInstance().getReference("Service_Information").child(vehicleId);

    }

    @Override
    protected void onStart() {
        super.onStart();

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                servicelist.clear();
                for (DataSnapshot serviceSnapshot : snapshot.getChildren())
                {
                    ServiceDetail serviceDetail = serviceSnapshot.getValue(ServiceDetail.class);
                    servicelist.add(serviceDetail);
                }

                ServiceList adapter = new ServiceList(AdminServiceCheck.this,servicelist);
                listViewService.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }
}