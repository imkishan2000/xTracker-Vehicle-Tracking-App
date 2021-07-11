package com.victusvir.xtracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminFuelCheck extends AppCompatActivity {

    ListView listViewFuel;
    List<FuelDetail> fuellist;

    private String vehicleId;

    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_fuel_check);

        listViewFuel = (ListView) findViewById(R.id.listViewFuel);
        fuellist = new ArrayList<>();

        Intent intent = getIntent();
        vehicleId = intent.getStringExtra(AdminUserProfile.VEHICLE_ID);

        database = FirebaseDatabase.getInstance().getReference("Fuel_Information").child(vehicleId);
    }

    @Override
    protected void onStart() {
        super.onStart();

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
             fuellist.clear();
             for (DataSnapshot fuelSnapshot : snapshot.getChildren())
             {
                 FuelDetail fuelDetail = fuelSnapshot.getValue(FuelDetail.class);
                 fuellist.add(fuelDetail);
             }

             FuelList adapter = new FuelList(AdminFuelCheck.this,fuellist);
             listViewFuel.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }
}