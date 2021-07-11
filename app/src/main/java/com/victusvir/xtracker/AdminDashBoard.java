package com.victusvir.xtracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminDashBoard extends AppCompatActivity {


    Button registerBtn;

    DatabaseReference databaseVehicle;

    ListView listViewVehicle;
    List<Vehicle> vehicleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dash_board);

        databaseVehicle = FirebaseDatabase.getInstance().getReference("vehicles");

        listViewVehicle = (ListView) findViewById(R.id.listViewVehicle);
        vehicleList = new ArrayList<>();

        findViewById(R.id.newRegisterBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),RegisterVehicle.class));
            }
        });

        listViewVehicle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Vehicle vehicle = vehicleList.get(position);

                Log.d("onItemClick: ", vehicle.getModel());


                Intent intent = new Intent(getApplicationContext(),AdminUserProfile.class);

                intent.putExtra("vehicleId",vehicle.getUserId());
                intent.putExtra("vehicleName",vehicle.getModel());
                intent.putExtra("vehicleRegNo",vehicle.getRegNo());
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseVehicle.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                vehicleList.clear();

                for (DataSnapshot vehicleSnapshot : snapshot.getChildren())
                {
                    Vehicle vehicle = vehicleSnapshot.getValue(Vehicle.class);
                    vehicleList.add(vehicle);
                }

                VehicleList adapter = new VehicleList(AdminDashBoard.this,vehicleList);
                listViewVehicle.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}