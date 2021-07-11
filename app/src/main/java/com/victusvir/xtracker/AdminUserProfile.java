package com.victusvir.xtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class AdminUserProfile extends AppCompatActivity {

    public static final String VEHICLE_ID = "vehicleId";

    TextView textViewVehicleModel;
    TextView textViewVehicleRegNo;
    ImageView imageViewFuel;
    ImageView imageViewTraceLocation;
    ImageView imageViewService;
    String vehicleId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_profile);

        textViewVehicleModel = (TextView) findViewById(R.id.textViewModelName);
        textViewVehicleRegNo = (TextView) findViewById(R.id.textViewRegNo);
        imageViewFuel = (ImageView) findViewById(R.id.imageViewFuel);
        imageViewTraceLocation = (ImageView) findViewById(R.id.imageViewTraceLocation);
        imageViewService = (ImageView) findViewById(R.id.imageViewService);


        Intent intent = getIntent();
        String modelName = intent.getStringExtra("vehicleName");
        String regNo = intent.getStringExtra("vehicleRegNo");
        vehicleId = intent.getStringExtra("vehicleId");
        try {
            textViewVehicleModel.setText(modelName);
            textViewVehicleRegNo.setText(regNo);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        imageViewTraceLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AdminTrackMap.class);
                intent.putExtra(VEHICLE_ID,vehicleId);
                startActivity(intent);
            }
        });

        imageViewFuel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AdminFuelCheck.class);
                intent.putExtra(VEHICLE_ID,vehicleId);
                startActivity(intent);
            }
        });

        imageViewService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AdminServiceCheck.class);
                intent.putExtra(VEHICLE_ID,vehicleId);
                startActivity(intent);
            }
        });


    }


}